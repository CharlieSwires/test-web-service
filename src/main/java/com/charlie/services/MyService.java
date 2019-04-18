package com.charlie.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charlie.beans.Track;
import com.charlie.beans.Tracks;


@Service
public class MyService {

	@Autowired
	private BasicIntegrationConfig bic;
	
	public Tracks getTracks() {
		// TODO Auto-generated method stub
		return null;
	}

	synchronized public Tracks getTracks3() {
		Tracks copyTracks = new Tracks();
		List<Track> tal = new ArrayList<Track>();
		if (bic.getTracks3().getTracks() != null)
		for(Track track: bic.getTracks3().getTracks()){
			Track tr = new Track();
			tr.setDest(track.getDest());
			tr.setText(track.getText());
			tal.add(tr);
		}
		bic.deleteTracks3();
		copyTracks.setTracks(tal);
		return copyTracks;
	}

	synchronized public Tracks getTracks2() {
		Tracks copyTracks = new Tracks();
		List<Track> tal = new ArrayList<Track>();
		if (bic.getTracks2().getTracks() != null)
		for(Track track: bic.getTracks2().getTracks()){
			Track tr = new Track();
			tr.setDest(track.getDest());
			tr.setText(track.getText());
			tal.add(tr);
		}
		bic.deleteTracks2();
		copyTracks.setTracks(tal);
		return copyTracks;
	}

	synchronized public Tracks getTracks1() {
		Tracks copyTracks = new Tracks();
		List<Track> tal = new ArrayList<Track>();
		if (bic.getTracks1().getTracks() != null)
		for(Track track: bic.getTracks1().getTracks()){
			Track tr = new Track();
			tr.setDest(track.getDest());
			tr.setText(track.getText());
			tal.add(tr);
		}
		bic.deleteTracks1();
		copyTracks.setTracks(tal);
		return copyTracks;
	}

	public Track send(Track track) {
		bic.setTrack(track);
		return track;
	}



}
