package com.miller.restbase.exceptions;

public class UserNotFoundException extends Exception {

	private int userId;
	
	public UserNotFoundException(int userId)
	{
		setUserId(userId);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Could not find User: " + getUserId();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
