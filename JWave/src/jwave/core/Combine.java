package jwave.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Combine {
	public static void combine(JWave j, JWave other, double secondStart){
	      ByteArrayInputStream bais2;
	      ByteArrayInputStream bais1;
	      int secondStartSamples = (int)(secondStart*j.SampleRate*j.BitsPerSample/8/j.NumChannels);
	      if(other.Subchunk2Size + secondStartSamples >= j.Subchunk2Size){
	         bais2 = new ByteArrayInputStream(j.data);
	         bais1 = new ByteArrayInputStream(other.data);
	      }else{
	         bais1 = new ByteArrayInputStream(j.data);
	         bais2 = new ByteArrayInputStream(other.data);
	      }
	      try{
	         int totalSamples = j.Subchunk2Size / j.BitsPerSample * 8 * j.NumChannels;
	         ByteArrayOutputStream baos = new ByteArrayOutputStream(j.Subchunk2Size);
	         int sampleNum = 0;      
	         byte[] buffer1 = new byte[j.BitsPerSample / 8 * j.NumChannels];
	         byte[] buffer2 = new byte[j.BitsPerSample / 8 * j.NumChannels];
	         while(sampleNum < totalSamples && bais1.available() > 0){
	            bais1.read(buffer1);
	            if(bais2.available()>0&&sampleNum >= secondStartSamples){
	               bais2.read(buffer2);
	            }else{
	            }
	            
	            byte[] left1 = new byte[j.BitsPerSample/8];
	            byte[] right1 = new byte[j.BitsPerSample/8];
	            byte[] left2 = new byte[j.BitsPerSample/8];
	            byte[] right2 = new byte[j.BitsPerSample/8];
	            
	            System.arraycopy(buffer1, 0, left1, 0, j.BitsPerSample/8);
	            System.arraycopy(buffer1, j.BitsPerSample/8, right1, 0, j.BitsPerSample/8);
	            System.arraycopy(buffer2, 0, left2, 0, j.BitsPerSample/8);
	            System.arraycopy(buffer2, j.BitsPerSample/8, right2, 0, j.BitsPerSample/8);
	            
	            int value = Util.shortFromByteArray(left1, false) + Util.shortFromByteArray(left2, false);
	            baos.write(Util.shortToLittleEndian((int)value));
	            
	            value = Util.shortFromByteArray(right1, false) + Util.shortFromByteArray(right2, false);
	            baos.write(Util.shortToLittleEndian((int)value));
	            
	            sampleNum++;
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
