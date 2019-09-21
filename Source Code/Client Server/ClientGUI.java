package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientGUI implements Initializable {
    private String shareDir, saveDir, dhtID, dhtIP;
    @FXML
    private TextField toSaveText, toShareText, ip, id;
    @FXML
    private Button select, select1, accept;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        select.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(toSaveText.getScene().getWindow());

            if(selectedDirectory == null){
                //No Directory selected
            }else{
                shareDir = selectedDirectory.getAbsolutePath();
                toShareText.setText(shareDir);
            }
        });
        select1.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(toSaveText.getScene().getWindow());
            if(selectedDirectory == null){
                //No Directory selected
            }else{
                saveDir = selectedDirectory.getAbsolutePath();
                toSaveText.setText(saveDir);
            }
        });
        accept.setOnAction(event -> {
            if(!id.getText().isEmpty() && !ip.getText().isEmpty() && !toShareText.getText().isEmpty() && !toSaveText.getText().isEmpty()) {
                dhtID = id.getText();
                dhtIP = ip.getText();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RequestGUI.fxml"));
                RequestGUI requestGUI = new RequestGUI(shareDir, saveDir, dhtIP, dhtID);
                fxmlLoader.setController(requestGUI);
                Parent root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 600, 400);
                Stage stage = new Stage();
                stage.setTitle("P2P Client Application");
                stage.setScene(scene);
                stage.setOnHidden(e -> {
                    requestGUI.stop();
                    Platform.exit();
                });
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });
    }

    public String getShareDir() {
        return shareDir;
    }

    public void setShareDir(String shareDir) {
        this.shareDir = shareDir;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }
}
