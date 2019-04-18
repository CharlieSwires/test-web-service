package com.charlie.services;

import java.util.List;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.charlie.beans.Track;

@MessagingGateway
public interface NumbersClassifier {

	@Gateway(requestChannel = "multipleOfThreeFlow.input")
	void multipleOfThree(Track track);

	@Gateway(requestChannel = "remainderIsOneFlow.input")
	void remainderIsOne(List<Track> tracks);

	@Gateway(requestChannel = "remainderIsTwoFlow.input")
	void remainderIsTwo(List<Track> tracks);

	@Gateway(requestChannel = "remainderIsThreeFlow.input")
	void remainderIsThree(List<Track> tracks);


}	
