package com.xunhui.gateway.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.xunhui.gateway.security.token.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService taService) {
		this.tokenAuthenticationService = taService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String returnInfo = "";
		try{
			Authentication auth = tokenAuthenticationService.getAuthenticationFromHeader((HttpServletRequest) req);
			SecurityContextHolder.getContext().setAuthentication(auth);
			chain.doFilter(req, res);
		}catch (AccessDeniedException e) {
			returnInfo = "Access is denied";
		}catch (Exception e) {
			returnInfo = "error,please check token";
		}finally{
			if(!returnInfo.equals("")){
				JSONObject returnJson = new JSONObject();
				try {
					returnJson.put("info", returnInfo);
					returnJson.put("flag", false);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				res.getWriter().write(returnJson.toString());
			}
		}
	}
}