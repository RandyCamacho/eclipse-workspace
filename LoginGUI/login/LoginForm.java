package login;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Stage{
    public LoginForm(){
        super();

        GridPane gp = new GridPane();
        gp.setHgap(15);
        gp.setVgap(15);
        gp.setPadding(new Insets(50));
        gp.setAlignment(Pos.CENTER);

        Text headerText = new Text("Enter username/email and password.");

        Label usernameFieldLabel = new Label("Username/email");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username or email here.");

        Label passwordFieldLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password here.");

        Label statusMessage= new Label("Incorrect username or password");
	    statusMessage.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
	    statusMessage.setVisible(false);

//        Label successMessage = new Label("Login successful!");
//        successMessage.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
//        successMessage.setTextFill(Color.FORESTGREEN);
//
//        Label errorMessage = new Label("Incorrect username or password.");
//        errorMessage.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
//        errorMessage.setTextFill(Color.FIREBRICK);

        Hyperlink forgotUsernameLink = new Hyperlink("Forgot Username");
        forgotUsernameLink.setOnAction(event -> {
            ForgotUsernameForm forgotUsernameForm = new ForgotUsernameForm(usernameField);
            forgotUsernameForm.showAndWait();
        });

        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password");
        forgotPasswordLink.setOnAction(event -> {
            ForgotPasswordForm forgotPass = new ForgotPasswordForm();
            forgotPass.showAndWait();
        });


        Button loginButton = new Button("Login");
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(event -> {
            String inputName = usernameField.getText();
            boolean userFound = false;
            for(User user: UserAccountManager.manager) {
                String userName = user.getUsername();
                String email = user.getEmail();
                if(userName.equals(inputName) || email.equals(inputName)) {
                    userFound = true;
                    if(user.getPassword().checkPassword(passwordField.getText())) {
	                    statusMessage.setText("Login successful!");
	                    statusMessage.setTextFill(Color.FORESTGREEN);
	                    statusMessage.setVisible(true);
	                    Alert popUp = new Alert(Alert.AlertType.CONFIRMATION, "Login Successful");
	                    popUp.showAndWait();
                    } else {
                    	statusMessage.setText("Incorrect username or password");
	                    statusMessage.setTextFill(Color.FIREBRICK);
	                    statusMessage.setVisible(true);
                    }
                }
            }
            if(!userFound) {
	            Alert popUp = new Alert(Alert.AlertType.WARNING, "Incorrect username or password");
	            statusMessage.setText("Incorrect username or password");
	            statusMessage.setTextFill(Color.FIREBRICK);
                statusMessage.setVisible(true);
            }
        });

        Button registerFormButton = new Button("Register");
        registerFormButton.setOnAction(event -> {
            //  Present a form to register a new user
            RegistrationForm registrationForm = new RegistrationForm();
            registrationForm.show();
        });

        gp.add(statusMessage, 0, 0);
        gp.add(headerText, 0, 1);
        gp.add(usernameFieldLabel, 0, 2);
        gp.add(new VBox(usernameField, forgotUsernameLink), 0, 3);
        gp.add(passwordFieldLabel, 0, 4);
        gp.add(new VBox(passwordField, forgotPasswordLink), 0, 5);
//        gp.add(passwordField, 0, 6);
//        gp.add(forgotPasswordLink, 0, 7);

        //Added button pane to include registration option
        HBox buttonPane = new HBox();
        buttonPane.setAlignment(Pos.CENTER_LEFT);
        buttonPane.setSpacing(10);
        buttonPane.getChildren().addAll(loginButton, registerFormButton);
        gp.add(buttonPane, 0, 6);
        //gp.add(loginButton, 0, 6);
        //gp.add(registerFormButton, 0, 7);


        Scene mainScene = new Scene(gp, 400, 500, Color.LIGHTGRAY);
        this.setScene(mainScene);
    }
}