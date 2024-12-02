package com.lecsures.section2.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecsures.section2.dtos.ResponseDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// use only http basic authentication
public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setHeader("eazy-error-reason", "Authentication failed");
		// response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json;charset=UTF-8"); // set response type is json with char set UTF-8
		
		ResponseDto<Object> responseDto = new ResponseDto<Object>();
		responseDto.setSuccess(false);
		responseDto.setMessage(authException.getMessage());
		responseDto.setObject(null);
		
		ObjectMapper objMapper = new ObjectMapper();
		String jsonReponse = objMapper.writeValueAsString(responseDto);
		response.getWriter().write(jsonReponse);
	}
	
}
