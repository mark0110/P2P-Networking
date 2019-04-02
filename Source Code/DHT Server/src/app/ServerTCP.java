import java.net.*;
import java.io.*;

public class ServerTCP {

    private Socket		 socket = null;
    private ServerSocket server = null;
    private DataInputStream in	 = null;
    String msg = null;

    public String getMsg() {
        return msg;
    }

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