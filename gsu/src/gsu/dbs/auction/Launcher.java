package gsu.dbs.auction;

import java.sql.SQLException;

import gsu.dbs.auction.admin.AdminHomePage;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.login.LoginPage;
import gsu.dbs.auction.newuser.NewUserPage;
import gsu.dbs.auction.newvendor.NewVendorPage;
import gsu.dbs.auction.sell.SellPage;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.font.TextLabel;

public class Launcher extends Application {
	private static StackPane pane;
	private static Stage primaryStage;

	public void start(Stage stage){
		pane = new StackPane();

		stage.setTitle("My JavaFX Application");
		stage.setScene(new Scene(pane, 1280, 720));
		stage.show();

		loadPage(new LoginPage());
	}

	public static void loadPage(Page page){
		pane.getChildren().clear();
		page.loadPage(pane);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void topBar(Pane page, String newTitle){
		// Top Bar
		StackPane topBar = new StackPane();
		topBar.setAlignment(Pos.CENTER_LEFT);
		topBar.setPadding(new Insets(12,32,12,32));
		topBar.setMinHeight(100);
		topBar.setMaxHeight(100);
		BackgroundFill fill = new BackgroundFill(Color.rgb(35, 47, 63), CornerRadii.EMPTY, Insets.EMPTY);
		topBar.setBackground(new Background(fill));
		page.getChildren().add(topBar);


		// Box containing store name AND search bar
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(32);
		topBar.getChildren().add(hbox);

		// Store name
		Label title = new Label(newTitle);
		title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 28));
		title.setTextFill(Color.WHITESMOKE);
		title.setMinWidth(300);
		hbox.getChildren().add(title);
		
		// Search bar
		TextField searchBar = new TextField();
		searchBar.setPromptText("Search bar");
		searchBar.setMinWidth(500);
		searchBar.setMaxWidth(600);
		searchBar.setPrefWidth(600);
		hbox.getChildren().add(searchBar);
		
		if ( BrowsePage.search != null ) {
			searchBar.setText(BrowsePage.search);
		}

		searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					Launcher.loadPage(new BrowsePage(searchBar.getText()));
				}
			}
		});

		// Box containing welcome, 
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER_RIGHT);
		vbox.setFillWidth(true);
		vbox.setPrefWidth(1024);
		vbox.setSpacing(1);
		hbox.getChildren().add(vbox);

		Label welcome = new Label("Welcome, " + LoginInformation.User);
		welcome.setTextFill(Color.WHITESMOKE);
		vbox.getChildren().add(welcome);

		Hyperlink sell = new Hyperlink("Sell an Item");
		sell.setAlignment(Pos.CENTER);
		sell.setMouseTransparent(false);
		sell.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new SellPage());
			}
		});	
		vbox.getChildren().add(sell);


		//Admin hyperlink		
		Hyperlink admin = new Hyperlink("Administrator");
		admin.setAlignment(Pos.CENTER);
		admin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Launcher.loadPage(new AdminHomePage());     //Ask for Administrator login to access next page?
			}	
		});
		vbox.getChildren().add(admin);

		Hyperlink signOut = new Hyperlink("Sign Out");
		signOut.setAlignment(Pos.CENTER);
		signOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Launcher.loadPage(new LoginPage());
			}
		});	
		vbox.getChildren().add(signOut);

		// Gradient graphic
		StackPane topBarGradient = new StackPane();
		topBarGradient.setAlignment(Pos.CENTER_LEFT);
		topBarGradient.setMinHeight(8);
		topBarGradient.setMaxHeight(8);
		topBarGradient.setStyle("-fx-background-color: linear-gradient(from 0px 0px to 0px 4px, rgba(0,0,0,0.5), rgba(0,0,0,0));");
		page.getChildren().add(topBarGradient);
		
		topBar.requestFocus();
	}

	public static Stage popup() {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		return dialog;
	}

	public static Stage error(String message) {
		final Stage dialog = popup();
		VBox pane = new VBox();
		pane.setPadding(new Insets(8,8,8,8));
		Scene dialogScene = new Scene(pane, 300, 140);
		dialog.setScene(dialogScene);
		dialog.show();

		Text label = new Text(message);
		label.setFill(Color.RED);
		label.setWrappingWidth(300);
		pane.getChildren().add(label);

		Button ok = new Button("Ok");
		ok.setOnAction(event -> {
			dialog.close();
		});
		pane.getChildren().add(ok);


		return dialog;
	}
}






