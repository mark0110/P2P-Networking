package app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;

public class RequestGUI implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private Button openShare, openSave, download;
    @FXML
    private Label status;
    private String shareDir, saveDir, dhtIP, dhtID;
    private Client client;

    public RequestGUI(String shareDir, String saveDir, String dhtIP, String dhtID) {
        this.shareDir = shareDir;
        this.saveDir = saveDir;
        this.dhtIP = dhtIP;
        this.dhtID = dhtID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        download.setOnAction(event -> {
            String name = nameField.getText();
            String serverIp;
            if (!name.isEmpty()) {
                try {
                    serverIp = client.queryContent(name);
                    String response = client.getContent(name, serverIp);
                    if (response.equals("200")) {
                        status.setText("Status: File Successfully Downloaded!");
                    } else {
                        status.setText("Error: " + response + "Problem Downloading File");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    status.setText("Error 404: File not found on DHT server");
                }
            }
        });
        openSave.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(saveDir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        openShare.setOnAction(event -> {
            try {
                Desktop.getDesktop().open(new File(shareDir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        String myIp = null;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 20000);
            myIp = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        client = new Client(myIp, dhtID, dhtIP, shareDir, saveDir);
        client.initialize();
        client.informAndUpdate();
        Server server = new Server(20420);
        new Thread(server).start();
    }

    public void stop() {
        System.out.println("This Printed!");
        client.exit();
        System.out.println("This Too!!");
    }

}
