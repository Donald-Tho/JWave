package jwave.core;

public class Normalize {
	public static void normalize(JWave j){
      try{
         short largest = Util.getMaxAmplitude(j);
         
         double mult = (double)Short.MAX_VALUE /(double)largest;
         if(mult>1){
            Effect.amplify(j, mult);
         }
         
      }catch(Exception e){
         e.printStackTrace();
      }
    }
}
