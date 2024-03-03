package com.gsy.gaojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.gsy.gaojbackendcommon.common.ErrorCode;
import com.gsy.gaojbackendcommon.exception.BusinessException;
import com.gsy.gaojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import gaojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import gaojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandbox {
    //定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";
    private static final String AUTH_REQUEST_SECRET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {



        System.out.println("远程代码沙箱");
        String url = "http://192.168.80.129:8090/executeCode";
        //转换成json
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(responseStr)){
            //
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"execute remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }
}
