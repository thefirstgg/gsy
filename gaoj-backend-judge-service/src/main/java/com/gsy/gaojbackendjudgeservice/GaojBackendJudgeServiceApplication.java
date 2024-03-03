package com.gsy.gaojbackendjudgeservice;

import com.gsy.gaojbackendjudgeservice.rabbitmq.InitRabbitMq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()//如果开启了redis，需要把exclude = {RedisAutoConfiguration.class}去掉
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.gsy")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.gsy.gaojbackendserviceclient.service"})
public class GaojBackendJudgeServiceApplication {

    public static void main(String[] args) {
        //启动消息队列
        InitRabbitMq.doInit();
        SpringApplication.run(GaojBackendJudgeServiceApplication.class, args);
    }

}
