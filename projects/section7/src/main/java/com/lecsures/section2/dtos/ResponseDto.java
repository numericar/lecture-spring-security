package com.lecsures.section2.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {
	private boolean isSuccess;
	private String message;
	private T object; 
	private String created;
	
	public ResponseDto() {
		this.created = LocalDateTime.now().toString();
	}
}
