import java.io.*;
import java.net.*;

//Class to send out a String using a UDP connection
public class ClientUDP {

    //constructor
    //takes in the message that needs to be sent
    public ClientUDP(String msg) {
        System.out.println("UDP: "+msg);
        //sending the packet with the String msg to the specified IP and port using UDP
        try {
            String[] arr = msg.split(";");
            String host = arr[1];
            int port = 20420;

            byte[] message = msg.getBytes();

            // Get the internet address of the specified host
            InetAddress address = InetAddress.getByName(host);

            // Initialize a datagram packet with data and address
            DatagramPacket packet = new DatagramPacket(message, message.length, address, port);

            // Create a datagram socket, send the packet through it, close it.
            DatagramSocket dsocket = new DatagramSocket();
            dsocket.send(packet);
            dsocket.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}