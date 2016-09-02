package com.xunhui.monitor;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


@SpringBootApplication
@EnableTurbine
@EnableHystrixDashboard
@EnableEurekaClient
public class MonitorApplication {
	
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(MonitorApplication.class).web(true).run(args);
    }
}