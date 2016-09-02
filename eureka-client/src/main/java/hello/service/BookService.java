package hello.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookService {

  @Bean
  RestTemplate restTemplate(){
    return new RestTemplate();
  }

  @Autowired
  RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "reliable")
  public String readingList(String s) {
    URI uri = URI.create("http://localhost:1111/");
    return this.restTemplate.getForObject(uri, String.class);
  }

  public String reliable(String s) {
    return "Cloud Native Java (O'Reilly)";
  }

}