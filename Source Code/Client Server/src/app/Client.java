package app;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
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
        hashValue = hashValue % 1;
        return hashValue;
    }

    public void initialize() {
        String message = "1;" + hostIP;
        Set<String> keys = dhtServers.keySet();
        String address = dhtServers.get(keys.iterator().next());
        String response = sendUDP(address, message);
        String[] responses = response.split(";");
        for (int i = 2; i < responses.length; i = i + 2) {
            dhtServers.put(responses[i], responses[i + 1]);
        }
        System.out.println(dhtServers);

    }

    public void informAndUpdate() {
        try {
            File folder = new File("./src/app/Photos");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    int hashValue = getHashValue(listOfFiles[i].getName());
                    String message = "2;" + hostIP + ";" + listOfFiles[i].getName() + ";" + hostIP;
                    sendUDP(dhtServers.get(Integer.toString(hashValue)), message);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public String queryContent(String name) throws FileNotFoundException {
        int hashValue = getHashValue(name);
        String dhtIP = dhtServers.get(Integer.toString(hashValue));
        String message = "3;" +hostIP  + ";" + name;
        String response = sendUDP(dhtIP, message);
        //Change split character to ":" or "|"
        String[] responses = response.split(";");
        if (responses[3].equals("404 Content Not Found")) {
            throw new FileNotFoundException("The content you are looking for is not ");
        }
        return responses[3];
    }

    public void exit() {
        String address = dhtServers.get("0");
        String message = "4;" + hostIP;
        sendUDP(address, message);
    }

    public void getContent(String contentName, String serverIP){
        String HTTPGetMessage = "GET HTTP/1.1\nHost: " + contentName;
        String response = "";
        try {
            Socket socket = new Socket(serverIP, 20420);
            DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            output.writeUTF(HTTPGetMessage);
            Thread.sleep(500);
            response = input.readUTF();
            if(!response.isEmpty()){
                System.out.println(response);
                socket.close();
            }
        } catch (IOException | InterruptedException e) {
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
            datagramSocket.close();
            if(message.startsWith("1") || message.startsWith("3")) {
                DatagramSocket reciever = new DatagramSocket(20420);
                byte[] buffer = new byte[2048];
                DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
                reciever.receive(recPacket);
                String response = new String(recPacket.getData(), 0, recPacket.getLength());
                reciever.close();
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
