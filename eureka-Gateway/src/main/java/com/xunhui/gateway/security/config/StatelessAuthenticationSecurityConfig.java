package com.xunhui.gateway.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.xunhui.gateway.security.filter.StatelessAuthenticationFilter;
import com.xunhui.gateway.security.filter.StatelessLoginFilter;
import com.xunhui.gateway.security.token.TokenAuthenticationService;

@EnableWebSecurity
@EnableAutoConfiguration
@Configuration
@ComponentScan
@Order(1)
public class StatelessAuthenticationSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationSecurityConfig() {
		super(true);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				/*.exceptionHandling().and()
				.anonymous().and()
				.servletApi().and()
				.headers().cacheControl().and()*/
				.authorizeRequests()
								
				//allow anonymous resource requests
//				.antMatchers("/").permitAll()
//				.antMatchers("/favicon.ico").permitAll()
//				.antMatchers("/resources/**").permitAll()

				//allow anonymous POSTs to login
				//.antMatchers(HttpMethod.POST, "/api/getToken").permitAll()
				
				//allow anonymous GETs to API
				//.antMatchers(HttpMethod.GET, "/api/**").permitAll()
				
				.antMatchers("/books/**").hasRole("ADMIN")
				.antMatchers("/animals/**").hasRole("USER").and()
				//all other request need to be authenticated
				//.anyRequest().hasRole("USER").and()
		
				// custom JSON based authentication by POST of {"username":"<name>","password":"<password>"} which sets the token header upon authentication
				.addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

				// custom Token based authentication based on the header previously given to the client
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)/*.exceptionHandling().and()
				.anonymous().and()
				.servletApi().and()
				.headers().cacheControl()*/;
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("admin11")).roles("ADMIN");
	}
	
	@Override  
    public void configure(WebSecurity web) throws Exception {  
        web.ignoring().antMatchers("/api/getToken");  
    }  
}
