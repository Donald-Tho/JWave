package jwave.core;

import java.util.ArrayList;

public class Tracks {
	ArrayList<JWave> tracks = new ArrayList<JWave>();
	public Tracks() {
	}
	
	public void add(JWave j) {
		tracks.add(j);
	}
	
	public JWave get(int index) {
		return(tracks.get(index));
	}
	
	public JWave remove (int index) {
		return(tracks.remove(index));
	}
	
	public JWave remove() {
		return(tracks.remove(tracks.size()-1));
	}
}
