package com.charlie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.charlie.beans.Tracks;
import com.charlie.services.MyService;


@RestController
@RequestMapping(value="/3")
public class ControllerSource3 {
	
	@Autowired
	private MyService serv3;
	
	//@PreAuthorize("hasRole('IT_DEVELOPER')")
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public ResponseEntity<Tracks> get(){
		Tracks rl = serv3.getTracks3();;	
		return new ResponseEntity<Tracks>(rl, HttpStatus.OK);
	}

}
