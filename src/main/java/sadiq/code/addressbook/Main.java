package sadiq.code.addressbook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200 , 800);
        stage.setTitle("Address Book");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:download.png"));
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}