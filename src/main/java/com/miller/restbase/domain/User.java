package com.miller.restbase.domain;

import com.fasterxml.jackson.annotation.JsonView;

public class User {
	
	public interface SummaryView {};
	
	private int id;
	private String email;
	private String password;
	
	public User(int id, String email, String password)
	{
		setId(id);
		setEmail(email);
		setPassword(password);
	}
	
	@JsonView(SummaryView.class)
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@JsonView(SummaryView.class)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

}
