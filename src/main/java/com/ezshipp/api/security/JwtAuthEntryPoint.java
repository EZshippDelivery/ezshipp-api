package com.ezshipp.api.security;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

   // private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);
    
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException e) 
//                        		 throws IOException, ServletException {
//    	
//       // logger.error("Unauthorized error. Message - {}", e.getMessage());
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
//    }

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
		
	}
}