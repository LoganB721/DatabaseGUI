package gsu.dbs.auction.admin;

import java.sql.SQLException;

import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.LoginInformation;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.login.LoginPage;
import gsu.dbs.auction.newuser.NewUserPage;
import gsu.dbs.auction.ui.Page;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AdminHomePage extends Page{

	@Override
	public void loadPage(Pane canvas) {
		VBox mainHolder = new VBox();
		mainHolder.setAlignment(Pos.CENTER);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Login as Admin");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		final TextField userTextField = new TextField();
		userTextField.setText("Admin");
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		final PasswordField pwBox = new PasswordField();
		pwBox.setText("password");
		grid.add(pwBox, 1, 2);
		
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			
				Launcher.loadPage(new EditUser());
				
			}
		});
		
		if ( LoginInformation.error ) {
			Label message = new Label("Sorry. It looks like you've supplied an incorrect username or password.");
			message.setTextFill(Color.RED);
			mainHolder.getChildren().add(message);
		}
		LoginInformation.error = false;
		canvas.getChildren().add(mainHolder);
		

	
		mainHolder.getChildren().add(grid);
	}
		
}
	
	

