package app;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String myIp = null;
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            myIp = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello!, My Ip is: " + myIp);
        System.out.println("Please enter the ID of the initial DHT Server: ");
        String dhtID = scanner.nextLine();
        System.out.println("Thank You!\nPlease enter the IP of the Initial DHT Server:");
        String dhtIP = scanner.nextLine();
        Client client = new Client(myIp, dhtID, dhtIP);
      //  client.initialize();
        client.informAndUpdate();
        try {
            System.out.println(client.queryContent("doggo.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.exit();
    }
}