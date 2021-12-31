package com.ezshipp.api.security;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntry extends BasicAuthenticationEntryPoint {

	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.addHeader("WWW-Authenticate", "Basic relam-" + getRealmName());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().print("HTTP Status 401 : Full authentication is required to access this resource");
	}

	@Override
	public void afterPropertiesSet() {
		setRealmName("ezshipp");
		super.afterPropertiesSet();
	}

}
