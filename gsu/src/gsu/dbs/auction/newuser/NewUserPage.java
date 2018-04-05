package gsu.dbs.auction.newuser;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.login.LoginPage;
import gsu.dbs.auction.ui.Page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class NewUserPage extends Page {

	@Override
	public void loadPage(Pane canvas) {
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);

		Launcher.topBar(mainPage, "User Information");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		// Username
		Label username = new Label("Username:");
		grid.add(username, 0, 2);

		final TextField usernameTextField = new TextField();
		usernameTextField.setText("");
		grid.add(usernameTextField, 1, 2);

		// Password
		Label password = new Label("Password:");
		grid.add(password, 0, 3);

		final TextField passwordTextField = new TextField();
		passwordTextField.setText("");
		grid.add(passwordTextField, 1, 3);

		// Password confirm
		Label confirmPassword = new Label("Confirm Password:");
		grid.add(confirmPassword, 0, 4);

		final TextField confirmPasswordTextField = new TextField();
		confirmPasswordTextField.setText("");
		grid.add(confirmPasswordTextField, 1, 4);

		Label emailAddress = new Label("Email Address:");
		grid.add(emailAddress, 0, 9);

		final TextField emailAddressTextField = new TextField("Email Address:");
		emailAddressTextField.setText("");
		grid.add(emailAddressTextField, 1, 9);

		Label age = new Label("Date of Birth:");
		grid.add(age, 0, 10);

		final TextField ageTextField = new TextField();
		ageTextField.setText("MM/DD/YYYY");
		grid.add(ageTextField, 1, 10);

		//Back Button
		Button back = new Button("Back to Login Page");
		HBox hbBack = new HBox(10);
		hbBack.setAlignment(Pos.BOTTOM_LEFT);
		hbBack.getChildren().add(back);
		grid.add(hbBack, 0, 11);

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
		grid.add(hbCreateUser, 1, 11);
		createUser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
					Launcher.loadPage(new ConfirmNewUser());
			
			}
		});

		mainPage.getChildren().add(grid);
		
		// To be used later
		final Date now = new Date();
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		final String date = df.format(now);
	}

}
