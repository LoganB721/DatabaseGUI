package gsu.dbs.auction;

import gsu.dbs.auction.login.LoginPage;
import gsu.dbs.auction.newuser.NewUserPage;
import gsu.dbs.auction.newvendor.NewVendorPage;
import gsu.dbs.auction.ui.Page;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Launcher extends Application {
	private static StackPane pane;
	
	public void start(Stage stage) {
		pane = new StackPane();
		
		stage.setTitle("My JavaFX Application");
		stage.setScene(new Scene(pane, 1280, 720));
		stage.show();
		
		loadPage(new LoginPage());
	}
	
	public static void loadPage(Page page) {
		pane.getChildren().clear();
		page.loadPage(pane);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void topBar(Pane page, String newTitle) {
		// Top Bar
		StackPane topBar = new StackPane();
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.setPadding(new Insets(12,32,12,32));
		topBar.setMinHeight(100);
		topBar.setMaxHeight(100);
	    BackgroundFill fill = new BackgroundFill(Color.rgb(35, 47, 63), CornerRadii.EMPTY, Insets.EMPTY);
		topBar.setBackground(new Background(fill));
		page.getChildren().add(topBar);
		
		// Welcome text
		StackPane tempWelcome = new StackPane();
		tempWelcome.setAlignment(Pos.TOP_RIGHT);
		topBar.getChildren().add(tempWelcome);
		Label welcome = new Label("Welcome, " + LoginInformation.User);
		welcome.setTextFill(Color.WHITESMOKE);
		welcome.setAlignment(Pos.TOP_RIGHT);
		tempWelcome.getChildren().add(welcome);
		
		// Box containing store name AND search bar
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(32);
		topBar.getChildren().add(hbox);
		
		// Store name
		Label title = new Label(newTitle);
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		title.setTextFill(Color.WHITESMOKE);
		hbox.getChildren().add(title);
		
		// Search bar
		TextField searchBar = new TextField();
		searchBar.setText("Search bar");
		searchBar.setMaxWidth(600);
		searchBar.setPrefWidth(600);
		hbox.getChildren().add(searchBar);
		
		//Access Level drop down bar
		ObservableList<String> pageSelect = FXCollections.observableArrayList("");
		if(LoginInformation.AccessLevel >= 3) {
		 pageSelect = 
				    FXCollections.observableArrayList(
				        "Customer",
				        "Vendor",
				        "Administrator"
				    );
		}else {
			 pageSelect = 
			    FXCollections.observableArrayList(
			        "Customer",
			        "Vendor"
			    );
		}
		final ComboBox comboBox = new ComboBox(pageSelect);
		comboBox.setValue("Customer");
		hbox.getChildren().add(comboBox);
	
		

		// New User Button
		Button newUserBtn = new Button("Create New User");
		newUserBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Launcher.loadPage(new NewUserPage());
			}
			
		});
		
		// New Vendor Button
		Button newVendorBtn = new Button("Create New Vendor");
		newVendorBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {    //every time you click button, this is what happens.
				Launcher.loadPage(new NewVendorPage());
			}
		});
		hbox.getChildren().addAll(newVendorBtn, newUserBtn);
		
		
		
		// Gradient graphic
		StackPane topBarGradient = new StackPane();
		topBarGradient.setAlignment(Pos.CENTER_LEFT);
		topBarGradient.setMinHeight(8);
		topBarGradient.setMaxHeight(8);
		topBarGradient.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 0px 4px, rgba(0,0,0,0.5), rgba(0,0,0,0));");
		page.getChildren().add(topBarGradient);
	}
}









