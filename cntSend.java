/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sasitha
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class cntSend extends Thread{
    private static InetAddress IP_HOST   = null ;
    private static int PORT= 0 ;
    private static int CNT = 0;
    
    public cntSend(int port, InetAddress ip, int cnt){
        this.PORT = port+1;
        this.IP_HOST = ip;
        this.CNT = cnt;
    }
    public void sentcnt(){
        try {
          byte count[] = new byte[2];
          count[0] = (byte)CNT;
          count[1] = (byte)CNT;
          DatagramSocket socket = new DatagramSocket() ; 
          //System.out.println (new String (sound));
          socket.send( new DatagramPacket( count , count.length ,  IP_HOST, PORT ) ) ; 
          socket.close() ;//close the socket that sen datagram packets
        }catch( Exception e ) {
          e.printStackTrace() ;
          System.out.println("Error in Sending Datagram packets\n" ) ;   
        }
    }
}
