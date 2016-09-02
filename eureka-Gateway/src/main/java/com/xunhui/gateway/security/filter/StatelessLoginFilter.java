package com.xunhui.gateway.security.filter;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunhui.gateway.security.pojo.User;
import com.xunhui.gateway.security.pojo.UserAuthentication;
import com.xunhui.gateway.security.pojo.UserRole;
import com.xunhui.gateway.security.token.TokenAuthenticationService;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private final TokenAuthenticationService tokenAuthenticationService;

	public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
			AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.tokenAuthenticationService = tokenAuthenticationService;
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword());
		return getAuthenticationManager().authenticate(loginToken);
	}

	@SuppressWarnings("serial")
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		final User user = new User();
		user.setUsername(authentication.getName());
		user.setPassword(new BCryptPasswordEncoder().encode("admin"));
		user.setRoles(new HashSet<UserRole>() {
			{
				add(UserRole.ADMIN);
			}
		});
		final UserAuthentication userAuthentication = new UserAuthentication(user);

		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthenticationTokenInHeader(response, userAuthentication);

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}
}