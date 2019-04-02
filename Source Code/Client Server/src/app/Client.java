package app;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class Client {
    private String hostIP;
    private HashMap<String, String> dhtServers;

    public Client(String hostIP, String dhtID, String dhtIP){
        this.hostIP = hostIP;
        dhtServers = new HashMap<>();
        dhtServers.put(dhtID, dhtIP);
    }

    private int getHashValue(String name){
        int hashValue = 0;
        for(int i = 0; i < name.length(); i++){
            hashValue += (int) name.charAt(i);
        }
        hashValue = hashValue % 4;
        return hashValue;
    }

    public void initialize(){
        String request = "1;";
        byte[] buf = request.getBytes();
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress localIp = InetAddress.getLocalHost();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, localIp, 20420);
            datagramSocket.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            datagramSocket.receive(packet);
            String response = new String(packet.getData(), 0, packet.getLength());
            String[] responses = response.split(";");
            int numServers = Integer.parseInt(responses[1]);
            for(int i = 3; i < 1 + (numServers * 2); i = i + 2){
                dhtServers.put(responses[i], responses[i+1]);
            }
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void informAndUpdate(){
        try {
            File folder = new File("./src/app/Photos");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    int hashValue = getHashValue(listOfFiles[i].getName());
                    String message = "2;" + listOfFiles[i].getName() + ";" + hostIP;
                    sendUDP(dhtServers.get("1"), message);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String sendUDP(String address, String message) {
        byte[] buf = message.getBytes();
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(address);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, 20420);
            datagramSocket.send(packet);
           // packet = new DatagramPacket(buf, buf.length);
            //datagramSocket.receive(packet);
            //String response = new String(packet.getData(), 0, packet.getLength());
            //return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
