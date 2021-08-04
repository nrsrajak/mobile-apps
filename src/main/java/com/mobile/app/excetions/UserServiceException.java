package com.mobile.app.excetions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = -1526274296182288790L;

	public UserServiceException(String message) {
		super(message); 
	}
	
}
