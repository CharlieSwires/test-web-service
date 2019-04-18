package com.charlie.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.charlie.beans.Track;
import com.charlie.beans.Tracks;

@EnableIntegration
@IntegrationComponentScan
public class BasicIntegrationConfig {
	private Tracks tracks = new Tracks();
	private Tracks tracks1 = new Tracks();
	private Tracks tracks2 = new Tracks();
	private Tracks tracks3 = new Tracks();

 
	@Autowired
	private NumbersClassifier numbersClassifier;



	@Bean
	QueueChannel multipleofThreeChannel() {
		return new QueueChannel();
	}

	@Bean
	QueueChannel remainderIsOneChannel() {
		return new QueueChannel();
	}

	@Bean
	QueueChannel remainderIsTwoChannel() {
		return new QueueChannel();
	}
	@Bean
	QueueChannel remainderIsThreeChannel() {
		return new QueueChannel();
	}

	boolean isMultipleOfThree(Track track) {
		return "0".equals(track.getDest());
	}

	boolean isRemainderOne(Track track) {
		return "1".equals(track.getDest());
	}

	boolean isRemainderTwo(Track track) {
		return "2".equals(track.getDest());
	}

	boolean isRemainderThree(Track track) {
		return "3".equals(track.getDest());
	}
	@Bean
	public IntegrationFlow multipleOfThreeFlow() {
	    return flow -> flow.split()
	      .<Track> filter(this::isMultipleOfThree)
	      .channel("multipleOfThreeChannel");
	}
	@Bean
	public IntegrationFlow classify() {
		return flow -> flow.split()
				.<Integer, Integer> route(number -> number, 
						mapping -> mapping
						.channelMapping(0, "multipleofThreeChannel")
						.subFlowMapping(1, subflow -> subflow
								.<Track> handle((payload, headers) -> {
									tracks1.getTracks().add(payload);
									System.out.println("payload: "+payload);
									return payload;
								}).channel("remainderIsOneChannel"))
						.subFlowMapping(2, subflow -> subflow
								.<Track> handle((payload, headers) -> {
									tracks2.getTracks().add(payload);
									return payload;
								})
								.channel("remainderIsTwoChannel"))
						.subFlowMapping(3, subflow -> subflow
								.<Track> handle((payload, headers) -> {
									tracks3.getTracks().add(payload);
									return payload;
								}).channel("remainderIsThreeChannel")));
	}

	public Tracks getTracks1() {
		return tracks1;
	}

	public void setTracks1(Tracks tracks1) {
		this.tracks1 = tracks1;
	}

	public Tracks getTracks2() {
		return tracks2;
	}

	public void setTracks2(Tracks tracks2) {
		this.tracks2 = tracks2;
	}

	public Tracks getTracks3() {
		return tracks3;
	}

	public void setTracks3(Tracks tracks3) {
		this.tracks3 = tracks3;
	}

	public void deleteTracks1(){
		tracks1.setTracks(new ArrayList<Track>());
	}

	public void deleteTracks2(){
		tracks2.setTracks(new ArrayList<Track>());
	}

	public void deleteTracks3(){
		tracks2.setTracks(new ArrayList<Track>());
	}

	@Bean
	@InboundChannelAdapter(value = "multipleofThreeChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<Tracks> fileReadingMessageSource() {
		MessageSource<Tracks> trks = new MessageSource<Tracks>(){

			@Override
			public Message<Tracks> receive() {
				// TODO Auto-generated method stub
				return new GenericMessage<Tracks>(getTracks());
			}
		};

		return trks;
	}

	synchronized public Tracks getTracks() {
		List<Track> tracks = new ArrayList<Track>();
		if (this.tracks.getTracks()!= null)
			for (Track track: this.tracks.getTracks()){
				Track trk = new Track();
				trk.setDest(track.getDest());
				trk.setText(track.getText());
				tracks.add(trk);
			}
		Tracks result = new Tracks();
		result.setTracks(tracks);
		this.tracks.setTracks(new ArrayList<Track>());
		return result;
	}

	public void setTracks(Tracks tracks) {
		this.tracks = tracks;
	}	
	synchronized public void setTrack(Track track) {
		System.out.println("Post track:"+track.toString());
		this.tracks.getTracks().add(track);
		numbersClassifier.multipleOfThree(this.tracks.getTracks());
	}
}