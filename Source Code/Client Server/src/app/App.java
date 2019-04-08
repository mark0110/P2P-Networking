package app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application  {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("P2P Client Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}