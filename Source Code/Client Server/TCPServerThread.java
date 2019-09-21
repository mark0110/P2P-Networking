package app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class TCPServerThread implements Runnable {

    protected Socket socket = null;
    protected String serverText = null;
    private DataInputStream input;
    private DataOutputStream output;
    private String shareDir;

    public TCPServerThread(Socket socket, String serverText, String shareDir) {
        this.socket = socket;
        this.serverText = serverText;
        this.shareDir = shareDir;
    }

    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendFile();
    }

    private int sendFile(){
        String fileString = "";
        try {
            fileString = input.readUTF();
        } catch (IOException e) {
            try {
                output.writeUTF("400");
                return -1;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        String[] file = fileString.split(" ");
        try {
            if(!file[0].equals("GET")) {
                try {
                    output.writeUTF("400");
                    return -1;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if(!file[1].substring(5, 8).equals("1.1")) {
                try {
                    output.writeUTF("505");
                    return -1;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            BufferedImage image = ImageIO.read(new File(shareDir + File.separator + file[3]));
            output.writeUTF("200");
            ImageIO.write(image, "jpg", output);
            output.close();
        } catch (IOException e) {
            try {
                output.writeUTF("404");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return 0;
    }
}

