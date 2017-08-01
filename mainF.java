/*
  # E/12/278
  # E/12/274
  # GROUP 22
  
  * This is iteration 1 of CO324 project 1.
  * In this iteration we connect 2 hosts using IP and port
  * This code will run good condition in networks which delay is not higher than 150ms.
  * Also for this iteration program do not contain any reordering or calculating packet missing methods.

*/
import java.util.*;
import java.io.*;
import java.net.*;

public class mainF{
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter 1 for Connerct 1 host or 2 for multicast");
    int select = sc.nextInt();
    
    if(select == 1){
        System.out.println("Enter port number");
        String p = sc.next();
        System.out.println("Enter IP address");
        String ip = sc.next();
        try {
            int port = Integer.parseInt( p ) ;
            InetAddress host = InetAddress.getByName( ip ) ;
            Send s = new Send(port,host);
            Receive r = new Receive(port,host);
            s.start();
            r.start();
        }catch( Exception e ) {
            System.out.println( e ) ;
        }
    } else if(select == 2){
        System.out.println("Enter port number");
        String p = sc.next();
        System.out.println("Enter IP address");
        String ip = sc.next();
        try{
            int port = Integer.parseInt( p ) ;
            InetAddress host = InetAddress.getByName( ip ) ;
            SendMulti sm = new SendMulti(port,host);
            ReceiveMulti rm = new ReceiveMulti(port, host);
            sm.start();
            rm.start();
        } catch(Exception e){
            System.out.println( e ) ;
        }
    }
  }
  
}