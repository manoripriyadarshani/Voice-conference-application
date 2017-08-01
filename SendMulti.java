import java.io.*;
import java.util.*;
import java.net.*;
import javax.sound.sampled.*;

//Capture and Send Voice using UPD

public class SendMulti extends Thread{
  private static InetAddress IP_HOST   = null ;
   private static int PORT= 0 ;
   private static int cnt = 0;
   
   public SendMulti(int port, InetAddress host) {
      this.IP_HOST = host;
      this.PORT = port;
  }
  
  //main sending thread
  public void run() {    
    //geting available audio devices
    Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        //get available mixers
        
    //printing available audio mixers
		System.out.println("Available mixers:");
		Mixer mixer = null;
		for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
		  System.out.println(cnt + " " + mixerInfo[cnt].getName());
			mixer = AudioSystem.getMixer(mixerInfo[cnt]);

			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
			  System.out.println(cnt + " Mic is supported!");
				break;
			}
    }
    
    byte b[] = null ;
    System.out.format("10\n");
    toCapture(b) ;  //run audio capture method
    System.out.format("13\n");    
  }
  
  public static void toCapture( byte soundbytes[] ) {
    if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {//If audio system available start getting audio input
      try { 
        DataLine.Info dataLineInfo = new DataLine.Info( TargetDataLine.class , getAudioFormat() ) ;
        TargetDataLine targetDataLine = (TargetDataLine)AudioSystem.getLine( dataLineInfo  ) ;
        targetDataLine.open( getAudioFormat() );
        targetDataLine.start();
        
        byte tempBuffer[] = new byte[1000] ;
        int cnt = 0 ;
      
        while( true ) {
          targetDataLine.read( tempBuffer , 0 , tempBuffer.length );//reading audio input and buffer them into byte array
          send( tempBuffer ) ;//send created packet to other host
        }
      } catch(Exception e ) {
        System.out.println(" Something is not working correctly\n" ) ;
        System.exit(0) ;
      }
    }
  }
  
  public static void send( byte sound
  
  [] ) {
    try {//Creating new datagram socket and sending them to other host
      DatagramSocket socket = new DatagramSocket() ; 
      //System.out.println (new String (sound));
      if(cnt >= 1000){
        cntSend cs = new cntSend(PORT, IP_HOST, cnt);
        cs.start();
        cnt = 0;
      }
      cnt++;
      socket.send( new DatagramPacket( sound , sound.length ,  IP_HOST, PORT ) ) ; 
      socket.close() ;//close the socket that sen datagram packets
    }catch( Exception e ) {
      e.printStackTrace() ;
      System.out.println("Error in Sending Datagram packets\n" ) ;   
    }
  }
  
  public static AudioFormat getAudioFormat() {//Defining the audio format
    float sampleRate = 8000.0F;
    int sampleSizeInBits = 16;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = false;
    return new AudioFormat( sampleRate, sampleSizeInBits, channels, signed, bigEndian );
  }
}
