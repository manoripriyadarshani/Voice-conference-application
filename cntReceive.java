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

public class cntReceive extends Thread{
    private static InetAddress IP_HOST   = null ;
    private static int PORT= 0 ;
    private static int CNT = 0;
    private static int countof = 0;
    
    public cntReceive(int port, InetAddress ip, int cnt){
        this.PORT = port+1;
        this.IP_HOST = ip;
        this.CNT = cnt;
    }
    
    public static void crun() {
    try {
      DatagramSocket socket = new DatagramSocket(PORT) ; 
      byte count[] = new byte[2] ;
      DatagramPacket datagram = new DatagramPacket( count , count.length , IP_HOST , PORT ) ;
      socket.receive( datagram ) ;
      countof = (int) count[0];
      socket.close() ;
      System.out.format("%d packets are lost", (1000-countof));
      //System.out.write(sound);
    }catch( Exception e ) {
      System.out.println("Unable to Receive UDP packets" ) ;   
    } 
  }
}
