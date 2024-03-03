package com.gsy.gaojbackendjudgeservice.judge.strategy;


import gaojbackendmodel.model.codesandbox.JudgeInfo;
import gaojbackendmodel.model.dto.question.JudgeCase;
import gaojbackendmodel.model.entity.Question;
import gaojbackendmodel.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 用于定义在策略中传递的参数
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}
