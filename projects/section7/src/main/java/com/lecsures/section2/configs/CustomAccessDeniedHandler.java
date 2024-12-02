package com.lecsures.section2.configs;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecsures.section2.dtos.ResponseDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setHeader("eazy-error-reason", "Authorization failed");
		// response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType("application/json.charset=UTF-8"); // set response type is json with char set UTF-8
		
		ResponseDto<Object> responseDto = new ResponseDto<Object>();
		responseDto.setSuccess(false);
		responseDto.setMessage(accessDeniedException.getMessage());
		responseDto.setObject(null);
		
		ObjectMapper objMapper = new ObjectMapper();
		String jsonReponse = objMapper.writeValueAsString(responseDto);
		response.getWriter().write(jsonReponse);
	}

}
