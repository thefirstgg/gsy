package com.gsy.gaojbackendjudgeservice.judge.codesandbox;


import gaojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import gaojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
