package com.rajen.exception;

public class badapirequest extends RuntimeException {

	
	
	public badapirequest() {
		super("file doest not exist");
	}
	public badapirequest(String msg) {
		super(msg);
	}
}
