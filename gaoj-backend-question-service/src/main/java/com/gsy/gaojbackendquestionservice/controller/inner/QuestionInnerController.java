package com.gsy.gaojbackendquestionservice.controller.inner;

import com.gsy.gaojbackendquestionservice.service.QuestionService;
import com.gsy.gaojbackendquestionservice.service.QuestionSubmitService;
import com.gsy.gaojbackendserviceclient.service.QuestionFeignClient;

import gaojbackendmodel.model.entity.Question;
import gaojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 该服务只用于内部
 */
@RestController
@RequestMapping(("/inner"))
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;
    /**
     * 根据 Id 获得用户
     * @param questionId
     * @return
     */
    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId){
        return questionService.getById(questionId);
    }
    @Override
    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionId){
        return questionSubmitService.getById(questionId);
    }
    @Override
    @PostMapping("/question_submit/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }


}
