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
   
   public JWave getMixedTrack(){
      if(tracks.size()==1){
         return(tracks.get(0));
      }else if(tracks.size()>1){
         JWave output = tracks.get(0);
         for(int i = 1; i < tracks.size(); i++){
            Effect.combine(output,tracks.get(i),trackPositions.get(i));
         }
         return(output);
      }
      return(null);
   }
}
