package com.xunhui.gateway;

import javax.servlet.Filter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunhui.gateway.security.pojo.User;
import com.xunhui.gateway.zuul.GatewayPreFilter;


@SpringBootApplication
@EnableZuulProxy
@RestController
public class GatewayApplication {
	
	@Bean
	public GatewayPreFilter simpleFilter() {
		return new GatewayPreFilter();
	}
	
	@Bean
	public Filter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return characterEncodingFilter;
	}
	
	private RestTemplate restTemplate = new TestRestTemplate();

	@Value("${server.port}")
	private String port;
	
//	@RequestMapping(value = "/testUserCurrent", method = RequestMethod.GET)
//	public void testUserCurrent() throws JsonProcessingException {
//		HttpHeaders httpHeaders = getToken();
//		HttpEntity<String> testRequest = new HttpEntity<String>(null, httpHeaders);
//		ResponseEntity<User> testResponse = restTemplate.exchange("http://localhost:" + port + "/books/to-read",HttpMethod.GET, testRequest, User.class);
//		System.out.println(">>>>>>testResponse:"+testResponse);
//	}
	
	@RequestMapping(value = "/api/getToken", method = RequestMethod.POST)
	public String getToken(@RequestBody String loginInfo) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonInfo = new JSONObject(loginInfo);
		String username = jsonInfo.getString("api_key");
		String password = jsonInfo.getString("api_secret");
		final User user =  new User();
		user.setUsername(username);
		user.setPassword(password);
		final String userJson = new ObjectMapper().writeValueAsString(user);
		HttpEntity<String> login = new HttpEntity<String>(
				userJson, httpHeaders);
		ResponseEntity<Void> results = restTemplate.postForEntity("http://localhost:" + port + "/api/login", login,
				Void.class);
		return results.getHeaders().getFirst("X-AUTH-TOKEN");
	}
	
    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApplication.class).web(true).run(args);
    }
}