package Gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LoginController {
    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox loginContainer;

    @FXML
    private VBox registerContainer;

    @FXML
    private TextField loginEmail;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private TextField registerEmail;

    @FXML
    private TextField registerName;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private PasswordField registerConfirmPassword;

    @FXML
    private DatePicker registerDOB;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private Hyperlink loginLink;

    @FXML
    public void initialize(){
        registerContainer.setVisible(false);
        registerContainer.setManaged(false);
        loginContainer.setVisible(true);
        loginContainer.setManaged(true);
    }

    @FXML
    public void handleLogin(){
        Alert incorrectPassword = new Alert((Alert.AlertType.ERROR));
        incorrectPassword.setTitle("Fly High Travel Planner");
        incorrectPassword.setContentText("Your password is incorrect!");
        incorrectPassword.setHeaderText(null);
        Stage stage1 = (Stage) incorrectPassword.getDialogPane().getScene().getWindow();
        stage1.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        Alert incorrectEmail = new Alert((Alert.AlertType.ERROR));
        incorrectEmail.setTitle("Fly High Travel Planner");
        incorrectEmail.setContentText("Your email id is incorrect!");
        incorrectEmail.setHeaderText(null);
        Stage stage2 = (Stage) incorrectEmail.getDialogPane().getScene().getWindow();
        stage2.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        Alert emptyAlert = new Alert((Alert.AlertType.WARNING));
        emptyAlert.setTitle("Fly High Travel Planner");
        emptyAlert.setContentText("Both email and password are required!");
        emptyAlert.setHeaderText(null);
        Stage stage3 = (Stage) emptyAlert.getDialogPane().getScene().getWindow();
        stage3.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:Tour.db");
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM user";
            statement.execute(query);
            ResultSet results = statement.getResultSet();
            boolean correctEmail = false;

            if (loginEmail.getText().trim().equals("") || loginPassword.getText().trim().equals("")){
                emptyAlert.showAndWait();
            } else {
                while (results.next()) {
                    if (results.getString("email").equals(loginEmail.getText())) {
                        correctEmail = true;
                        HomeController.email = loginEmail.getText();
                        if (results.getString("password").equals(loginPassword.getText())){
                            PageLoader pageLoader = new PageLoader();
                            pageLoader.loadPage("HOME",borderPane);
                            break;
                        }
                        incorrectPassword.showAndWait();
                        break;
                    }
                }

                if (!correctEmail)
                    incorrectEmail.showAndWait();
            }

            statement.close();
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegister(){
        Alert invalidPassword = new Alert((Alert.AlertType.WARNING));
        invalidPassword.setTitle("Fly High Travel Planner");
        invalidPassword.setContentText("Your password must be minimum 8 characters and " +
                "contain at least 1 uppercase, 1 lowercase, 1 digit and 1 special character!");
        invalidPassword.setHeaderText(null);
        Stage stage4 = (Stage) invalidPassword.getDialogPane().getScene().getWindow();
        stage4.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        Alert invalidEmail = new Alert((Alert.AlertType.WARNING));
        invalidEmail.setTitle("Fly High Travel Planner");
        invalidEmail.setContentText("Please enter a valid email id!");
        invalidEmail.setHeaderText(null);
        Stage stage5 = (Stage) invalidEmail.getDialogPane().getScene().getWindow();
        stage5.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        Alert emptyAlert = new Alert((Alert.AlertType.WARNING));
        emptyAlert.setTitle("Fly High Travel Planner");
        emptyAlert.setContentText("All the fields are required!");
        emptyAlert.setHeaderText(null);
        Stage stage6 = (Stage) emptyAlert.getDialogPane().getScene().getWindow();
        stage6.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        Alert mismatchAlert = new Alert((Alert.AlertType.WARNING));
        mismatchAlert.setTitle("Fly High Travel Planner");
        mismatchAlert.setContentText("Passwords do not match!");
        mismatchAlert.setHeaderText(null);
        Stage stage7 = (Stage) mismatchAlert.getDialogPane().getScene().getWindow();
        stage7.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        Alert invalidDOB = new Alert((Alert.AlertType.WARNING));
        invalidDOB.setTitle("Fly High Travel Planner");
        invalidDOB.setContentText("Please select a valid date of birth! You should be more than 13 years of age to use this" +
                " application!");
        invalidDOB.setHeaderText(null);
        Stage stage8 = (Stage) invalidDOB.getDialogPane().getScene().getWindow();
        stage8.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        Alert duplicateRegistration = new Alert((Alert.AlertType.ERROR));
        duplicateRegistration.setTitle("Fly High Travel Planner");
        duplicateRegistration.setContentText("Account with this email id already exists! Please choose another email id.");
        duplicateRegistration.setHeaderText(null);
        Stage stage9 = (Stage) duplicateRegistration.getDialogPane().getScene().getWindow();
        stage9.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/icon.png")));

        try{
            Connection connection = DriverManager.getConnection("jdbc:sqlite:Tour.db");
            Statement statement = connection.createStatement();

            if (registerEmail.getText().trim().equals("") || registerPassword.getText().trim().equals("")
            || registerConfirmPassword.getText().trim().equals("") || registerName.getText().trim().equals("")
                    || registerDOB.getEditor().getText().trim().equals("")){
                emptyAlert.showAndWait();
            } else {

                String email = registerEmail.getText().trim();
                String name = registerName.getText().trim();
                String password = registerPassword.getText().trim();
                String confirmPassword = registerConfirmPassword.getText().trim();
                LocalDate dob = registerDOB.getValue();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateOfBirth = dob.format(myFormatObj);

                if (emailIsValid(email)){
                    if (passwordIsValid(password)){
                        if (passwordsMatch(password, confirmPassword)){
                            if (dobIsValid(dob)){
                               if (duplicateRegistration(email)){
                                   duplicateRegistration.showAndWait();
                               } else {
                                   String query = "INSERT INTO user VALUES ('" + email + "', '" + password + "', " +
                                           "'" + name + "', '" + dateOfBirth + "');";
                                   statement.execute(query);
                                   HomeController.email = email;
                                   PageLoader pageLoader = new PageLoader();
                                   pageLoader.loadPage("HOME",borderPane);
                               }
                            } else{
                                invalidDOB.showAndWait();
                            }
                        } else {
                            mismatchAlert.showAndWait();    
                        }
                    } else{
                        invalidPassword.showAndWait();
                    }
                } else {
                    invalidEmail.showAndWait();
                }
                }

            statement.close();
            connection.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLoginToRegister(){
        loginContainer.setVisible(false);
        loginContainer.setManaged(false);
        registerContainer.setVisible(true);
        registerContainer.setManaged(true);
    }

    @FXML
    public void handleRegisterToLogin(){
        registerContainer.setVisible(false);
        registerContainer.setManaged(false);
        loginContainer.setVisible(true);
        loginContainer.setManaged(true);
    }

    @FXML
    public void handleHyperlinkHover(){
        registerLink.setStyle("-fx-text-fill: #f5f3f3; -fx-font-weight: bold;");
        loginLink.setStyle("-fx-text-fill: #f5f3f3; -fx-font-weight: bold;");
    }

    @FXML
    public void handleHyperlinkExit(){
        registerLink.setStyle("-fx-text-fill: #09244f; -fx-font-weight: bold;");
        loginLink.setStyle("-fx-text-fill: #09244f; -fx-font-weight: bold;");
    }

    @FXML
    public void handleLoginEnterKey(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
            handleLogin();
        }
    }

    @FXML
    public void handleRegisterEnterKey(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)) {
            handleRegister();
        }
    }

    public boolean emailIsValid(String email){
        for (int i=0; i<email.length(); i++){
            char ch = email.charAt(i);
            if(ch=='@'){
                for (int j=i+1; j<email.length(); j++){
                    char ch1 = email.charAt(j);
                    if (ch1=='.')
                        return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean passwordIsValid(String password) {
        if (password.length() < 8) {
            return false;
        } else {
            boolean upperCasePresent = false;
            boolean lowerCasePresent = false;
            boolean specialCharPresent = false;
            boolean digitPresent = false;
            for (int i = 0; i < password.length(); i++) {
                char ch = password.charAt(i);
                if (ch >= 48 && ch <= 57)
                    digitPresent = true;
                else if (ch >= 65 && ch <= 90)
                    upperCasePresent = true;
                else if (ch >= 97 && ch <= 122)
                    lowerCasePresent = true;
                else
                    specialCharPresent = true;
            }

            if (upperCasePresent && lowerCasePresent && digitPresent && specialCharPresent)
                return true;
            else
                return false;
        }
    }

    public boolean passwordsMatch(String password, String confirmPassword){
        if (password.equals(confirmPassword))
            return true;
        else
            return false;
    }

    public boolean dobIsValid(LocalDate dob){
        LocalDate today = LocalDate.now();
        int currentDay = today.getDayOfMonth();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int selectedDay = dob.getDayOfMonth();
        int selectedMonth = dob.getMonthValue();
        int selectedYear = dob.getYear();
        if (currentYear-selectedYear > 13)
            return true;
        else if (currentYear-selectedYear < 13)
            return false;
        else {
            if (currentMonth-selectedMonth > 0)
                return true;
            else if (currentMonth-selectedMonth < 0)
                return false;
            else {
                if (currentDay-selectedDay >= 0)
                    return true;
                else
                    return false;
            }
        }
    }

    public boolean duplicateRegistration(String email){
       try {

           Connection connection1 = DriverManager.getConnection("jdbc:sqlite:Tour.db");
           Statement statement1 = connection1.createStatement();

           String query = "SELECT * FROM user";
           statement1.execute(query);
           ResultSet results1 = statement1.getResultSet();

           while (results1.next()) {
               if (results1.getString("email").equals(email)){
                   statement1.close();
                   connection1.close();
                   return true;
               }
           }

           statement1.close();
           connection1.close();
           return false;

       } catch (Exception e){
           e.printStackTrace();
           return true;
       }
    }
}