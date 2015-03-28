package com.miller.restbase.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.miller.restbase.domain.Card;
import com.miller.restbase.exceptions.CardNotFoundException;

@RestController
@RequestMapping("/users/{userId}/cards")
public class CardController {
		
	List<Card> cards = new ArrayList<Card>();
	
	public CardController()
	{
		cards.add(new Card(1, "732-541-5555"));
		cards.add(new Card(2, "732-541-1111"));
		cards.add(new Card(3, "732-541-2222"));
		cards.add(new Card(4, "732-541-3333"));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/", method = RequestMethod.GET)
    public List<Card> listAllCards(@PathVariable int userId)
    {
    	return cards;
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/{cardId}", method = RequestMethod.GET)
    public Card getCard(@PathVariable int userId, @PathVariable int cardId) throws CardNotFoundException
    {
		for(Card card : cards)
			if(card.getId() == cardId)
				return card;
		
		throw new CardNotFoundException(userId, cardId);
    }
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/", method = RequestMethod.POST)
    public ResponseEntity<Card> addCard(@PathVariable int userId, @RequestBody Card card) throws CardNotFoundException
    {
		cards.add(card);
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(getClass()).getCard(userId, card.getId())).toUri());
		return new ResponseEntity<Card>(card, httpHeaders, HttpStatus.CREATED);
    }
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({CardNotFoundException.class})
	public ResponseEntity<VndErrors> cardNotFoundExceptionHandler(CardNotFoundException ex)
	{
		MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd.error+json");
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(vndErrorMediaType);
		return new ResponseEntity<VndErrors>(new VndErrors("CardNotFoundException", 
				ex.getMessage()), httpHeaders, HttpStatus.NOT_FOUND);
	}
	
}
