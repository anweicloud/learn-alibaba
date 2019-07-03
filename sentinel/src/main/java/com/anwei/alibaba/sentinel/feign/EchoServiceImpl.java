package com.anwei.alibaba.sentinel.feign;

import org.springframework.stereotype.Component;

@Component
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String str) {
        return "请检查服务提供方状态";
    }
}