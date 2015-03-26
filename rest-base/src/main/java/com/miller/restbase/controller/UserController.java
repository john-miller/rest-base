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

import com.fasterxml.jackson.annotation.JsonView;
import com.miller.restbase.domain.User;
import com.miller.restbase.exceptions.UserNotFoundException;

@RequestMapping("/users")
@RestController
public class UserController {

	private List<User> users = new ArrayList<User>();
	
	public UserController()
	{
    	users.add(new User(1, "test@test.com", "SuperSecretPassword"));
    	users.add(new User(5, "test@test.com", "SuperSecretPassword"));
	}
	
	/* Information about the individual user */
	@JsonView(User.SummaryView.class)
    @RequestMapping(value="{userId}", method = RequestMethod.GET)
    public User getUserInfo(@PathVariable int userId) throws UserNotFoundException 
	{
    	
    	for(User user : users)
    		if(user.getId() == userId)
    			return user;
    	
    	throw new UserNotFoundException(userId);
    }
	
	/* Information about all the users */
	@JsonView(User.SummaryView.class)
    @RequestMapping(value="/", method = RequestMethod.GET)
    public List<User> listAllUsers()
    {
    	return users;
    }
	
	/* Add a user */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="/", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@PathVariable int userId, @RequestBody User user) throws UserNotFoundException
    {
		users.add(user);
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(methodOn(getClass()).getUserInfo(user.getId())).toUri());
		return new ResponseEntity<User>(user, httpHeaders, HttpStatus.CREATED);
    }
	
	/* Exception Handling */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<VndErrors> userNotFoundException(UserNotFoundException ex)
	{
		MediaType vndErrorMediaType = MediaType.parseMediaType("application/vnd.error+json");
		HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(vndErrorMediaType);
		return new ResponseEntity<VndErrors>(new VndErrors("UserNotFoundException", 
				ex.getMessage()), httpHeaders, HttpStatus.NOT_FOUND);
	}
	
}
