package com.xunhui.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@RefreshScope
public class ConfigClientApplication {
	
	@Value("${from:123}")
	private String from;
	
	@Value("${name:456}")
	private String name;
	
	@RequestMapping("/from")
    public String from() {

        return this.from+","+this.name;
    }
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigClientApplication.class).web(true).run(args);
    }
}