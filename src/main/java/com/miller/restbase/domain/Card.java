package com.miller.restbase.domain;

public class Card {

	private int Id;
	private String phoneNumber;

	public Card()
	{
		
	}
	
	public Card(int Id, String phoneNumber)
	{
		setId(Id);
		setPhoneNumber(phoneNumber);
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	
	
}
