package eurekademo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
//@ServletComponentScan
//@RestController
public class EurekaApplication implements CommandLineRunner{

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EurekaApplication.class, args);
	}
	
//	@RequestMapping("/test.service")
//	 public String serviceUrl() {
//		
//	    return "123";
//	}

	@Override
	public void run(String... args) throws Exception {
//		HessianServiceExporter exporter = SpringTool.getBean("accountHessian");
//		System.out.println(">>>>>"+exporter);
	}
}
