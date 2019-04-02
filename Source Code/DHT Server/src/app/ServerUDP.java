import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerUDP{
    int port = 20420;
    DatagramSocket dsocket;
    byte[] buffer;
    DatagramPacket packet;
    String msg;

    public String getMsg() {
        return msg;
    }

    ServerUDP(){
        this.buffer = new byte[2048];
    }

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
