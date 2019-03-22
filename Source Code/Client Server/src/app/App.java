package app;
import java.io.File;
import java.io.IOException;
import java.net.*;

public class App {
    public static void main(String[] args) {
        getOtherIP("");
        try {
            File folder = new File("./src/app/Photos");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("File " + listOfFiles[i].getName() + ":" + hashValue(listOfFiles[i].getName()));
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
}
    private static int hashValue(String name){
        int hashValue = 0;
        for(int i = 0; i < name.length(); i++){
            hashValue += (int) name.charAt(i);
        }
        hashValue = hashValue % 4;
        return hashValue;
    }
    private static String[] getOtherIP(String ip){
        String test = "Hello!!";
        byte[] buf = test.getBytes();
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress localIp = InetAddress.getLocalHost();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, localIp, 20101);
            datagramSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}