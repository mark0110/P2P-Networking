package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Set;

public class Client {
    private String hostIP;
    private HashMap<String, String> dhtServers;

    public Client(String hostIP, String dhtID, String dhtIP) {
        this.hostIP = hostIP;
        dhtServers = new HashMap<>();
        dhtServers.put(dhtID, dhtIP);
    }

    public String getHostIP() {
        return hostIP;
    }

    public HashMap<String, String> getDhtServers() {
        return dhtServers;
    }

    private int getHashValue(String name) {
        int hashValue = 0;
        for (int i = 0; i < name.length(); i++) {
            hashValue += (int) name.charAt(i);
        }
        hashValue = hashValue % 4;
        return hashValue;
    }

    public void initialize() {
        String message = "1;";
        Set<String> keys = dhtServers.keySet();
        String address = dhtServers.get(keys.iterator().next());
        String response = sendUDP(address, message);
        String[] responses = response.split(";");
        int numServers = Integer.parseInt(responses[1]);
        for (int i = 3; i < 1 + (numServers * 2); i = i + 2) {
            dhtServers.put(responses[i], responses[i + 1]);
        }

    }

    public void informAndUpdate() {
        try {
            File folder = new File("./src/app/Photos");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    int hashValue = getHashValue(listOfFiles[i].getName());
                    String message = "2;" + listOfFiles[i].getName() + ";" + hostIP;
                    sendUDP(dhtServers.get(Integer.toString(hashValue)), message);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String queryContent(String name) throws FileNotFoundException {
        int hashValue = getHashValue(name);
        String dhtIP = dhtServers.get(Integer.toString(hashValue));
        String message = "3;" + name;
        String response = sendUDP(dhtIP, message);
        //Change split character to ":" or "|"
        String[] responses = response.split(";");
        if (responses[2].equals("404 Content Not Found")) {
            throw new FileNotFoundException("The content you are looking for is not ");
        }
        return responses[2];
    }

    public void exit() {
        String address = dhtServers.get("1");
        String message = "4;" + hostIP;
        sendUDP(address, message);
    }

    private String sendUDP(String address, String message) {
        byte[] buf = message.getBytes();
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(address);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, 20420);
            datagramSocket.send(packet);
            if(message.startsWith("1") || message.startsWith("3")) {
                packet = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(packet);
                String response = new String(packet.getData(), 0, packet.getLength());
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
