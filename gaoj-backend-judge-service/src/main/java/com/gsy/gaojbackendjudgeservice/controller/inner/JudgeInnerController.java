package com.gsy.gaojbackendjudgeservice.controller.inner;

import com.gsy.gaojbackendjudgeservice.judge.JudgeService;
import com.gsy.gaojbackendserviceclient.service.JudgeFeignClient;
import gaojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 该服务只用于内部
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestBody long questionSubmitId){
        return judgeService.doJudge(questionSubmitId);
    }

}
