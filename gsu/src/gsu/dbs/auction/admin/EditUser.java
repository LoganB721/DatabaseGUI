package gsu.dbs.auction.admin;


import java.sql.Connection;
import java.sql.ResultSet;
import gsu.dbs.auction.DBConnect;
import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.newuser.NewUserPage;
import gsu.dbs.auction.ui.Page;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;

public class EditUser extends Page{
	Connection c;
	ObservableList<ObservableList> data;
	ObservableList<ObservableList> fieldData;
	TableView tv;
	HBox hb; //Text Fields
	
	public void loadPage(Pane canvas)  {


		VBox mainPage = new VBox();
		mainPage.setFillWidth(true);
		canvas.getChildren().add(mainPage);

		Launcher.topBar(mainPage, "Administrator");

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		//Vbox for combo box and custom query
		VBox queries = new VBox(25);
	

		//Dropdown menu for admin to pick what to edit
		ObservableList<String> options = 
				FXCollections.observableArrayList(
						"Edit Admins",
						"Edit Users",
						"Edit Customers",
						"Edit Vendors",
						"Edit Products",
						"Edit Product Type"
						);
		final ComboBox<String> comboBox = new ComboBox<String>(options); 
		final TextArea querysearch = new TextArea();
		querysearch.setPrefColumnCount(50);
		querysearch.setPrefRowCount(10);
		final Button submitquery = new Button("Submit Query");
	
		queries.getChildren().addAll(comboBox, querysearch, submitquery);
		submitquery.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				buildData(querysearch.getText());
				grid.add(tv, 1, 0);
			}
			
		});
		
		
		grid.add(queries, 0, 0);
		//Create Table and columns for users

		comboBox.setOnAction((event)->{
			String selected = comboBox.getSelectionModel().getSelectedItem();

			if(selected.contains("User")){
				buildData("select * from User");
				grid.add(tv, 1, 0);
				grid.add(hb,1,1);
			}
			if(selected.contains("Admins")) {
				buildData("select u.*"
						+ "from User u join Administrator a on u.AccountID = a.AdminID "
						+ "where u.AccessLevel >= 3 ");
				grid.add(tv, 1, 0);
			}
			if(selected.contains("Customers")){
				buildData("select u.*"
						+ "from User u join Customer c on u.AccountID = c.CustomerID "
						+ "where AccessLevel >= 1 ");
				grid.add(tv, 1, 0);
			}    
			if(selected.contains("Vendors")) {				//add in customer review info as well
				buildData("select u.* from User u join Vendor v on u.AccountID = v.VendorID "
						+ "where AccessLevel >= 2");
				grid.add(tv, 1, 0);
			}
			if(selected.contains("Products")) {
				buildData("select * from Products");
				grid.add(tv, 1, 0);
			}
			if(selected.contains("Edit Product Type")) {
				buildData("select * from Product_Type");
				grid.add(tv, 1, 0);
			}

		});

/*
		//Create Text Fields for adding new User info
		final TextField addAccID = new TextField();
		addAccID.setPromptText("AccountID");
		final TextField addUsername = new TextField();
		addUsername.setPromptText("Username");
		final TextField addPassword = new TextField();
		addPassword.setPromptText("Password");
		final TextField addDateCreated = new TextField();
		addDateCreated.setPromptText("Date Created");
		final TextField addAccessLevel = new TextField();
		addAccessLevel.setPromptText("Access Level");
		final TextField addEmail = new TextField();
		addEmail.setPromptText("Email");
		final TextField addAge = new TextField();
		addAge.setPromptText("Age");
		final TextField addLoginDate = new TextField();
		addLoginDate.setPromptText("Login Date");

		HBox hb = new HBox();
		hb.setPadding(new Insets(25,25,25,25));
		hb.setSpacing(15);

		hb.getChildren().addAll(addAccID, addUsername, addPassword, addDateCreated, addAccessLevel, addEmail, addAge, addLoginDate);



		grid.add(hb, 1, 1);
*/


		//Back Button
		Button back = new Button("Back to browse page");
		Button addUser = new Button("Add User");
		Hyperlink cantfind = new Hyperlink("Can't find what you're looking for?");
		
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
		
		
		mainPage.getChildren().addAll(grid);

	}

	public void buildData(String query){
		data = FXCollections.observableArrayList();
		tv = new TableView();
		
		try{
			c = DBConnect.getConnection();
			//Query for SQL
			String SQL = query;
			//Result
			ResultSet res = c.createStatement().executeQuery(SQL);
			//Adds the columns to the Table
			for(int i=0 ; i<res.getMetaData().getColumnCount(); i++){
				final int j = i;                
				TableColumn col = new TableColumn(res.getMetaData().getColumnName(i+1));

				col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
					public ObservableValue<String> call(CellDataFeatures<ObservableList, String> s) {
						ObservableList cell = s.getValue();
						Object t = cell.get(j);
						String str = "";
						if ( t != null ) {
							str = t.toString();
						}
						SimpleStringProperty prop = new SimpleStringProperty(str);
						return prop;                        
					}
				});

				tv.getColumns().addAll(col); 
				System.out.println("Column ["+i+"] ");
			}



			//Add Data to ObservableList 
			while(res.next()){
				ObservableList<String> row = FXCollections.observableArrayList();
				for(int i=1 ; i<=res.getMetaData().getColumnCount(); i++){
					row.add(res.getString(i));
				}
				System.out.println("Row [1] added " + row );
				data.add(row);

			}

			//Add to tableview
			tv.setItems(data);

		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Whoops... Something happened.");             
		}

	}

	public void buildFields(String query) {
		fieldData = FXCollections.observableArrayList();
		hb = new HBox();
		hb.setPadding(new Insets(25,25,25,25));
		hb.setSpacing(15);
		try {
			//Query for SQL
			String SQL = query;
			//Result
			ResultSet res = c.createStatement().executeQuery(SQL);
			//Cycle Columns and create text field;
			for(int i=0 ; i<res.getMetaData().getColumnCount(); i++){
				TextField tf = new TextField();
				
				hb.getChildren().add(tf);
			}
	
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Whoops... Something happened.");             
		}

	}
}
