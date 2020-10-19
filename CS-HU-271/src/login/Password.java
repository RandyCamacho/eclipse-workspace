package login;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Password extends Stage{
	public Password(TextField loginPasswordField) {
		super();

//Forgot PassWord page


		VBox layout = new VBox();
		layout.setSpacing(15);
		layout.setPadding(new Insets(50));

		Text headerText = new Text("Forgot Password");
		headerText.setFont(Font.font("Helvetica", FontWeight.LIGHT, 20));
		TextFlow header = new TextFlow(headerText);


		Label emailFieldLabel = new Label("Email");
		TextField emailField = new TextField();

		Label userNameLabel = new Label("Username");
		TextField usernameField = new TextField();

//Submit and cancel Buttons		

		Button submitButton = new Button("Submit");
		submitButton.setDefaultButton(true);

		Button cancelButton = new Button("Cancel");
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(event -> this.close());

//Submit Button event 

		submitButton.setOnAction(event -> {
			String inputUsername = usernameField.getText();
			for (UserAccount user : UserAccountManager.accountManager) {
				if (user.getEmail().equals(emailField.getText())) {
					if (user.getUsername().equals(inputUsername)) {

						Stage newPassPopUp = new Stage();
                        GridPane newPassGrid = new GridPane();
                        newPassGrid.setAlignment(Pos.CENTER);
                        newPassGrid.setPadding(new Insets(50));

                        Label message = new Label("Please set a new Password below ");
                        
                        Label newPassLabel = new Label("Enter New Password");
                        PasswordField newPassField = new PasswordField();
                        newPassField.setPromptText("Enter New Password");

                        Label confirmLabel = new Label("Confirm Password");
                        PasswordField confirmPassword = new PasswordField();
                        confirmPassword.setPromptText("Confirm Password");

                        Button newPasswordButton = new Button("Submit");

                        newPasswordButton.setOnAction(event1 -> {

                            if (newPassField.getText().equals(confirmPassword.getText())) {
                                user.setPassword(confirmPassword.getText());
                                newPassPopUp.close();
                                this.close();
                            } else {
                                Alert popUp = new Alert(Alert.AlertType.WARNING,"Passwords Do Not Match");
                                popUp.showAndWait();
                            }
                        });

                        newPassGrid.add(message, 0, 0);
                        newPassGrid.add(newPassLabel, 0, 2);
                        newPassGrid.add(newPassField, 0, 3);
                        newPassGrid.add(confirmLabel, 0, 5);
                        newPassGrid.add(confirmPassword, 0, 6);
                        newPassGrid.add(newPasswordButton, 0, 7);

                        Scene popUpScene = new Scene(newPassGrid, 400, 400, Color.LIGHTGRAY);
                        newPassPopUp.setResizable(false);
                        newPassPopUp.setScene(popUpScene);
                        newPassPopUp.showAndWait();
                        
                        loginPasswordField.setText(user.getPassword());
                        
						this.close();
					} else {
						Alert popUp = new Alert(Alert.AlertType.WARNING, "Invalid Email Address or Username");
						popUp.showAndWait();
					}
				} else {
					Alert popUp = new Alert(Alert.AlertType.WARNING, "Invalid Email Address or Username");
					popUp.showAndWait();
				}
			}
		});

		
//Set layout for page
		
		layout.getChildren().addAll(
				header,
				emailFieldLabel,
				emailField,
				userNameLabel,
				usernameField,
				submitButton,
				cancelButton);

		Scene scene = new Scene(layout, 300, 400);
		this.setScene(scene);
	}
}
