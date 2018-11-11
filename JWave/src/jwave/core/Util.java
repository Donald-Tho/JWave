/**
 * 
 */
package jwave.core;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author apier
 *
 */
public class Util {
	public static int cosinterp(int a, int b, double x){
		return((int)(Math.cos(x*Math.PI)*(a-b)/2.0 + (a+b)/2.0));
	}
	
	public static int readInt(FileInputStream fis, String endian){
      try{
         if(endian.equals("big")){
            byte[] b = new byte[4];
            fis.read(b);
            return(intFromByteArray(b, true));
         }else{
            byte[] b = new byte[4];
            fis.read(b);
            return(intFromByteArray(b, false));
         }
      }catch(IOException e){
         System.out.println(e.toString());
      }
      return(-1);
    }
	
	public static short readShort(FileInputStream fis, String endian){
      try{
         if(endian.equals("big")){
            byte[] b = new byte[2];
            fis.read(b);
            return(shortFromByteArray(b, true));
         }else{
            byte[] b = new byte[2];
            fis.read(b);
            return(shortFromByteArray(b, false));
         }
      }catch(IOException e){
         System.out.println(e.toString());
      }
      return(-1);
    }
	
	public static int intFromByteArray(byte[] b, boolean bigEndian) {
      if(bigEndian){
         return   b[3] & 0xFF |
            (b[2] & 0xFF) << 8 |
            (b[1] & 0xFF) << 16 |
            (b[0] & 0xFF) << 24;
      }else{
         return   b[0] & 0xFF |
            (b[1] & 0xFF) << 8 |
            (b[2] & 0xFF) << 16 |
            (b[3] & 0xFF) << 24;
      }
    }
	
	public static short shortFromByteArray(byte[] b, boolean bigEndian) {
      if(bigEndian){
         return   (short)(b[1] & 0xFF |
            (b[0] & 0xFF) << 8);
      }else{
         return   (short)(b[0] & 0xFF |
            (b[1] & 0xFF) << 8);
      }
	}
	
	public static byte[] intToLittleEndian(int num) {
      byte[] b = new byte[4];
      b[0] = (byte) (num & 0xFF);
      b[1] = (byte) ((num >> 8) & 0xFF);
      b[2] = (byte) ((num >> 16) & 0xFF);
      b[3] = (byte) ((num >> 24) & 0xFF);
      return b;
    }
   
   public static byte[] intToBigEndian(int num) {
      byte[] b = new byte[4];
      b[0] = (byte) ((num >> 24) & 0xFF);
      b[1] = (byte) ((num >> 16) & 0xFF);
      b[2] = (byte) ((num >> 8) & 0xFF);
      b[3] = (byte) (num & 0xFF);
      return b;
    }
   
   public static byte[] shortToLittleEndian(short num) {
      byte[] b = new byte[2];
      b[0] = (byte) (num & 0xFF);
      b[1] = (byte) ((num >> 8) & 0xFF);
      return b;
    }
   
   public static byte[] shortToLittleEndian(int num) {
      byte[] b = new byte[2];
      b[0] = (byte) (num & 0xFF);
      b[1] = (byte) ((num >> 8) & 0xFF);
      return b;
    }
   
   public static byte[] shortToBigEndian(short num) {
      byte[] b = new byte[2];
      b[0] = (byte) ((num >> 8) & 0xFF);
      b[1] = (byte) (num & 0xFF);
      return b;
    }
   
   public ArrayList<Sample> getSamples(JWave j, byte[] data){
      ArrayList<Sample> samples = new ArrayList<Sample>();
      try{
         int totalSamples = j.Subchunk2Size / j.BitsPerSample * 8;
         int sampleNum = 0;      
         byte[] buffer = new byte[j.BitsPerSample / 8];
         
         ByteArrayInputStream bais = new ByteArrayInputStream(data);
         while(sampleNum < totalSamples && bais.available() > 1){
            bais.read(buffer);
            short left = shortFromByteArray(buffer, false);
            bais.read(buffer);
            short right = shortFromByteArray(buffer, false);
            samples.add(new Sample(left,right));
            sampleNum+=2;
         }
         return(samples);
      }catch(Exception e){
         e.printStackTrace();
         return(null);
      }
   }
   
   public static short getMaxAmplitude(JWave j){
      try{
         int totalSamples = j.Subchunk2Size / j.BitsPerSample * 8;
         int sampleNum = 0;      
         byte[] buffer = new byte[j.BitsPerSample / 8];
         short largest = 0;
         
         ByteArrayInputStream bais = new ByteArrayInputStream(j.data);
         while(sampleNum < totalSamples && bais.available() > 0){
            bais.read(buffer);
            short temp = Util.shortFromByteArray(buffer, false);
            if(temp<0){
               temp = (short)(0 - temp);
            }
            if(temp > largest){
               largest = temp;
            }
            sampleNum++;
         }
         return(largest);
      }catch(Exception e){
         e.printStackTrace();
         return(0);
      }
   }
}
