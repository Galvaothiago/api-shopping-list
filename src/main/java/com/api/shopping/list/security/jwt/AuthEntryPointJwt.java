package com.api.shopping.list.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
	private static Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		String message = authException.getMessage();
		
		if(message.equalsIgnoreCase("Bad credentials")) message = "email or password is not correct";
		
		response.setContentType("application/json");
	    response.setStatus(HttpStatus.UNAUTHORIZED.value());
	    response.getOutputStream().println("{ \"error\": \"" + message + "\" }");
		
		logger.error("Unauthorized error: {}", authException.getMessage());
	}
}
