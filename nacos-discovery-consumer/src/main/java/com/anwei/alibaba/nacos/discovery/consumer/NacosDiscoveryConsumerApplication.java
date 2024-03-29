package com.anwei.alibaba.nacos.discovery.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoveryConsumerApplication {

    @Autowired
    ConfigurableApplicationContext cfgAppctx;

    public static void main(String[] args) {
        SpringApplication.run(NacosDiscoveryConsumerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    public class NacosController {
        @Autowired
        private LoadBalancerClient loadBalancerClient;
        @Autowired
        private RestTemplate restTemplate;

//        @Value("${spring.application.name}")
//        private String appName;

        @GetMapping("/echo/app-name")
        public String echoAppName() {
            String appName = cfgAppctx.getEnvironment().getProperty("spring.application.name");
            String provider = cfgAppctx.getEnvironment().getProperty("provider.name");
            //使用 LoadBalanceClient 和 RestTemolate 结合的方式来访问
            ServiceInstance serviceInstance = loadBalancerClient.choose(provider);
            String url = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName + serviceInstance.getPort());
            System.out.println("request url:" + url);
            return restTemplate.getForObject(url, String.class);
        }

    }
}
