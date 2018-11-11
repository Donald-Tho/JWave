package jwave.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ChangeSpeed {
	public static void changeSpeed(JWave j, double multiplier){
      try{
         int newSamples = (int)(j.Subchunk2Size / multiplier / (j.BitsPerSample/8)* j.NumChannels);
         double sampleNum = 0;
         byte[] next = new byte[j.BitsPerSample/8*j.NumChannels];
         byte[] last = new byte[j.BitsPerSample/8*j.NumChannels];
         
         int lastpos = 0;
         int nextpos = 1;
         
         
         ByteArrayOutputStream baos = new ByteArrayOutputStream(j.BitsPerSample / 8 * j.NumChannels);
         ByteArrayInputStream bais = new ByteArrayInputStream(j.data);
         
         
         while(sampleNum < newSamples && bais.available() > 0){
            double exactDifference = sampleNum - lastpos;
            double bias = exactDifference-Math.floor(exactDifference);
            if(exactDifference >= 1){
               if(!(lastpos == Math.floor(sampleNum) || nextpos == Math.floor(sampleNum) + 1)){
                  if(lastpos == Math.floor(sampleNum) - 1){
                     
                     last = next.clone();
                     lastpos = nextpos;
                     bais.read(next);
                     nextpos = lastpos + 1;
                  }
                  else if(lastpos < Math.floor(sampleNum) - 1){
                     
                     bais.skip((long)((Math.floor(sampleNum)-lastpos*j.BitsPerSample/8*j.NumChannels)));
                     bais.read(last);
                     bais.read(next);
                     lastpos = (int)Math.floor(sampleNum);
                     nextpos = (int)Math.floor(sampleNum) + 1;
                  }
               }
            }
            
            
            byte[] prevleft = new byte[j.BitsPerSample/8];
            byte[] prevright = new byte[j.BitsPerSample/8];
            byte[] nextleft = new byte[j.BitsPerSample/8];
            byte[] nextright = new byte[j.BitsPerSample/8];
            System.arraycopy(last, 0, prevleft, 0, j.BitsPerSample/8);
            System.arraycopy(last, j.BitsPerSample/8, prevright, 0, j.BitsPerSample/8);
            System.arraycopy(next, 0, nextleft, 0, j.BitsPerSample/8);
            System.arraycopy(next, j.BitsPerSample/8, nextright, 0, j.BitsPerSample/8);
            
            double value = Util.cosinterp(Util.shortFromByteArray(prevleft, false), Util.shortFromByteArray(nextleft, false), bias);
            baos.write(Util.shortToLittleEndian((int)value));
            
            value = Util.cosinterp(Util.shortFromByteArray(prevright, false), Util.shortFromByteArray(nextright, false), bias);
            baos.write(Util.shortToLittleEndian((int)value));
            sampleNum += multiplier;
         }
         
         byte[] temp = baos.toByteArray();
         j.data = new byte[(int)(temp.length)];
         baos.close();
         java.lang.System.arraycopy(temp,0,j.data,0,(int)(temp.length));
         j.Subchunk2Size = j.data.length;
      }catch(Exception e){
         e.printStackTrace();
      }
   }
}
