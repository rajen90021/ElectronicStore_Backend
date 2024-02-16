package com.rajen.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class resourcenotfoundexception  extends RuntimeException{

	public resourcenotfoundexception() {
		// TODO Auto-generated constructor stub
		super("resource not found ");
	}
	public resourcenotfoundexception(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}
	
	
}
