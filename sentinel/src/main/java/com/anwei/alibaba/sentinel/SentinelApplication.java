package com.anwei.alibaba.sentinel;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class SentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }

    @RestController
    public class TestController {
        @GetMapping(value = "/hello")
        @SentinelResource("hello")
        public String hello() {
            return "Hello Sentinel" + System.currentTimeMillis();
        }
    }

}
