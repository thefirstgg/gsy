package com.gsy.gaojbackendjudgeservice.judge.codesandbox.impl;


import com.gsy.gaojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import gaojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import gaojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
