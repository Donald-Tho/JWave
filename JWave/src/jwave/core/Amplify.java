package jwave.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Amplify {
	public static void amplify(JWave j, double multiplier){
      try{
         int totalSamples = j.Subchunk2Size / j.BitsPerSample * 8;
         int sampleNum = 0;      
         byte[] buffer = new byte[j.BitsPerSample / 8];
         
         ByteArrayInputStream bais = new ByteArrayInputStream(j.data);
         ByteArrayOutputStream baos = new ByteArrayOutputStream(j.BitsPerSample / 8);
         while(sampleNum < totalSamples && bais.available() > 0){
            bais.read(buffer);
            short intermediate = Util.shortFromByteArray(buffer, false);
            intermediate *= multiplier;
            if(intermediate > Short.MAX_VALUE){
               intermediate = Short.MAX_VALUE;
            }else if(intermediate < Short.MIN_VALUE){
               intermediate = Short.MIN_VALUE;
            }
            baos.write(Util.shortToLittleEndian(intermediate));
            sampleNum++;
         }
         j.data = baos.toByteArray();
         
         
      }catch(Exception e){
         e.printStackTrace();
      }
    }
}
