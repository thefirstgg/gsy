package com.gsy.gaojbackendjudgeservice.judge.codesandbox.impl;


import com.gsy.gaojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import gaojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import gaojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import gaojbackendmodel.model.codesandbox.JudgeInfo;
import gaojbackendmodel.model.enums.JudgeInfoMessageEnum;
import gaojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> inputList = executeCodeRequest.getInputList();

        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试用例成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo=new JudgeInfo();

        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);

        executeCodeResponse.setJudgeInfo(judgeInfo);
        
        return executeCodeResponse;
    }
}
