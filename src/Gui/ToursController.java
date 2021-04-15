package Gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class ToursController {

    private ObservableList<String> locations;

    public String[] solution;

    @FXML
    private ListView<String> listView;

    @FXML
    private ComboBox<String> source;

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
    private Button planButton;

    @FXML
    private Label copyInfo;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label output;

    @FXML
    public void initialize() {

        output.setText("");
        copyInfo.setText("Developed By - \nSoul & Fuel Software Solutions Pvt. Ltd. 2020 ©");
        listView.setStyle("-fx-background-color:  #22705a;");
        scrollPane.setStyle("-fx-background:  #22705a; -fx-border-color:  #22705a;");
        locations = FXCollections.observableArrayList();

        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:Tour.db");
            Statement statement = connection.createStatement();
            String query = "SELECT name FROM location";
            statement.execute(query);
            ResultSet results = statement.getResultSet();
            while(results.next()){
                String city = results.getString("name");
                city = city.toLowerCase();
                city = city.substring(0,1).toUpperCase() + city.substring(1);
                locations.add(city);
            }

        } catch (Exception ignored){ }

        source.getItems().addAll(locations);
        source.setStyle("-fx-font: 16px \"Britannic Bold\";");
        source.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell;
                cell = new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item);
                            Font font = new Font("Britannic Bold", 18);
                            setFont(font);
                            setTextFill(Color.valueOf("#09244f"));
                        }
                    }
                };
                return cell;
            }
        });

        listView.setItems(locations);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                ListCell<String> cell;
                cell = new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item);
                            Font font = new Font("Britannic Bold", 18);
                            setFont(font);
                            setTextFill(Color.valueOf("#09244f"));
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    public void handleSourceSelect() {
        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (source.getSelectionModel().getSelectedItem().equals(item)) {
                            setDisable(true);
                        }
                        setText(item);
                        Font font = new Font("Britannic Bold", 18);
                        setFont(font);
                        setTextFill(Color.valueOf("#09244f"));
                    }

                };
            }
        });
    }

    @FXML
    public void calculateResults() {
        Alert alert = new Alert((Alert.AlertType.WARNING));
        alert.setTitle("Empty Selection");
        alert.setContentText("Please select a source location and at least 1 location to visit.");
        alert.setHeaderText(null);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        try {
            if (listView.getSelectionModel().getSelectedItems().isEmpty() ||
                    source.getSelectionModel().isEmpty()) {
                alert.showAndWait();
            } else {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:Tour.db");
                Statement statement = connection.createStatement();

                String startPoint = source.getSelectionModel().getSelectedItem();
                ObservableList<String> locationSet = listView.getSelectionModel().getSelectedItems();
                String[] locations = new String[locationSet.size() + 2];
                locations[1] = startPoint;

                int counter = 2;
                for (String i : locationSet) {
                    locations[counter] = i;
                    counter++;
                }

                int[][] adjacency_matrix = new int[locations.length][locations.length];
                String travelType;
                int totalCost = 0;

                for (int i = 1; i < locations.length; i++) {
                    for (int j = 1; j < locations.length; j++) {
                        if (i == j) {
                            adjacency_matrix[i][j] = 0;
                        } else {
                            String query = "SELECT * FROM (travel NATURAL JOIN route) WHERE ((source = '" + locations[i].toUpperCase() +
                                    "' AND destination = '" + locations[j].toUpperCase() + "') OR (source = '" + locations[j].toUpperCase() +
                                    "' AND destination = '" + locations[i].toUpperCase() + "'))";
                            statement.execute(query);
                            ResultSet results = statement.getResultSet();

                            if(results.getInt("distance") <= 300 || results.getInt("station") == 0){
                                travelType = "Road";
                            }
                            else if(results.getInt("distance") <= 700 || results.getInt("airport") == 0){
                                travelType = "Train";
                            }
                            else{
                                travelType = "Air";
                            }

                            while (results.next()){
                                if(results.getString("type").equals(travelType.toUpperCase())){
                                    adjacency_matrix[i][j] = results.getInt("distance");
                                    totalCost = totalCost + results.getInt("cost");
                                }
                            }

                            results.close();
                        }
                    }
                }

                TSP_Solution tsp_solution = new TSP_Solution();
                solution = tsp_solution.findBestRoute(adjacency_matrix, locations);

                statement.close();
                connection.close();
                createOutput(totalCost);

            }
        } catch (Exception ignored){ }
    }

    public void createOutput(int totalCost){

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:Tour.db");
            Statement statement = connection.createStatement();

            StringBuilder outputText = new StringBuilder("- THE TRAVEL PLAN -\n\n");
            String travelType;

            for (int i=1; i<solution.length - 1 ;i++){

                String query = "SELECT * FROM (travel NATURAL JOIN route) WHERE ((source = '" + solution[i].toUpperCase() +
                        "' AND destination = '" + solution[i+1].toUpperCase() + "') OR (source = '" + solution[i+1].toUpperCase() +
                        "' AND destination = '" + solution[i].toUpperCase() + "'))";
                statement.execute(query);
                ResultSet results = statement.getResultSet();

                if(results.getInt("distance") <= 300 || results.getInt("station") == 0){
                    travelType = "Road";
                }
                else if(results.getInt("distance") <= 700 || results.getInt("airport") == 0){
                    travelType = "Train";
                }
                else{
                    travelType = "Air";
                }

                outputText.append(solution[i]).append(" to ").append(solution[i + 1]).append(". Travel By - ").append(travelType).append(".\n");
            }

            output.setText((outputText.toString()) + "\nTotal Travel Cost = Rs. " + totalCost);
            Font font = new Font("Arial Black", 18);
            output.setFont(font);
            output.setMinWidth(1000);
            output.setMaxWidth(1000);
            output.setTextFill(Color.valueOf("#09244f"));
            output.setStyle("-fx-background-color: #ffc626; -fx-background-radius: 10px;");

        } catch (Exception e){
            e.printStackTrace();
        }

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
        homeButton.setStyle("-fx-background-color: #09244f; -fx-text-fill: #f5f3f3;");
    }

    @FXML
    public void handlePlanButtonHover() {
        planButton.setStyle("-fx-background-color: #060145;");
    }

    @FXML
    public void handlePlanButtonExit() {
        planButton.setStyle("-fx-background-color:  #09244f;");
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
