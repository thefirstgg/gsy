package com.gsy.gaojbackendjudgeservice.judge.strategy;

import cn.hutool.json.JSONUtil;
import gaojbackendmodel.model.codesandbox.JudgeInfo;
import gaojbackendmodel.model.dto.question.JudgeCase;
import gaojbackendmodel.model.dto.question.JudgeConfig;
import gaojbackendmodel.model.entity.Question;
import gaojbackendmodel.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 默认判题策略
 */

public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {

        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfoResponse=new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        //先判断沙箱执行的结果输出数量和预期输出数量是否相等
        if(outputList.size()!= inputList.size())
        {
            judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //依次判断每项输入输出是否相等
        for(int i=0;i<judgeCaseList.size();i++)
        {
            JudgeCase judgeCase=judgeCaseList.get(i);

            if(!judgeCase.getOutput().equals(outputList.get(i)))
            {
                judgeInfoMessageEnum=JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制

        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needTimeLimit = judgeConfig.getTimeLimit();
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        if(time>needTimeLimit)
        {
            judgeInfoMessageEnum=JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            return null;
        }

        if(memory>needMemoryLimit)
        {
            judgeInfoMessageEnum=JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            return null;
        }
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;

    }
}
