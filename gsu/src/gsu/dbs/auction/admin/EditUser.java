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
import javafx.scene.Scene;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
		ObservableList<String> options = FXCollections.observableArrayList(
				"Edit Admins",
				"Edit Access Levels",
				"Edit Bidding Items",
				"Edit Bid History",
				"Edit Customer Reviews",
				"Edit Customers",
				"Edit Invoices",
				"Edit Products",
				"Edit Product Types",
				"Edit Shipments",
				"Edit Shipping Addresses",
				"Edit Sold Items",
				"Edit Status",
				"Edit Stored Items",
				"Edit Users",
				"Edit Vendors"
		);
		final ComboBox<String> comboBox = new ComboBox<String>(options); 
		final Button submitquery = new Button("New Query");
	
		queries.getChildren().addAll(comboBox, submitquery);
		submitquery.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent event) {
				queryBox(grid);
			}
		});
		
		
		grid.add(queries, 0, 0);
		
		// Default data (EMPTY)
		build(grid,null);
		
		//Create Table and columns for views
		comboBox.setOnAction((event)->{
			String selected = comboBox.getSelectionModel().getSelectedItem();

			if(selected.contains("User")){
				build(grid, "select u.AccountID, u.Username, u.Password, u.DateCreated, "
						+ "	u.Email, u.BirthDate, u.LoginDate, u.AccessLevel, al.Title"
						+ " from User u left join Access_Level al"
						+ "	ON u.AccessLevel = al.AccessLevel");
			}
			if(selected.contains("Admins")) {
				build(grid, "select u.*"
						+ "from User u join Administrator a on u.AccountID = a.AdminID "
						+ "where u.AccessLevel = 4 ");
			}
			if(selected.contains("Customers")){
				build(grid, "select u.*"
						+ "from User u join Customer c on u.AccountID = c.CustomerID "
						+ "where AccessLevel >= 2 ");
			}    
			if(selected.contains("Vendors")) {				//add in customer review info as well
				build(grid, "select v.*, u.Username, u.Email, u.AccessLevel"
						+ " from Vendor v left join User u "
						+ " ON v.VendorID = u.AccountID "
						+ " where AccessLevel >= 3");
			}
			if(selected.contains("Products")) {
				build(grid, "select p.ProductID, p.ProductName, v.VendorName, p.StartingPrice,"
						+ " pt.ProductType, p.ProductDesc, s.Description, p.ImageURL "
						+ "	from Products p left join Vendor v "
						+ "	ON p.VendorID = v.VendorID"
						+ "	left join Product_Type pt "
						+ "	ON p.ProductTypeID = pt.ProductTypeID"
						+ "	left join Status s "
						+ " ON p.ProductStatus = s.StatusID");
			}
			if(selected.contains("Product Type")) {
				build(grid, "select * from Product_Type");
			}
			if(selected.contains("Access Level")) {
				build(grid,"select * from Access_Level");
			}
			if(selected.contains("Customer Reviews")) {
				build(grid,"select cr.CustomerID, u.Username, cr.VendorID, v.VendorName, "
						+ "	cr.Rating, cr.Comment"
						+ " from Customer_Review cr left Join User u "
						+ "	ON cr.CustomerID = u.AccountID "
						+ "	left Join Vendor v "
						+ "	ON cr.VendorID = v.VendorID");
			}
			if(selected.contains("Stored Items")) {
				build(grid, "select si.StorageID, p.ProductName, si.Location,"
						+ "	p.ImageURL "
						+ " from Stored_Items si left join Products p"
						+ "	ON si.StorageID = p.ProductID");
			}
			if(selected.contains("Bidding Items")) {
				build(grid, "select bi.BiddingItemID, p.ProductName, bi.StartTime,"
						+ "	bi.EndTime, p.ImageURL "
						+ "	from Bidding_Items bi left join Products p "
						+ "ON bi.BiddingItemID = p.ProductID");
			}
			if(selected.contains("Bid History")) {
				build(grid, "select bh.BidNumber, u.AccountId, u.Username, bi.BiddingItemID, p.ProductName, "
						+ "	bi.StartTime, bi.EndTime, p.StartingPrice, bh.BidPrice, "
						+ "	p.ImageURL"
						+ " from Bid_History bh "
						+ "	left join Products p "
						+ "	ON p.ProductID = bh.BiddingItemID"
						+ "	left join User u "
						+ "	ON bh.CustomerID = u.AccountID"
						+ "	left join Bidding_Items bi "
						+ "	ON bi.BiddingItemID = bh.BiddingItemID");
			}
			if(selected.contains("Status")) {
				build(grid, "select * from Status");
			}
			if(selected.contains("Sold Items")) {
				build(grid, "select si.CustomerID, u.Username, si.SoldItemID, p.ProductName,"
						+ "	si.BidNumber, si.FinalBidPrice "
						+ "	from Sold_Item si left join User u"
						+ "	ON u.AccountID = si.CustomerID"
						+ "	left join Products p "
						+ "	ON si.SoldItemID = p.ProductID");
			}
			if(selected.contains("Invoices")) {
				build(grid, "select i.InvoiceID, i.CustomerID, u.Username, i.SoldItemID,"
						+ "	p.ProductName, i.BidNumber, i.InvoiceDate, i.InvoiceTotal,"
						+ "	i.InvoiceDueDate, i.PaymentDate"
						+ " from Invoice i left join User u "
						+ "	ON i.CustomerID = u.AccountID"
						+ "	left join Products p "
						+ " ON i.SoldItemID = p.ProductID");
			}
			if(selected.contains("Shipments")) {
				build(grid, "select s.ShipmentID, s.InvoiceID, s.ShippingAddressID, sa.Address, sa.City, sa.State, sa.zip,"
						+ " sc.CompanyName, s.ShipDate, ss.Description "
						+ "	from Shipment s left join Shipping_Address sa"
						+ "	ON s.ShippingAddressID = sa.ShippingAddressID"
						+ "	left join Shipping_Company sc "
						+ "	ON s.ShippingCompanyID = sc.ShippingCompanyID"
						+ "	left join Status ss "
						+ "	ON s.ShipStatus = ss.StatusID");
			}
			if(selected.contains("Shipping Addresses")) {
				build(grid, "select sa.ShippingAddressID, u.Username, sa.Address,"
						+ "	sa.Address2, sa.City, sa.State, sa.ZIP "
						+ "	from Shipping_Address sa left join User u"
						+ "	ON sa.CustomerID = u.AccountID ");
			}
			if(selected.contains("Shipping Companies")) {
				build(grid, "select * "
						+ "from Shipping_Company");
			}
		});

		//Back Button
		Button back = new Button("Back to browse page");
		Hyperlink cantfind = new Hyperlink("Can't find what you're looking for?");
		
		HBox hbBack = new HBox();
		hbBack.setAlignment(Pos.BOTTOM_CENTER);
		hbBack.getChildren().add(back);
		hbBack.setSpacing(25);
		grid.add(hbBack, 0, 3);

		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				Launcher.loadPage(new BrowsePage());

			}
		});
		
		
		mainPage.getChildren().addAll(grid);

	}
	
	private void queryBox(GridPane grid) {
        final Stage dialog = Launcher.popup();
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(8,8,8,8));
        Scene dialogScene = new Scene(pane, 500, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        
        VBox dialogVbox = new VBox(4);
        pane.getChildren().add(dialogVbox);
        dialogVbox.getChildren().add(new Text("Type your query below:"));
        
        TextArea text = new TextArea();
        dialogVbox.getChildren().add(text);
        
        Button submit = new Button("Submit");
        dialogVbox.getChildren().add(submit);
        
        submit.setOnAction(event -> {
        		String SQL = text.getText();
        		dialog.close();
        		build(grid, SQL);
        });
	}

	private void build(GridPane grid, String string) {
		if ( hb != null ) {
			hb.getChildren().clear();
		}
		buildData(string);
		grid.add(tv, 0, 1);
		
	}

	private void buildData(String query){
		data = FXCollections.observableArrayList();
		tv = new TableView();
		tv.setPrefWidth(Integer.MAX_VALUE);
		
		try{
			if ( query != null ) {
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
	
					tv.getColumns().add(col); 
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
			}

			//Add to tableview
			tv.setItems(data);

		}catch(Exception e){
			Launcher.error(e.getMessage());
			//e.printStackTrace();
			//System.out.println("Whoops... Something happened.");             
		}

	}

}
