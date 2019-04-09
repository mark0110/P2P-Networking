import java.net.*;
import java.io.*;

//Class to initialize the TCP server for the program ro accept packets through TCP
public class ServerTCP {

    //initialization
    private Socket		 socket = null;
    private ServerSocket server = null;
    private DataInputStream in	 = null;
    String msg = null;

    public String getMsg() {
        return msg;
    }

    //creating the TCP socket with the pre-specified port
    public ServerTCP() {
        int port = 20420;
        try {
            server = new ServerSocket(port);
            socket = server.accept();
            in = new DataInputStream(socket.getInputStream());
            msg = in.readUTF();

            in.close();
            socket.close();
            server.close();

        } catch(IOException i) {
            System.out.println(i);
        }
    }
}