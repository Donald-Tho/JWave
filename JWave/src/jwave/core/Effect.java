package jwave.core;

public class Effect {
	
	public static void changeSpeed(JWave j, double multiplier){
		ChangeSpeed.changeSpeed(j,multiplier);
	}
	
	public static void combine(JWave j, JWave other, double secondStart){
		Combine.combine(j,other,secondStart);
	}
	
	public static void chorus(JWave j, double delay){
        Chorus.chorus(j,delay);
    }
	
	public static void amplify(JWave j, double multiplier){
		Amplify.amplify(j,multiplier);
	}
	
	public void normalize(JWave j){
		Normalize.normalize(j);
	}
}
