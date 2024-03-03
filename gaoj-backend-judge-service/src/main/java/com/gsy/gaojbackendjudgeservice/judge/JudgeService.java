package com.gsy.gaojbackendjudgeservice.judge;


import gaojbackendmodel.model.entity.QuestionSubmit;

public interface JudgeService {
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
