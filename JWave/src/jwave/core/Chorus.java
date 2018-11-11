package jwave.core;

public class Chorus {
	public static void chorus(JWave j, double delay){
      JWave copy = new JWave(j.f);
      Effect.amplify(copy, 0.5);
      Effect.amplify(j, 0.5);
      Effect.combine(j, copy, delay);
    }
}
