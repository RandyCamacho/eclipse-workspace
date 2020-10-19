package login;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.regex.Pattern;

public class LoginApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			UserAccountManager.init("./users.db");
		} catch (FileNotFoundException e) {
			UserAccountManager.init();
			e.printStackTrace();
		}
		initializeGUI(primaryStage);
	}

	private static void initializeGUI(Stage primaryStage) {
		primaryStage.setTitle("Scrum Development");
		primaryStage.setResizable(false);

		GridPane gridLayout = new GridPane();
		gridLayout.setAlignment(Pos.CENTER);
		gridLayout.setHgap(15);
		gridLayout.setVgap(15);
		gridLayout.setPadding(new Insets(50));

		Text headerText = new Text("Panic Driven Development User System");
		headerText.setFont(Font.font("Helvetica", FontWeight.LIGHT, 20));
		TextFlow header = new TextFlow(headerText);

		Button registerFormButton = new Button("Register");
		Button loginFormButton = new Button("Log In");
		loginFormButton.setDefaultButton(true);

		registerFormButton.setOnAction(event -> {
			RegistrationForm registrationForm = new RegistrationForm();
			registrationForm.show();
		});
		loginFormButton.setOnAction(event -> {
			LoginForm loginForm = new LoginForm();
			loginForm.show();
		});

		gridLayout.add(header, 0, 0);
		gridLayout.add(registerFormButton, 0, 1);
		gridLayout.add(loginFormButton, 0, 2);

		Scene mainScene = new Scene(gridLayout, 300, 400, Color.LIGHTGRAY);

		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
}