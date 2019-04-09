import java.net.DatagramPacket;
import java.net.DatagramSocket;

//class to initialize the UDP server for the program to accept UDP requests
public class ServerUDP{

    //initializing of variables
    int port = 20099;
    DatagramSocket dsocket;
    byte[] buffer;
    DatagramPacket packet;
    String msg;

    public String getMsg() {
        return msg;
    }

    ServerUDP(){
        this.buffer = new byte[8192];
    }

    //starting the server
    public void start(){
        try {
            dsocket = new DatagramSocket(port);
            packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                dsocket.receive(packet);
                msg = new String(buffer, 0, packet.getLength());
                packet.setLength(buffer.length);
                dsocket.close();
            }


        } catch (Exception e){

        }
    }
}
