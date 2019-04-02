import java.net.*;
import java.io.*;

public class ClientTCP {

    private Socket socket = null;
    private DataOutputStream out = null;

    public ClientTCP(String address, String msg) {
        int port = 20420;

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
