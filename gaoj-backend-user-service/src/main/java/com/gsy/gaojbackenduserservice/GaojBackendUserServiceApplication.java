package com.gsy.gaojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()//如果开启了redis，需要把exclude = {RedisAutoConfiguration.class}去掉
@MapperScan("com.gsy.gaojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.gsy")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.gsy.gaojbackendserviceclient.service"})
public class GaojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaojBackendUserServiceApplication.class, args);
    }

}
