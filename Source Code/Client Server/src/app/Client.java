package app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Set;

public class Client {
    private String hostIP, shareDir, saveDir;
    private HashMap<String, String> dhtServers;

    public Client(String hostIP, String dhtID, String dhtIP, String shareDir, String saveDir) {
        this.hostIP = hostIP;
        dhtServers = new HashMap<>();
        dhtServers.put(dhtID, dhtIP);
        this.shareDir = shareDir;
        this.saveDir = saveDir;
    }

    public String getHostIP() {
        return hostIP;
    }

    public HashMap<String, String> getDhtServers() {
        return dhtServers;
    }

    public void setDhtServers(HashMap<String, String> dhtServers) {
        this.dhtServers = dhtServers;
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
        String message = "1;" + hostIP;
        Set<String> keys = dhtServers.keySet();
        String address = dhtServers.get(keys.iterator().next());
        String response = sendUDP(address, message);
        String[] responses = response.split(";");
        for (int i = 2; i < responses.length; i = i + 2) {
            dhtServers.put(responses[i], responses[i + 1]);
        }
    }

    public void informAndUpdate() {
        try {
            File folder = new File(shareDir);
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
        String message = "3;" + hostIP + ";" + name;
        String response = sendUDP(dhtIP, message);
        String[] responses = response.split(";");
        if (responses[3].equals("404 Content Not Found")) {
            throw new FileNotFoundException("The content you are looking for is not found");
        }
        return responses[3];
    }

    public void exit() {
        String address = dhtServers.get("0");
        String message = "4;" + hostIP;
        sendUDP(address, message);
    }

    public String getContent(String contentName, String serverIP) {
        String HTTPGetMessage = "GET HTTP/1.1 Host: " + contentName;
        try {
            Socket socket = new Socket(serverIP, 20420);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            output.writeUTF(HTTPGetMessage);
            String response = input.readUTF();
            switch (response) {
                case "200": {
                    BufferedImage image = ImageIO.read(input);
                    File file = new File(saveDir + File.separator + contentName);
                    ImageIO.write(image, "jpg", file);
                    return "200";
                }
                case "400": {
                    return "400";
                }
                case "404": {
                    return "404";
                }
                case "505": {
                    return "505";
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "What...";
    }

    private String sendUDP(String address, String message) {
        byte[] buf = message.getBytes();
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(address);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, ip, 20420);
            datagramSocket.send(packet);
            datagramSocket.close();
            if (message.startsWith("1") || message.startsWith("3")) {
                DatagramSocket receiver = new DatagramSocket(20420);
                byte[] buffer = new byte[2048];
                DatagramPacket recPacket = new DatagramPacket(buffer, buffer.length);
                receiver.receive(recPacket);
                String response = new String(recPacket.getData(), 0, recPacket.getLength());
                receiver.close();
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
