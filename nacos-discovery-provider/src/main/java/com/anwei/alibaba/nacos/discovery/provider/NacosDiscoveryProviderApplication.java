package com.anwei.alibaba.nacos.discovery.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoveryProviderApplication {

    @Autowired
    ConfigurableApplicationContext cfgApplicationContext;

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosDiscoveryProviderApplication.class, args);
        while(true) {
            //当动态配置刷新时，会更新到 Enviroment中，因此这里每隔一秒中从Enviroment中获取配置
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            System.err.println("user name :" + userName + "; age: " + userAge);
            TimeUnit.SECONDS.sleep(3);
        }
    }

    /**
     * 静态加载resource下的配置
    */
//    @Value("${server.port}")
//    private String port;

    @RestController
    public class EchoController {
        @GetMapping(value = "/echo/{string}")
        public String echo(@PathVariable String string) {
//            Integer.parseInt("2A");
            final String userName = cfgApplicationContext.getEnvironment().getProperty("user.name");
            final String port = cfgApplicationContext.getEnvironment().getProperty("server.port");
            return "Hello Nacos Discovery " + string+" my port:"+port + "  " + userName;
        }
    }
}
