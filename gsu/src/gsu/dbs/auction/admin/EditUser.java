package gsu.dbs.auction.admin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.TestConnection;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.ui.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EditUser extends Page{
	Connection connect = null;
	
	public void loadPage(Pane canvas)  {
		try {
			connect = DriverManager.getConnection("jdbc:mysql://45.79.216.182" + "/" + "AuctionDB" + "root" + "Orange!9739");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);
		
		Launcher.topBar(mainPage, "Administrator");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//Dropdown menu for admin to pick what to edit
        ObservableList<String> options = 
        	    FXCollections.observableArrayList(
        	        "Edit Users",
        	        "Edit Customers",
        	        "Edit Vendors",
        	        "Edit Products"
        	    );
        final ComboBox<String> comboBox = new ComboBox<String>(options); 
        comboBox.setValue("Edit Users");
        grid.add(comboBox, 0, 0);

		//Create Table and columns for users
		TableView users = new TableView();
		users.setEditable(true);	//Allows data to be editable;
	    TableColumn AccountIDCol = new TableColumn("AccountID");
	    AccountIDCol.setPrefWidth(100);
	    TableColumn UsernameCol = new TableColumn("Username");
	    UsernameCol.setPrefWidth(100);
	    TableColumn PasswordCol = new TableColumn("Password");
        PasswordCol.setPrefWidth(100);
	    TableColumn DateCreatedCol = new TableColumn("Date Created");
        DateCreatedCol.setPrefWidth(100);
	    TableColumn AccessLevelCol = new TableColumn("AccessLevel");
        AccessLevelCol.setPrefWidth(100);
	    TableColumn EmailCol = new TableColumn("Email");
        EmailCol.setPrefWidth(100);
	    TableColumn AgeCol = new TableColumn("Age");
        AgeCol.setPrefWidth(100);
	    TableColumn LoginDateCol = new TableColumn("LoginDate");
        LoginDateCol.setPrefWidth(100);
	    users.getColumns().addAll(AccountIDCol, UsernameCol, PasswordCol, DateCreatedCol, AccessLevelCol, EmailCol, AgeCol, LoginDateCol);
	
	   // users.setItems(fetchData(connect));
	    
        grid.add(users, 1, 0);
       
        //Create Text Fields for adding new User info
        final TextField addAccID = new TextField();
        addAccID.setPromptText("AccountID");
        addAccID.setMaxWidth(AccountIDCol.getPrefWidth());
        final TextField addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMaxWidth(UsernameCol.getPrefWidth());
        final TextField addPassword = new TextField();
        addPassword.setPromptText("Password");
        addPassword.setMaxWidth(PasswordCol.getPrefWidth());
        final TextField addDateCreated = new TextField();
        addDateCreated.setPromptText("Date Created");
        addDateCreated.setMaxWidth(DateCreatedCol.getPrefWidth());
        final TextField addAccessLevel = new TextField();
        addAccessLevel.setPromptText("Access Level");
        addAccessLevel.setMaxWidth(AccessLevelCol.getPrefWidth());
        final TextField addEmail = new TextField();
        addEmail.setPromptText("Email");
        addEmail.setMaxWidth(EmailCol.getPrefWidth());
        final TextField addAge = new TextField();
        addAge.setPromptText("Age");
        addAge.setMaxWidth(AgeCol.getPrefWidth());
        final TextField addLoginDate = new TextField();
        addLoginDate.setPromptText("Login Date");
        addLoginDate.setMaxWidth(LoginDateCol.getPrefWidth());
        
        HBox hb = new HBox();
        hb.setPadding(new Insets(25,25,25,25));
        hb.setSpacing(15);
        hb.getChildren().addAll(addAccID, addUsername, addPassword, addDateCreated, addAccessLevel, addEmail, addAge, addLoginDate);
        
        grid.add(hb, 1, 1);
        

        
		//Back Button
		Button back = new Button("Back to browse page");
		Button addUser = new Button("Add User");
		HBox hbBack = new HBox();
		hbBack.setAlignment(Pos.BOTTOM_CENTER);
		hbBack.getChildren().addAll(back,addUser);
		hbBack.setSpacing(25);
		grid.add(hbBack, 1, 2);
		
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		
					Launcher.loadPage(new BrowsePage());
			
			}
		});
		
		addUser.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// Insert code to add to database here
				
			}
		});
		
		mainPage.getChildren().add(grid);
		
	}
	
	public ObservableList<String> fetchData(Connection connect) throws SQLException{
		//Row Iteration
		ObservableList<String> data = FXCollections.observableArrayList();
		Statement s = connect.createStatement();
		ResultSet result = s.executeQuery("select * from User");
		
		while(result.next()) {
			data.add(result.getString("AccountID"));
		}
		
		return data;
	}
	
}
