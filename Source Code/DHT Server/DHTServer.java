import java.io.IOException;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.Scanner;

//Main class to run. Initializes 2 threads, one for UPD and the other for TCP
public class DHTServer {

    public static ServerInstance server;

    public static void main(String[] args) throws IOException {

        InetAddress IPaddress = InetAddress.getLocalHost();

        System.out.println(IPaddress.getHostAddress());
        System.out.print("Enter ID: ");

        Scanner scan = new Scanner(System.in);

        int id = scan.nextInt();
        System.out.print("Enter successor server IP: ");
        String sucIP = scan.next();

        server = new ServerInstance(id, sucIP, IPaddress.getHostAddress());

        Thread udp = new ThreadUDP();
        udp.start();

        System.out.println("UDP server has been successfully initialized!");

        Thread tcp = new ThreadTCP();
        tcp.start();

        System.out.println("TCP server has been successfully initialized!\n\nAll systems working properly!");

    }

}