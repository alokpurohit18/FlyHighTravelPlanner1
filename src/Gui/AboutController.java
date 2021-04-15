package Gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;

public class AboutController {
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
    private ScrollPane scrollPane;

    @FXML
    private Label companyInfo1;

    @FXML
    private Label companyInfo2;

    @FXML
    private Label appInfo1;

    @FXML
    private Label appInfo2;

    @FXML
    private Label copyInfo;

    @FXML
    public void initialize() {
        scrollPane.setStyle("-fx-background:  #22705a; -fx-border-color:  #22705a;");
        companyInfo1.setText("Innovation is the key to success of many businesses in the digital age. Established since 1989, " +
                "Soul & Fuel Software Solutions\nPrivate Limited focuses to help public and private organizations select, " +
                "architect and implement advanced IT solutions to\nempower their business. We provide leading edge solutions " +
                "of modernized infrastructure, IT security, cloud computing,\nmobility, CRM, digital marketing " +
                "and a broad portfolio of IT services that serve the needs of enterprises and public\norganizations " +
                "in Mumbai and in India. We are committed to deliver top quality services " +
                "by conforming to important ISO/IEC\nquality standards backed by our established best practices " +
                "and effective service management systems and processes.\n Headquartered located in Mumbai, " +
                "the company has its offices in all major cities in India including Delhi," +
                " Chennai,\nBangalore, Hyderabad, Jodhpur & others.");
        companyInfo2.setText("Head Office - Soul & Fuel Software Solutions, 256B, Luke Grant Building, St. Peter Church Colony," +
                " Vasant Vihar, Peddar Road,\nMumbai - 451408.");
        appInfo1.setText("Have you ever wondered about having someone to help plan a holiday or do you enjoy planning a trip/holiday." +
                " Well, then this\nis app is just for you. Trip planning can be a daunting task. Where do you begin? What’s step one?" +
                "What’s step two?\nWhat’s step three? It’s easy to get overwhelmed, especially when you haven’t done something like " +
                "this before —\nand especially considering just how much information there is out there these days.Blogs, social media, " +
                "and guidebooks\nhave never been more plentiful. There’s a fire hose of information out there which can sometimes make " +
                "the task of\nplanning a trip even more challenging and overwhelming.");
        appInfo2.setText("This app helps you to plan and organise a trip or a holiday. Note that at the moment this app only " +
                "works for trip within India.\nYou can select all the cities/destinations that you wish to visit during this trip. " +
                "Once you have selected all the cities\nor locations of your trip, you will be prompted about some your preferences " +
                "like travel - air, train or road. After you give\nyour preferences, the app will finally display the order in which " +
                "you need to visit all the locations to minimize the cost\nof your trip. The app will also display the mode of transport " +
                "for every part/journey of the trip i.e. between any 2 locations.");
        copyInfo.setText("Developed By - \nSoul & Fuel Software Solutions Pvt. Ltd. 2020 ©");
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        PageLoader pageLoader = new PageLoader();
        pageLoader.loadPage(((Button) event.getSource()).getText(), borderPane);
    }

    @FXML
    public void handleHomeButtonHover() {
        homeButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleHomeButtonExit() {
        homeButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handleAboutButtonHover() {
        aboutButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleAboutButtonExit() {
        aboutButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handleToursButtonHover() {
        toursButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleToursButtonExit() {
        toursButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handleContactButtonHover() {
        contactButton.setStyle("-fx-background-color: #f5f3f3; -fx-text-fill: black;");
    }

    @FXML
    public void handleContactButtonExit() {
        contactButton.setStyle("-fx-background-color:  #09244f; -fx-text-fill: #f5f3f3;");
    }
}
