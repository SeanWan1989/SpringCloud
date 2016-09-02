package com.xunhui.gateway;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunhui.gateway.security.config.StatelessAuthenticationSecurityConfig;
import com.xunhui.gateway.security.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(StatelessAuthenticationSecurityConfig.class)
@WebIntegrationTest
public class ApplicationIT {

	private RestTemplate restTemplate = new TestRestTemplate();

	@Value("${server.port}")
	private String port;

//	@Test
//	@Timed(millis = 10000)
//	public void testToken() throws JsonProcessingException {
//		HttpHeaders httpHeaders = getToken();
//	}

	@Test
	public void testUserCurrent() throws JsonProcessingException {
		HttpHeaders httpHeaders = getToken();
		HttpEntity<String> testRequest = new HttpEntity<String>(null, httpHeaders);
		ResponseEntity<User> testResponse = restTemplate.exchange("http://localhost:" + port + "/books/to-read",
				HttpMethod.GET, testRequest, User.class);
		Assert.assertEquals(HttpStatus.OK, testResponse.getStatusCode());
	}

	private HttpHeaders getToken() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		String password = "admin11";
		String username = "admin";
		final User user =  new User();
		user.setUsername(username);
		user.setPassword(password);
		final String userJson = new ObjectMapper().writeValueAsString(user);
		HttpEntity<String> login = new HttpEntity<String>(
				userJson, httpHeaders);
		ResponseEntity<Void> results = restTemplate.postForEntity("http://localhost:" + port + "/api/login", login,
				Void.class);
		Assert.assertEquals(HttpStatus.OK, results.getStatusCode());
		Assert.assertNotNull(results.getHeaders().getFirst("X-AUTH-TOKEN"));
		httpHeaders.add("X-AUTH-TOKEN", results.getHeaders().getFirst("X-AUTH-TOKEN"));
		return httpHeaders;
	}

}
