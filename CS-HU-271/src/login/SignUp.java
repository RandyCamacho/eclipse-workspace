package login;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SignUp extends Stage {

	private static final String errorColorName = "firebrick";
	private static final String validColorName = "forestgreen";
	private static final Color errorColor = Color.valueOf(errorColorName);
	private static final Color validColor = Color.valueOf(validColorName);
	private static final String textFieldError = "-fx-text-box-border: " + errorColorName + "; -fx-focus-color: " + errorColorName;
	private static final String textFieldValid = "-fx-text-box-border: " + validColorName + "; -fx-focus-color: " + validColorName;
	private static final Font errorMessage = Font.font("Helvetica", FontWeight.LIGHT, 10);

	public SignUp() {
		super();

		VBox layout = new VBox(15);
		layout.setPadding(new Insets(50));
		layout.setAlignment(Pos.CENTER);

		VBox usernameArea = new VBox(5);
		Label usernameLabel = new Label("User Name");
		TextField usernameField = new TextField();
		Text usernameErrorMessage = new Text();
		usernameErrorMessage.setFill(errorColor);
		usernameErrorMessage.setFont(errorMessage);
		StringProperty usernameError = new SimpleStringProperty();

		usernameErrorMessage.textProperty().bind(usernameError);
		usernameArea.getChildren().addAll(usernameLabel, usernameField, usernameErrorMessage);

		VBox emailArea = new VBox(5);
		Label emailLabel = new Label("Email Address");
		TextField emailField = new TextField();
		Text emailErrorMessage = new Text();
		emailErrorMessage.setFill(errorColor);
		emailErrorMessage.setFont(errorMessage);
		StringProperty emailError = new SimpleStringProperty();

		emailErrorMessage.textProperty().bind(emailError);
		emailArea.getChildren().addAll(emailLabel, emailField, emailErrorMessage);

		VBox confirmEmailArea = new VBox(5);
		Label confirmEmailLabel = new Label("Confirm Email Address");
		TextField confirmEmailField = new TextField();
		Text confirmEmailErrorMessage = new Text();
		confirmEmailErrorMessage.setFill(errorColor);
		confirmEmailErrorMessage.setFont(errorMessage);
		StringProperty confirmEmailError = new SimpleStringProperty();

		confirmEmailErrorMessage.textProperty().bind(confirmEmailError);
		confirmEmailArea.getChildren().addAll(confirmEmailLabel, confirmEmailField, confirmEmailErrorMessage);


		VBox passwordArea = new VBox(5);
		Label passwordLabel = new Label("Password");
		PasswordField passwordField = new PasswordField();
		Text passwordErrorMessage = new Text();
		passwordErrorMessage.setFill(errorColor);
		passwordErrorMessage.setFont(errorMessage);
		StringProperty passwordError = new SimpleStringProperty();

		passwordErrorMessage.textProperty().bind(passwordError);
		passwordArea.getChildren().addAll(passwordLabel, passwordField, passwordErrorMessage);

		VBox confirmPasswordArea = new VBox(5);
		Label confirmPasswordLabel = new Label("Confirm Password");
		PasswordField confirmPasswordField = new PasswordField();
		Text confirmPasswordErrorMessage = new Text();
		confirmPasswordErrorMessage.setFill(errorColor);
		confirmPasswordErrorMessage.setFont(errorMessage);
		StringProperty confirmPasswordError = new SimpleStringProperty();

		confirmPasswordErrorMessage.textProperty().bind(confirmPasswordError);
		confirmPasswordArea.getChildren().addAll(confirmPasswordLabel, confirmPasswordField, confirmPasswordErrorMessage);

		final boolean[] usernameFieldValid = {false};
		final boolean[] emailFieldValid = {false};
		final boolean[] confirmEmailFieldValid = {false};
		final boolean[] passwordFieldValid = {false};
		final boolean[] confirmPasswordFieldValid = {false};

		HBox buttonArea = new HBox(15);
		buttonArea.setAlignment(Pos.CENTER_LEFT);

		Button cancelButton = new Button("Cancel");
		cancelButton.setCancelButton(true);
		cancelButton.setOnAction(event -> this.close());

		Button submitButton = new Button("Create Account");
		submitButton.setDefaultButton(true);
		submitButton.setOnAction(event -> {
			if (confirmSpecifics(
					usernameField,
					emailField,
					confirmEmailField,
					passwordField,
					confirmPasswordField,
					usernameError,
					emailError,
					confirmEmailError,
					passwordError,
					confirmPasswordError)) {
				UserAccount newUser = new UserAccount(usernameField.getText(), passwordField.getText(), emailField.getText());
				UserAccountManager.accountManager.add(newUser);
				if (UserAccountManager.accountManager.contains(newUser)) {
					Alert popUp = new Alert(Alert.AlertType.INFORMATION, "New user successfully created");
					popUp.setOnCloseRequest(closeEvent -> this.close());
					popUp.showAndWait();
					UserAccountManager.accountManager.save("./users.db");
				} else {
					Alert popUp = new Alert(Alert.AlertType.ERROR, "Unable to create new user");
					popUp.setOnCloseRequest(closeEvent -> this.close());
					popUp.showAndWait();
				}
			} else {
				Alert popUp = new Alert(Alert.AlertType.WARNING, "Unable to create new user");
				popUp.setOnCloseRequest(closeEvent -> this.close());
				popUp.showAndWait();
			}
		});

		buttonArea.getChildren().addAll(submitButton, cancelButton);

		layout.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ACCEPT)) submitButton.fire();
		});

		layout.getChildren().addAll(
				usernameArea,
				emailArea,
				confirmEmailArea,
				passwordArea,
				confirmPasswordArea,
				buttonArea
		);

		usernameField.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
			if (wasFocused && !isFocused) usernameFieldValid[0] = checkUsernameField(usernameField, usernameError);
			enableSubmitIfValid(submitButton, usernameFieldValid[0], emailFieldValid[0], passwordFieldValid[0], passwordFieldValid[0], confirmPasswordFieldValid[0]);
		});

		emailField.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
			if (wasFocused && !isFocused) emailFieldValid[0] = checkEmailField(emailField, emailError);
		});

		confirmEmailField.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
			if (wasFocused && !isFocused)
				confirmEmailFieldValid[0] = checkConfirmEmailField(emailField, confirmEmailField, confirmEmailError);
		});
		confirmEmailField.textProperty().addListener(observable -> confirmEmailFieldValid[0] = checkConfirmEmailField(emailField, confirmEmailField, confirmEmailError));


		passwordField.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
			if (wasFocused && !isFocused) passwordFieldValid[0] = checkPasswordField(passwordField, passwordError);
		});
		passwordField.textProperty().addListener(observable -> passwordFieldValid[0] = checkPasswordField(passwordField, passwordError));

		confirmPasswordField.focusedProperty().addListener(observable -> {
			confirmPasswordFieldValid[0] = checkConfirmPasswordField(passwordField, confirmPasswordField, confirmPasswordError);
		});
		confirmPasswordField.textProperty().addListener(observable -> confirmPasswordFieldValid[0] = checkConfirmPasswordField(passwordField, confirmPasswordField, confirmPasswordError));

		Scene scene = new Scene(layout, 600, 500, Color.LIGHTGRAY);
		this.setTitle("Sign Up");
		this.setResizable(false);
		this.setScene(scene);
	}

	private static void enableSubmitIfValid(Button submitButton, boolean usernameValid, boolean emailValid, boolean confirmEmailValid, boolean passwordValid, boolean confirmPasswordValid) {
		submitButton.setDisable(usernameValid && emailValid && confirmEmailValid && passwordValid && confirmEmailValid);
	}

	private static boolean confirmSpecifics(
			TextField usernameField,
			TextField emailField,
			TextField confirmEmailField,
			PasswordField passwordField,
			PasswordField confirmPasswordField,
			StringProperty usernameError,
			StringProperty emailError,
			StringProperty confirmEmailError,
			StringProperty passwordError,
			StringProperty confirmPasswordError) {
		boolean isTrue = true;
		isTrue &= checkUsernameField(usernameField, usernameError);
		isTrue &= checkEmailField(confirmEmailField, emailError);
		isTrue &= checkConfirmEmailField(emailField, confirmEmailField, confirmEmailError);
		isTrue &= checkPasswordField(passwordField, passwordError);
		isTrue &= checkConfirmPasswordField(passwordField, confirmPasswordField, confirmPasswordError);
		return isTrue;
	}

	private static boolean checkUsernameField(TextField usernameField, StringProperty usernameError) {
		boolean valid = false;
		if (usernameField.getText().isEmpty()) {
			usernameError.set("Must specify a username");
			usernameField.styleProperty().set(textFieldError);
		} else if (!UserAccount.validUsername(usernameField.getText())) {
			usernameError.set("Password must be between 3 and 16 characters long");
			usernameField.styleProperty().set(textFieldError);
		} else if (UserAccount.usernameExists(usernameField.getText())) {
			usernameError.set("That username is already taken");
		} else {
			usernameError.set("");
			usernameField.styleProperty().set(textFieldValid);
			valid = true;
		}
		return valid;
	}

	private static boolean checkEmailField(TextField emailField, StringProperty emailError) {
		boolean valid = false;
		if (emailField.getText().isEmpty()) {
			emailError.set("Specify an email address");
			emailField.styleProperty().set(textFieldError);
		} else if (!UserAccount.validEmail(emailField.getText())) {
			emailError.set("Email address is not valid");
		} else if (UserAccount.emailExists(emailField.getText())) {
			emailError.set("Email already taken");
		} else {
			emailError.set("");
			emailField.styleProperty().set(textFieldValid);
			valid = true;
		}
		return valid;
	}

	private static boolean checkConfirmEmailField(TextField emailField, TextField confirmEmailField, StringProperty confirmEmailError) {
		boolean valid = false;
		if (!confirmEmailField.getText().equals(emailField.getText())) {
			confirmEmailError.set("Email must match");
			confirmEmailField.styleProperty().set(textFieldError);
		} else if (!emailField.getText().isEmpty()) {
			confirmEmailError.set("");
			confirmEmailField.styleProperty().set(textFieldValid);
			valid = true;
		}
		return valid;
	}

	private static boolean checkPasswordField(PasswordField passwordField, StringProperty passwordError) {
		boolean valid = false;
		if (passwordField.getText().isEmpty()) {
			passwordError.set("Must specify a password");
			passwordField.styleProperty().set(textFieldError);
		} else if (!UserAccount.validatePassword(passwordField.getText())) {
			passwordError.set("Password must be between 8 and 128 characters");
			passwordField.styleProperty().set(textFieldError);
		} else {
			passwordError.set("");
			passwordField.styleProperty().set(textFieldValid);
			valid = true;
		}
		return valid;
	}

	private static boolean checkConfirmPasswordField(PasswordField passwordField, PasswordField confirmPasswordField, StringProperty confirmPasswordError) {
		boolean valid = false;
		if (!confirmPasswordField.getText().equals(passwordField.getText())) {
			confirmPasswordError.set("Passwords must match");
			confirmPasswordField.styleProperty().set(textFieldError);
		} else if (!passwordField.getText().isEmpty()) {
			confirmPasswordError.set("");
			confirmPasswordField.styleProperty().set(textFieldValid);
			valid = true;
		}
		return valid;
	}


	private static void display(String message, EventHandler<WindowEvent> closeHandler) {
		Stage signUpStage = new Stage();
		GridPane gridLayout = new GridPane();
		gridLayout.setAlignment(Pos.CENTER);
		gridLayout.setPadding(new Insets(50));
		Text messageText = new Text(message);
		messageText.setFont(Font.font("Tahoma", FontWeight.LIGHT, 18));
		gridLayout.add(messageText, 0, 0);
		Scene signUpScene = new Scene(gridLayout, 300, 100, Color.LIGHTGRAY);
		signUpStage.setOnCloseRequest(closeHandler);
		signUpStage.setResizable(false);
		signUpStage.setScene(signUpScene);
		signUpStage.showAndWait();
	}
}
