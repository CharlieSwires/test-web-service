package com.charlie.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.charlie.beans.Track;
import com.charlie.beans.Tracks;
import com.charlie.services.MyService;


@RestController
@RequestMapping(value="/1")
public class ControllerSource1 {
	
	@Autowired
	private MyService serv1;
	
	//@PreAuthorize("hasRole('IT_DEVELOPER')")
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public ResponseEntity<Tracks> get(){
		Tracks rl = serv1.getTracks1();	
		return new ResponseEntity<Tracks>(rl, HttpStatus.OK);
	}
}
