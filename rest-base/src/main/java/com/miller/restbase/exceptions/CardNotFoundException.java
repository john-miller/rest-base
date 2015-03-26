package com.miller.restbase.exceptions;

public class CardNotFoundException extends Exception {

	private int cardId;
	private int userId;
	
	public CardNotFoundException(int userId, int cardId)
	{
		setUserId(userId);
		setCardId(cardId);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Could not find Card: " + getCardId() + " for User: " + getUserId();
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
