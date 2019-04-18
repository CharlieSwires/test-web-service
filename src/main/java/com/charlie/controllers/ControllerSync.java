package com.charlie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.charlie.beans.Track;
import com.charlie.beans.Tracks;
import com.charlie.services.MyService;


@RestController
@RequestMapping(value="/central")
public class ControllerSync {
	
	@Autowired
	private MyService serv;
	
	//@PreAuthorize("hasRole('IT_DEVELOPER')")
	@RequestMapping(value = "/get", method=RequestMethod.GET)
	public ResponseEntity<Tracks> get(){
		Tracks rl = serv.getTracks();;	
		return new ResponseEntity<Tracks>(rl, HttpStatus.OK);
	}

	@RequestMapping(value = "/post", method=RequestMethod.POST)
	public ResponseEntity<Track> postTest(@RequestBody Track track){
		return new ResponseEntity<Track>(serv.send(track),HttpStatus.OK);
		
	}

}
