package app;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String myIp = "";
        try {
            Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces();
            NicsLoop:
            while (nics.hasMoreElements()) {
                NetworkInterface nic = nics.nextElement();
                Enumeration<InetAddress> addrs = nic.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    InetAddress addr = addrs.nextElement();
                    String[] ips = addr.getHostAddress().split("\\.");
                    if(ips.length > 2 && ips[0].equals("192") && ips[1].equals("168")){
                        myIp = "" + addr.getHostAddress();
                        break NicsLoop;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello!, My Ip is: " + myIp);
        System.out.println("Please enter the ID of the initial DHT Server: ");
        String dhtID = scanner.nextLine();
        System.out.println("Thank You!\nPlease enter the IP of the Initial DHT Server:");
        String dhtIP = scanner.nextLine();
        Client client = new Client(myIp, dhtID, dhtIP);
        System.out.println(client.getHostIP());
        System.out.println(client.getDhtServers());
       // client.initialize();
      //  client.informAndUpdate();
    }
}