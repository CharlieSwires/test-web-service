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
		return "1".equals(track.getDest())||"2".equals(track.getDest())||"3".equals(track.getDest());
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
	      .channel("multipleOfThreeChannel").gateway(classify((this.tracks.getTracks() != null && this.tracks.getTracks().size() > 0)?this.tracks.getTracks().get(0):null));
	}
	@Bean
	public IntegrationFlow classify(Track track) {
		return flow -> flow.split()
				.<Track,String> route(m -> m.getDest(),
						mapping -> mapping
						.subFlowMapping("1", subflow -> subflow
								.<Track> handle((payload, headers) -> {
									if (tracks1.getTracks() == null) {
										List<Track> ts = new ArrayList<Track>();
										tracks1.setTracks(ts);
									}
									tracks1.getTracks().add(payload);
									tracks.getTracks().remove(0);
									System.out.println("payload1: "+payload);
									return payload;
								}).channel("remainderIsOneChannel"))
						.subFlowMapping("2", subflow -> subflow
								.<Track> handle((payload, headers) -> {
									if (tracks2.getTracks() == null) {
										List<Track> ts = new ArrayList<Track>();
										tracks2.setTracks(ts);
									}
									tracks2.getTracks().add(payload);
									tracks.getTracks().remove(0);
									System.out.println("payload2: "+payload);
									return payload;
								})
								.channel("remainderIsTwoChannel"))
						.subFlowMapping("3", subflow -> subflow
								.<Track> handle((payload, headers) -> {
									if (tracks3.getTracks() == null) {
										List<Track> ts = new ArrayList<Track>();
										tracks3.setTracks(ts);
									}
									tracks3.getTracks().add(payload);
									tracks.getTracks().remove(0);
									System.out.println("payload3: "+payload);
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
		tracks3.setTracks(new ArrayList<Track>());
	}

	@Bean
	@InboundChannelAdapter(value = "multipleofThreeChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<Track> fileReadingMessageSource() {
		MessageSource<Track> trk = new MessageSource<Track>(){

			@Override
			public Message<Track> receive() {
				if (tracks.getTracks() == null || tracks.getTracks().size() == 0) return new GenericMessage<Track>(new Track());
				return new GenericMessage<Track>(tracks.getTracks().get(0));
			}
		};

		return trk;
	}

	public Tracks getTracks() {
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
		return result;
	}

	public void setTracks(Tracks tracks) {
		this.tracks = tracks;
	}	
	public void setTrack(Track track) {
		if(this.tracks.getTracks() == null) {
			List<Track> tracks = new ArrayList<Track>();
			this.tracks.setTracks(tracks);
		}
		this.tracks.getTracks().add(track);

		numbersClassifier.multipleOfThree(this.tracks.getTracks().get(0));
	}
}