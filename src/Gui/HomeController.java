package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HomeController {

    private MediaPlayer videoPlayer;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button homeButton;

    @FXML
    private Button contactButton;

    @FXML
    private Button aboutButton;

    @FXML
    private Button toursButton;

    @FXML
    private Hyperlink accountInfo;

    @FXML
    private MediaView view;

    @FXML
    private Label copyInfo;

    public static String email;


    @FXML
    public void initialize(){
        copyInfo.setText("Developed By - \nSoul & Fuel Software Solutions Pvt. Ltd. 2020 ©");
        accountInfo.setText(email);
        File path = new File("src\\Media\\cover.mp4");
        Media video = new Media(path.toURI().toString());
        videoPlayer = new MediaPlayer(video);
        view.setMediaPlayer(videoPlayer);
        videoPlayer.setAutoPlay(true);
        videoPlayer.setVolume(100);
    }

    @FXML
    public void playVideo(){
        videoPlayer.setOnEndOfMedia(this::stopVideo);
        if(videoPlayer.getStatus().equals(MediaPlayer.Status.PAUSED) || videoPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)){
            videoPlayer.play();
        }
        if(videoPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
            videoPlayer.pause();
        }
    }

    @FXML
    public void muteVideo(){
        if (videoPlayer.isMute()){
            videoPlayer.setMute(false);
        }
        else{
            videoPlayer.setMute(true);
        }
    }

    @FXML
    public void stopVideo(){
        videoPlayer.stop();
    }

    @FXML
    public void forwardVideo(){
        videoPlayer.seek(videoPlayer.getCurrentTime().add(Duration.seconds(10.0)));
    }

    @FXML
    public void rewindVideo(){
        videoPlayer.seek(videoPlayer.getCurrentTime().subtract(Duration.seconds(10.0)));
    }

    @FXML
    public void handleHyperlinkHover(){
        accountInfo.setStyle("-fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handleHyperlinkExit(){
        accountInfo.setStyle("-fx-text-fill: #09244f;");
    }

    @FXML
    public void handleLink(){
        try{

            Connection connection = DriverManager.getConnection("jdbc:sqlite:Tour.db");
            Statement statement = connection.createStatement();

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

            String query = "SELECT * FROM user";
            statement.execute(query);
            ResultSet results = statement.getResultSet();

            while (results.next()) {
                if (results.getString("email").equals(email)) {
                    String name = results.getString("name");
                    String password = results.getString("password");
                    String dob = results.getString("dob");
                    infoAlert.setTitle("Fly High Travel Planner");
                    infoAlert.setHeaderText("Account Information:");
                    infoAlert.setContentText("Name: " + name + "\nEmail: " + email + "\nPassword: " + password + "\nDate of Birth: " + dob);
                    break;
                    }
            }

            Stage stage1 = (Stage) infoAlert.getDialogPane().getScene().getWindow();
            stage1.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));
            infoAlert.showAndWait();

            statement.close();
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        stopVideo();
        PageLoader pageLoader = new PageLoader();
        pageLoader.loadPage(((Button) event.getSource()).getText(),borderPane);
    }


    @FXML
    public void handleHomeButtonHover(){
        homeButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleHomeButtonExit(){
        homeButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handleAboutButtonHover(){
        aboutButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleAboutButtonExit(){
        aboutButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handleToursButtonHover(){
        toursButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleToursButtonExit(){
        toursButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handleContactButtonHover(){
        contactButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleContactButtonExit(){
        contactButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }
}
