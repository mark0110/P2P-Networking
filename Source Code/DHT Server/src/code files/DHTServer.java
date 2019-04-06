import java.io.IOException;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.Scanner;

public class DHTServer {

    public static ServerInstance server;

    public static void main(String[] args) throws IOException {

        InetAddress IPaddress = InetAddress.getLocalHost();

        System.out.println(IPaddress.getHostAddress());
        System.out.print("Enter ID: ");

        Scanner scan = new Scanner(System.in);

        int id = scan.nextInt();
        System.out.print("Enter successor IP: ");
        String sucIP = scan.next();

        server = new ServerInstance(id, sucIP, IPaddress.getHostAddress());

        Thread udp = new ThreadUDP();
        udp.start();

        Thread tcp = new ThreadTCP();
        tcp.start();

        System.out.println("test");
    }

}