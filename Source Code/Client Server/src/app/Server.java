package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private ServerSocket socket = null;
    private int port = 20420;
    private boolean stopped = false;
    private Thread runningThread = null;

    public Server(int port){
        this.port = port;
    }

    public void run(){
        synchronized (this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = socket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
            new Thread(
                    new TCPServerThread(
                            clientSocket, "Multithreaded Server")
            ).start();
        }
        System.out.println("Server Stopped.") ;

    }
    private synchronized boolean isStopped() {
        return stopped;
    }

    public synchronized void stop(){
        stopped = true;
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

}