package me.tianying.config;//package com.ciming.config;
//
//import com.ciming.server.DynamicRouteServiceImpl;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//@Component
//@Order(2) // 通过order值的大小来决定启动的顺序
//
//public class InitGatewayRoute implements CommandLineRunner {
//
//    private Logger log = LoggerFactory.getLogger(InitGatewayRoute.class);
//
//    @Autowired
//    DynamicRouteServiceImpl dynamicRouteService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//
//            log.info("gateway路由初始化完成");
//            dynamicRouteService.initRoute();
//        } catch (Exception e) {
//            log.error("gateway路由初始化异常", e);
//        }
//    }
//
//}