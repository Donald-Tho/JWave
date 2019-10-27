package jwave.core;

import java.io.*;

public class JWave{
   public File f;
   
   public int ChunkID;
   public int ChunkSize;
   public int Format;
   public int Subchunk1ID;
   public int Subchunk1Size;
   public short AudioFormat;
   public short NumChannels;
   public int SampleRate;
   public int ByteRate;
   public short BlockAlign;
   public short BitsPerSample;
   public int Subchunk2ID;
   public int Subchunk2Size;
   
   public byte[] data;
   
   
   
   public JWave(){
   }
   
   public JWave(File f){
      this.f = f;
      loadAllData();
   }
   
   public JWave(String s){
      this.f = new File(s);
      loadAllData();
   }
      
   public String getAttributes(){
      return("ChunkID: "+ChunkID +"\n"+
         "ChunkSize: "+ChunkSize +"\n"+
         "Format: "+Format +"\n"+
         "Subchunk1ID: "+Subchunk1ID +"\n"+
         "Subchunk1Size: "+Subchunk1Size +"\n"+
         "AudioFormat: "+AudioFormat +"\n"+
         "NumChannels: "+NumChannels +"\n"+
         "SampleRate: "+SampleRate +"\n"+
         "ByteRate: "+ByteRate +"\n"+
         "BlockAlign: "+BlockAlign +"\n"+
         "BitsPerSample: "+BitsPerSample +"\n"+
         "Subchunk2ID: "+Subchunk2ID +"\n"+
         "Subchunk2Size: "+Subchunk2Size);
   }
   
   private void loadAttributes(){
      //read raw bytes and convert to ints and shorts
      try{
         FileInputStream fis = new FileInputStream(f);
         ChunkID = Util.readInt(fis, "big");
         ChunkSize = Util.readInt(fis, "little");
         Format = Util.readInt(fis, "big");
         Subchunk1ID = Util.readInt(fis, "big");
         Subchunk1Size = Util.readInt(fis, "little");
         AudioFormat = Util.readShort(fis, "little");
         NumChannels = Util.readShort(fis, "little");
         SampleRate = Util.readInt(fis, "littl e");
         ByteRate = Util.readInt(fis, "little");
         BlockAlign = Util.readShort(fis, "little");
         BitsPerSample = Util.readShort(fis, "little");
         Subchunk2ID = Util.readInt(fis, "big");
         Subchunk2Size = Util.readInt(fis, "little");
         fis.close();
      } catch(Exception e){
         e.printStackTrace();
      }
      
   }
   
   private void writeAttributes(FileOutputStream fos){
      try{
         
         //convert to bytes (with correct endianess) and write sequentially
         fos.write(Util.intToBigEndian(ChunkID));
         fos.write(Util.intToLittleEndian(ChunkSize));
         fos.write(Util.intToBigEndian(Format));
         fos.write(Util.intToBigEndian(Subchunk1ID));
         fos.write(Util.intToLittleEndian(Subchunk1Size));
         fos.write(Util.shortToLittleEndian(AudioFormat));
         fos.write(Util.shortToLittleEndian(NumChannels));
         fos.write(Util.intToLittleEndian(SampleRate));
         fos.write(Util.intToLittleEndian(ByteRate));
         fos.write(Util.shortToLittleEndian(BlockAlign));
         fos.write(Util.shortToLittleEndian(BitsPerSample));
         fos.write(Util.intToBigEndian(Subchunk2ID));
         fos.write(Util.intToLittleEndian(Subchunk2Size));
      }
      catch(IOException e){
         System.out.println(e.toString());
      }
   }
   
   public byte[] getData(){
      loadData();
      return(data);
   }
   
   public void setData(byte[] data){
      this.data = data;
   }
   
   public void setAttributes(JWave other){
      this.ChunkID = other.ChunkID;
      this.ChunkSize = other.ChunkSize;
      this.Format = other.Format;
      this.Subchunk1ID = other.Subchunk1ID;
      this.Subchunk1Size = other.Subchunk1Size;
      this.AudioFormat = other.AudioFormat;
      this.NumChannels = other.NumChannels;
      this.SampleRate = other.SampleRate;
      this.ByteRate = other.ByteRate;
      this.BlockAlign = other.BlockAlign;
      this.BitsPerSample = other.BitsPerSample;
      this.Subchunk2ID = other.Subchunk2ID;
      this.Subchunk2Size = other.Subchunk2Size;
   }
   
   private void loadData(){
      try{
         FileInputStream fis = new FileInputStream(f);
         //read raw bytes and convert to ints and shorts
         fis.skip(44);
         int available = fis.available();
         data = new byte[available];
         fis.read(data);
         fis.close();
         
      }
      catch(Exception e){
         e.printStackTrace();
      }
      
   }
   
   private void writeData(FileOutputStream fos){
      try{
         fos.write(data);
      }
      catch(IOException e){
         System.out.println(e.toString());
      }
   }
   
   private void loadAllData(){
      loadAttributes();
      loadData();
   }
   
   public void writeAllData(String file){
      try{
         FileOutputStream fos = new FileOutputStream(new File(file));
         Subchunk2Size = data.length;
         writeAttributes(fos);
         writeData(fos);
         fos.close();
      }catch(Exception e){
         e.printStackTrace();
      }
   }
   
   public void writeAllData(){
      try{
         FileOutputStream fos = new FileOutputStream(f);
         Subchunk2Size = data.length;
         writeAttributes(fos);
         writeData(fos);
         fos.close();
      }catch(Exception e){
         e.printStackTrace();
      }
   }
   
   public InputStream getInputStream(){
      try{
         return(new FileInputStream(f));
      }catch(Exception e){
         System.out.println(e);
      }
      return(null);
   }
}