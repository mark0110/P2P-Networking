import java.io.IOException;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        InetAddress IPaddress = InetAddress.getLocalHost();

        System.out.println(IPaddress.getHostAddress());
        System.out.print("Enter ID: ");

        Scanner scan = new Scanner(System.in);

        int id = scan.nextInt();
        System.out.print("Enter successor IP: ");
        String sucIP = scan.next();
        System.out.print("Enter predecessor IP: ");
        String preIP = scan.next();

        Hashtable<Integer, String> h = new Hashtable<Integer, String>();

        Server server = new Server(5000);

    }

}