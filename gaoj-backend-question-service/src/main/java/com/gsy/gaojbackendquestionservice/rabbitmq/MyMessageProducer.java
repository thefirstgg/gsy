package com.gsy.gaojbackendquestionservice.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;

public class MyMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param exchange
     * @param routingKey
     * @param message
     */
    public void sendMessage(String exchange,String routingKey,String message){
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}
