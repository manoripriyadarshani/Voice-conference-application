import java.io.*;
import java.util.*;
import java.net.*;
import javax.sound.sampled.*;
import java.nio.ByteBuffer;

public class Receive extends Thread{
   private static InetAddress IP_HOST   = null ;
   private static int PORT = 0 ;
   //private static int cnt = 0;
   
   public Receive(int port, InetAddress host) {
      this.IP_HOST = host;
      this.PORT = port;
  }
  
  public void run() {
    byte b[] = null ;
    while( true ) {
      b = receive() ; 
      toSpeaker( b ) ;
    }        
  }
  
  public static byte[] receive() {
    try {
      DatagramSocket socket = new DatagramSocket(PORT) ; 
      byte sound[] = new byte[1000] ;
      DatagramPacket datagram = new DatagramPacket( sound , sound.length , IP_HOST , PORT ) ;
      socket.receive( datagram ) ;
      socket.close() ;
      //System.out.write(sound);
      //cntReceive cr = new cntReceive(PORT, IP_HOST, cnt);
      //cr.start();
      return datagram.getData() ;
    }catch( Exception e ) {
      System.out.println("Unable to Receive UDP packets" ) ;   
      return null ;
    } 
  }
  
  public static void toSpeaker( byte sound[] ) {
    try{  
      DataLine.Info dataLineInfo = new DataLine.Info( SourceDataLine.class , getAudioFormat() ) ;
      SourceDataLine sourceDataLine = (SourceDataLine)AudioSystem.getLine( dataLineInfo );
      sourceDataLine.open( getAudioFormat() ) ;
      sourceDataLine.start();
      
      int cnt = 0;
      
      sourceDataLine.write( sound , 0, sound.length );
      sourceDataLine.drain() ;
      sourceDataLine.close() ;
    } catch(Exception e ) {
      System.out.println("Speaker is not working\n" ) ;
    }
  }
  
  public static AudioFormat getAudioFormat() {
    float sampleRate = 8000.0F;
    int sampleSizeInBits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = false;
    return new AudioFormat( sampleRate, sampleSizeInBits, channels, signed, bigEndian );
  }
}
