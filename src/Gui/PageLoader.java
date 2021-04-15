package Gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageLoader {

    private String pageFile;

    public void loadPage(String button, BorderPane borderPane) {
        try {

            switch (button) {
                case "CONTACT":
                    pageFile = "Contact.fxml";
                    break;
                case "HOME":
                    pageFile = "Home.fxml";
                    break;
                case "ABOUT":
                    pageFile = "About.fxml";
                    break;
                default:
                    pageFile = "Tours.fxml";
                    break;
            }

            borderPane.getChildren().removeAll();
            BorderPane pane = FXMLLoader.load(getClass().getResource(pageFile));
            borderPane.getChildren().setAll(pane);
        }
        catch (IOException e){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
