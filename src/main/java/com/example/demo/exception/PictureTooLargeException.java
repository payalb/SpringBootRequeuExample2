package com.example.demo.exception;

public class PictureTooLargeException extends RuntimeException {

	private static final long serialVersionUID = -5694986355967190100L;

	public PictureTooLargeException(String string) {
		super(string);
	}

}
