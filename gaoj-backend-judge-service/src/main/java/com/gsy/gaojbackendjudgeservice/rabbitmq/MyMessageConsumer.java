package com.gsy.gaojbackendjudgeservice.rabbitmq;

import com.gsy.gaojbackendjudgeservice.judge.JudgeService;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MyMessageConsumer {
    @Resource
    private JudgeService judgeService;


    //指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"},ackMode = "MANUAL")//队列的名称，ackMode：是否需要确认一下再认为成功取走，默认的话直接取走不需要确认
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag){
        log.info("receiveMessage message = {}",message);
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliveryTag,false);

        }catch (Exception e){
            channel.basicNack(deliveryTag,false,false);
        }

    }
}
