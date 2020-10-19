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

public class Login extends Stage{
    public Login(){
        super();

        GridPane mainPane = new GridPane();
        mainPane.setHgap(15);
        mainPane.setVgap(15);
        mainPane.setPadding(new Insets(50));
        mainPane.setAlignment(Pos.CENTER);
// Login page
        Text headerText = new Text("Enter username or email and password.");

        Label usernameLabel = new Label("Username or email");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username or email here.");

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password here.");
        
        Label statusLabel= new Label("Incorrect username or password");
	    statusLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 18));
	    statusLabel.setVisible(false);
	    
//Hyperlink for forgot UserName
	    
	    Hyperlink forgotUsernameLink = new Hyperlink("Forgot Username");
        forgotUsernameLink.setOnAction(event -> {
            ForgotUserName forgotUsername = new ForgotUserName(usernameField);
            forgotUsername.showAndWait();
        });
        
        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password");
        forgotPasswordLink.setOnAction(event -> {
            Password forgotPassword = new Password(passwordField);
            forgotPassword.showAndWait();
        });

        Button loginButton = new Button("Login");
        loginButton.setDefaultButton(true);
        loginButton.setOnAction(event -> {
            String inputName = usernameField.getText();
            String inputPassword = passwordField.getText();
            boolean userExist = false;
            for(UserAccount userAccount: UserAccountManager.accountManager) {
                String userName = userAccount.getUsername();
                String email = userAccount.getEmail();
                String userPassword = userAccount.getPassword();

                if(userName.equals(inputName) || email.equals(inputName)) {
                    userExist = true;
                    if(userPassword.equals(inputPassword)) {
	                    statusLabel.setText("Login successful!");
	                    statusLabel.setTextFill(Color.FORESTGREEN);
	                    statusLabel.setVisible(true);
	                    Alert popUp = new Alert(Alert.AlertType.CONFIRMATION, "Login Successful");
	                    popUp.showAndWait();
                    } else {
                    	statusLabel.setText("Incorrect username or password");
	                    statusLabel.setTextFill(Color.FIREBRICK);
	                    statusLabel.setVisible(true);
                    }
                }
            }
            
            if(!userExist) {
	            Alert popUp = new Alert(Alert.AlertType.WARNING, "Incorrect username or password");
	            statusLabel.setText("Incorrect username or password");
	            statusLabel.setTextFill(Color.FIREBRICK);
                statusLabel.setVisible(true);
            }
        });
        
        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(event -> {
//  Present a sign up form for a new user
            SignUp signUp = new SignUp();
            signUp.show();
        });

        mainPane.add(statusLabel, 0, 0);
        mainPane.add(headerText, 0, 1);
        mainPane.add(usernameLabel, 0, 2);
        mainPane.add(new VBox(usernameField), 0, 3);
        mainPane.add(new VBox(usernameField, forgotUsernameLink), 0, 3);
        mainPane.add(passwordLabel, 0, 4);
        mainPane.add(new VBox(passwordField), 0, 5);
        mainPane.add(new VBox(passwordField, forgotPasswordLink), 0, 5);

        //Added button pane to include sign up option
        HBox buttonPane = new HBox();
        buttonPane.setAlignment(Pos.CENTER_LEFT);
        buttonPane.setSpacing(10);
        buttonPane.getChildren().addAll(loginButton, signUpButton);
        mainPane.add(buttonPane, 0, 6);

        Scene mainScene = new Scene(mainPane, 500, 500, Color.LIGHTGRAY);
        this.setScene(mainScene);
    }
}
