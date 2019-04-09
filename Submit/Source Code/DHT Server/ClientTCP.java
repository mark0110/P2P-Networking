import java.net.*;
import java.io.*;

//Class that sends out one String using a TCP connection
//Takes in 2 Strings - the message adn the IP address where the msg should be sent to
public class ClientTCP {

    //initializations of variables
    private Socket socket = null;
    private DataOutputStream out = null;

    //constructor
    public ClientTCP(String address, String msg) {
        System.out.println("TCP sent: "+ msg);
        //port number
        int port = 20420;

        //the opening of a socket and sending out the msg based on the IP and the port
        try {
            socket = new Socket(address, port);
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(msg);
            out.flush();

            out.close();
            socket.close();
        }
        catch(UnknownHostException u) {
            System.out.println(u);
        }
        catch(IOException i) {
            System.out.println(i);
        }
    }
}
