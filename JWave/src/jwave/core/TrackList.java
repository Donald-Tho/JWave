package jwave.core;

import java.util.ArrayList;

public class TrackList {
	public ArrayList<JWave> tracks;
	public ArrayList<Double> trackPositions;
	
	public TrackList() {
		tracks = new ArrayList<JWave>();
		trackPositions = new ArrayList<Double>();
	}
	
	public void add(JWave j) {
		tracks.add(j);
		trackPositions.add(0.0);
	}
	
	public void add(JWave j, double trackPosition) {
		tracks.add(j);
		trackPositions.add(trackPosition);
	}
}
