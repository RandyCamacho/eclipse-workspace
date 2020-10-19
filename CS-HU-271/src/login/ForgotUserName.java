package login;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class ForgotUserName extends Stage{
	public ForgotUserName(TextField loginUsernameField) {
		super();

//Forgot UserName page


		VBox layout = new VBox();
		layout.setSpacing(15);
		layout.setPadding(new Insets(50));

		Text headerText = new Text("Forgot Username");
		headerText.setFont(Font.font("Helvetica", FontWeight.LIGHT, 20));
		TextFlow header = new TextFlow(headerText);


		Label emailFieldLabel = new Label("Email");
		TextField emailField = new TextField();

		Label passwordFieldLabel = new Label("Password");
		PasswordField passwordField = new PasswordField();

//Submit and cancel Buttons		

		Button submitButton = new Button("Submit");
		submitButton.setDefaultButton(true);

		Button cancelButton = new Button("Cancel");
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(event -> this.close());

//Submit Button event 

		submitButton.setOnAction(event -> {
			String inputPassword = passwordField.getText();
			for (UserAccount user : UserAccountManager.accountManager) {
				if (user.getEmail().equals(emailField.getText())) {
					if (user.getPassword().equals(inputPassword)) {
						loginUsernameField.setText(user.getUsername());
						Alert popUp = new Alert(Alert.AlertType.CONFIRMATION, "Username recovered: " + user.getUsername());
						popUp.showAndWait();
						this.close();
					} else {
						Alert popUp = new Alert(Alert.AlertType.WARNING, "Invalid email address or password");
						popUp.showAndWait();
					}
				} else {
					Alert popUp = new Alert(Alert.AlertType.WARNING, "Invalid email address or password");
					popUp.showAndWait();
				}
			}
		});

		
//Set layout for page
		
		layout.getChildren().addAll(
				header,
				emailFieldLabel,
				emailField,
				passwordFieldLabel,
				passwordField,
				submitButton,
				cancelButton);

		Scene scene = new Scene(layout, 300, 400);
		this.setScene(scene);
	}
}
