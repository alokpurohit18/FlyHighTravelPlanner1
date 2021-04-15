package Gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Fly High Travel Planner");
        primaryStage.setScene(new Scene(root, 1080, 720 ));
        primaryStage.resizableProperty().setValue(false);
        Image logo = new Image("/Media/icon.png");
        primaryStage.getIcons().add(logo);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}