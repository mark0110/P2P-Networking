package app;

import java.io.*;
import java.net.Socket;

public class TCPServerThread implements Runnable {

        protected Socket socket = null;
        protected String serverText   = null;

        public TCPServerThread(Socket socket, String serverText) {
            this.socket = socket;
            this.serverText   = serverText;
        }

        public void run() {
            try {
                System.out.println("connected");
                Thread.sleep(500);
                DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                String in = input.readUTF();
                System.out.println(in);
                output.writeUTF("Hello its the server!!");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

