package hello;

import hello.service.BookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@RestController
public class EurekaClientApplication {
	@Autowired
  	private DiscoveryClient discoveryClient;
	@Autowired
	private BookService service;
	
   @RequestMapping("/")
    public String home() {
        return "Hello world";
    }
   	
	 @RequestMapping("/service-instances/{applicationName}")
	 public List<ServiceInstance> serviceInstancesByApplicationName(
	         @PathVariable String applicationName) {
	     return this.discoveryClient.getInstances(applicationName);
	 }
	 
	 @RequestMapping("/serviceUrl")
	 public String serviceUrl() {
	    List<ServiceInstance> list = discoveryClient.getInstances("STORES");
	    if (list != null && list.size() > 0 ) {
	        return list.get(0).getUri().toString();
	    }
	    return null;
	}
   
	 @RequestMapping("/to-read")
	  public String toRead() {
	    return service.readingList("123")+":client";
	  }
	 
    public static void main(String[] args) {
    	 new SpringApplicationBuilder(EurekaClientApplication.class).web(true).run(args);
    }
}