package gsu.dbs.auction.newuser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import gsu.dbs.auction.DBConnect;
import gsu.dbs.auction.HashGeneratorUtils;
import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.LoginInformation;
import gsu.dbs.auction.login.LoginPage;
import gsu.dbs.auction.ui.Page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NewUserPage extends Page {
	private static String error = null;
	
	@Override
	public void loadPage(Pane canvas) {
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		mainPage.setAlignment(Pos.CENTER);
		canvas.getChildren().add(mainPage);
		
		if ( NewUserPage.error != null ) {
			Label message = new Label("Sorry, there was an error creating your account. " + error);
			message.setTextFill(Color.RED);
			mainPage.getChildren().add(message);
		}


		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		Text scenetitle = new Text("Create an Account");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);
		
		// Username
		Label username = new Label("Username:");
		grid.add(username, 0, 2);

		final TextField usernameTextField = new TextField();
		usernameTextField.setText("");
		grid.add(usernameTextField, 1, 2);

		// Password
		Label password = new Label("Password:");
		grid.add(password, 0, 3);

		final TextField passwordTextField = new PasswordField();
		passwordTextField.setText("");
		grid.add(passwordTextField, 1, 3);

		// Password confirm
		Label confirmPassword = new Label("Confirm Password:");
		grid.add(confirmPassword, 0, 4);

		final TextField confirmPasswordTextField = new PasswordField();
		confirmPasswordTextField.setText("");
		grid.add(confirmPasswordTextField, 1, 4);

		Label emailAddress = new Label("Email Address:");
		grid.add(emailAddress, 0, 5);

		final TextField emailAddressTextField = new TextField("Email Address:");
		emailAddressTextField.setText("");
		grid.add(emailAddressTextField, 1, 5);

		Label age = new Label("Date of Birth:");
		grid.add(age, 0, 6);
		
		final DatePicker ageChooser = new DatePicker();
		ageChooser.setPromptText("mm/dd/yyyy");
		grid.add(ageChooser, 1, 6);

		//Back Button
		Button back = new Button("Back to Login Page");
		HBox hbBack = new HBox(10);
		hbBack.setAlignment(Pos.BOTTOM_LEFT);
		hbBack.getChildren().add(back);
		grid.add(hbBack, 0, 7);

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new LoginPage());
			}
		});

		// Create User Button
		Button createUser = new Button("Create User");
		HBox hbCreateUser = new HBox(10);
		hbCreateUser.setAlignment(Pos.BOTTOM_RIGHT);
		hbCreateUser.getChildren().add(createUser);
		grid.add(hbCreateUser, 1, 7);
		createUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				createUser(usernameTextField.getText(), passwordTextField.getText(), confirmPasswordTextField.getText(), emailAddressTextField.getText(), ageChooser.getValue());
			}
		});

		mainPage.getChildren().add(grid);
		
		// Reset the error.
		NewUserPage.error = null;
	}
	
	private void error( String error ) {
		NewUserPage.error = error;
		Launcher.loadPage(new NewUserPage());
	}
	
	private void createUser(String username, String password, String password2, String email, LocalDate localDate) {
		if ( !password.equals(password2) ) {
			error("Password mismatch!");
			return;
		}
		
		if ( username.length() < 2 ) {
			error("Username must be at least 2 characters!");
			return;
		}
		
		if ( password.length() < 2 ) {
			error("Password must be at least 2 characters!");
			return;
		}
		
		if ( !email.contains("@") || !email.contains(".") || email.length() < 4 ) {
			error("Please type a valid email!");
			return;
		}
		
		try {
			Connection c = DBConnect.getConnection();
			
			PreparedStatement s = c.prepareStatement("SELECT Username FROM User WHERE Username = ?");
			s.setString(1, username);
			ResultSet result = s.executeQuery();
			if ( result.isBeforeFirst() ) { // User already exists
				error("This username is already taken!");
				return;
			}
			
			final LocalDate now = LocalDate.now();
			
			int userage = now.getYear()-localDate.getYear();
			if ( userage < 16 ) {
				error("You must be at least 16 to use this service!");
				return;
			}
			
			s = c.prepareStatement("INSERT INTO User(Username,Password,DateCreated,Email,Birthdate) VALUES(?,?,?,?,?)");
			s.setString(1, username);
			s.setString(2, HashGeneratorUtils.generateSHA1(password));
			s.setDate(3, java.sql.Date.valueOf(now));
			s.setString(4, email);
			s.setDate(5, java.sql.Date.valueOf(localDate));
			s.executeUpdate();
			
			Launcher.loadPage(new ConfirmNewUser());
		} catch (Exception e) {
			e.printStackTrace();
			error("Code 1");
		}
	}
}
