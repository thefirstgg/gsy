package com.gsy.gaojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsy.gaojbackendcommon.common.ErrorCode;
import com.gsy.gaojbackendcommon.constant.CommonConstant;
import com.gsy.gaojbackendcommon.exception.BusinessException;
import com.gsy.gaojbackendcommon.utils.SqlUtils;
import com.gsy.gaojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.gsy.gaojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.gsy.gaojbackendquestionservice.service.QuestionService;
import com.gsy.gaojbackendquestionservice.service.QuestionSubmitService;
import com.gsy.gaojbackendserviceclient.service.JudgeFeignClient;
import com.gsy.gaojbackendserviceclient.service.UserFeignClient;
import gaojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import gaojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import gaojbackendmodel.model.entity.Question;
import gaojbackendmodel.model.entity.QuestionSubmit;
import gaojbackendmodel.model.entity.User;
import gaojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import gaojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import gaojbackendmodel.model.vo.QuestionSubmitVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author gsy
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-02-03 15:21:34
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {
    @Resource
    private QuestionService questionService;
    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Resource
    private MyMessageProducer myMessageProducer;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //todo 校验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum==null)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误");
        }

        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        //todo
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if(!save)
        {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据插入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
        //向消息队列发送信息（发给judge模块）
        myMessageProducer.sendMessage("code_exchange","my_routingKey",String.valueOf(questionSubmitId));
//        //执行判题服务
//        CompletableFuture.runAsync(()->{
//
//            judgeFeignClient.doJudge(questionSubmitId);
//        });
        return questionSubmitId;
    }
    /**
     * 获取查询包装类
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotEmpty(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status)!=null, "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        //只有本人能够看到自己的代码
        long userId = loginUser.getId();
        //进行脱敏
        if(userId!=questionSubmit.getUserId()&&!userFeignClient.isAdmin(loginUser))
        {
            questionSubmitVO.setCode(null);
        }

        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream().map(questionSubmit -> {
            return getQuestionSubmitVO(questionSubmit, loginUser);
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

}




