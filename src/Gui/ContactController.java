package Gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class ContactController {

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
    private Label copyInfo;

    @FXML
    private Button website;

    @FXML
    private Button facebook;

    @FXML
    private Button instagram;

    @FXML
    private Button linkedin;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        copyInfo.setText("Developed By - \nSoul & Fuel Software Solutions Pvt. Ltd. 2020 ©");
        Tooltip facebookTip = new Tooltip("Our Facebook Page");
        Tooltip instagramTip = new Tooltip("Our Instagram Page");
        Tooltip linkedinTip = new Tooltip("Our Linkedin Profile");
        Tooltip websiteTip = new Tooltip("Our Website");
        facebook.setTooltip(facebookTip);
        instagram.setTooltip(instagramTip);
        linkedin.setTooltip(linkedinTip);
        website.setTooltip(websiteTip);
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
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

    @FXML
    public void handleLinkedinHover(){
        linkedin.setScaleX(1.3);
        linkedin.setScaleY(1.3);
    }

    @FXML
    public void handleLinkedinExit(){
        linkedin.setScaleX(1);
        linkedin.setScaleY(1);
    }

    @FXML
    public void handleFacebookHover(){
        facebook.setScaleX(1.3);
        facebook.setScaleY(1.3);
    }

    @FXML
    public void handleFacebookExit(){
        facebook.setScaleX(1);
        facebook.setScaleY(1);
    }

    @FXML
    public void handleInstagramHover(){
        instagram.setScaleX(1.3);
        instagram.setScaleY(1.3);
    }

    @FXML
    public void handleInstagramExit(){
        instagram.setScaleX(1);
        instagram.setScaleY(1);
    }

    @FXML
    public void handleWebsiteHover(){
        website.setScaleX(1.3);
        website.setScaleY(1.3);
    }

    @FXML
    public void handleWebsiteExit(){
        website.setScaleX(1);
        website.setScaleY(1);
    }

    @FXML
    public void displayFacebook(){
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/AceSoftwareSolutions/"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void displayInstagram(){
        try {
            Desktop.getDesktop().browse(new URI("https://www.instagram.com/zewiasoftware/?hl=en"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void displayLinkedin(){
        try {
            Desktop.getDesktop().browse(new URI("https://www.linkedin.com/company/soulandfuel/"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void displayWebsite(){
        try {
            Desktop.getDesktop().browse(new URI("https://www.soulandfuel.com/"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLogout() throws Exception {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage.setTitle("Fly High Travel Planner");
        stage.setScene(new Scene(root, 1080, 720 ));
        stage.resizableProperty().setValue(false);
        Image logo = new Image("/Media/icon.png");
        stage.getIcons().add(logo);
        logoutButton.getScene().getWindow().hide();
        stage.show();
    }

    @FXML
    public void handleLogoutButtonHover() {
        logoutButton.setStyle("-fx-background-color: #060145;");
    }

    @FXML
    public void handleLogoutButtonExit() {
        logoutButton.setStyle("-fx-background-color:  #09244f;");
    }
}
