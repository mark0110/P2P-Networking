package app;
import java.io.File;
import java.io.IOException;
import java.net.*;

public class App {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", "1", "localhost");
       // client.initialize();
        client.informAndUpdate();
    }
}