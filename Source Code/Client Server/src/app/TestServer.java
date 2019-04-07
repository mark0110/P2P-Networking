package app;

public class TestServer {
    public static void main(String[] args){
        Server server = new Server(20420);
        new Thread(server).start();
        try{
            Thread.sleep(100 * 1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        server.stop();
    }
}
