package gsu.dbs.auction.sell;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gsu.dbs.auction.DBConnect;
import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.LoginInformation;
import gsu.dbs.auction.login.BrowsePage;
import gsu.dbs.auction.login.LoginPage;
import gsu.dbs.auction.ui.Page;
import gsu.dbs.auction.wrapper.BiddingItem;
import gsu.dbs.auction.wrapper.Product;
import javafx.scene.layout.Pane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SellPage extends Page {

	protected HashMap<String,Integer> cached_product_types;
	
	@Override
	public void loadPage(Pane canvas) {
		VBox mainHolder = new VBox();
		mainHolder.setAlignment(Pos.CENTER);
		

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("SELL AN ITEM");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		
		Label itemName = new Label("Item Name:");
		grid.add(itemName, 0, 1);
		TextField itemname = new TextField();
		grid.add(itemname, 1, 1);

		
		Label start = new Label("Starting Price:");
		grid.add(start, 0, 2);
		TextField category = new TextField();
		category.setPromptText("$1");
		grid.add(category, 1, 2);

		
		Label imagelabel = new Label("Item Image:");
		grid.add(imagelabel, 0, 3);
		TextField imagefield = new TextField();
		grid.add(imagefield, 1, 3);
		
		
		Label pt = new Label("Product Type:");
		grid.add(pt, 0, 4);
		ObservableList<String> options = FXCollections.observableArrayList(getProductTypes());
		final ComboBox producttype = new ComboBox(options);
		grid.add(producttype, 1, 4);

		
		Label desclabel = new Label("Item Description:");
		grid.add(desclabel, 0, 5);
		TextArea descfield = new TextArea();
		descfield.setPrefRowCount(4);
		descfield.setPrefColumnCount(28);
		descfield.setWrapText(true);
		grid.add(descfield, 1, 5);
		
		
		Label bcd = new Label("Bid close date:");
		grid.add(bcd, 0, 6);
		final DatePicker ageChooser = new DatePicker();
		ageChooser.setPromptText("mm/dd/yyyy");
		ageChooser.setValue(LocalDate.now().plusDays(1));
		grid.add(ageChooser, 1, 6);
		
		
		Button btn = new Button("Sell Item");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int price = (int) Float.parseFloat(category.getText());
				int typeid = getProductID(producttype.getValue());
				int status = 0;
				String desc = descfield.getText();
				if ( typeid >= 0 ) {
					if ( itemname.getText().length() > 0 ) {
						if ( price > 1 ) {
							if ( imagefield.getText().contains(".") ) {
								LocalDate now = LocalDate.now();
								int dist = (int) (ageChooser.getValue().toEpochDay() - now.toEpochDay());
								if ( dist > 1 && dist < 31 ) {
									Product product = new Product(-1, -1, itemname.getText(), imagefield.getText(), price, typeid, status, desc );
									int vendorID = LoginInformation.getVendorID();
									if ( vendorID == -1 ) {
										createVendorSellProduct(product, ageChooser.getValue());
									} else {
										//Launcher.loadPage(new ConfirmSellPage(product, ageChooser.getValue()));
										listItem( product, ageChooser.getValue() );
									}
								} else {
									Launcher.error("Invalid bid end time. Bid must be at least 1 day, and less than 31 days.");
								}
							} else {
								Launcher.error("Invalid product image");
							}
						} else {
							Launcher.error("Price too low ("+price+").");
						}
					} else {
						Launcher.error("Invalid product name");
					}
				} else {
					Launcher.error("Invalid product type");
				}
			}
		});
		
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 7);
		
		Button back = new Button("Back");
		back.setOnAction(event -> {
			Launcher.loadPage(new BrowsePage(BrowsePage.search));
		});
		grid.add(back, 0, 7);

		
		mainHolder.getChildren().add(grid);
		canvas.getChildren().add(mainHolder);
	}

	private void createVendorSellProduct(Product product, LocalDate localDate) {
		Stage s = Launcher.popup();
		VBox mvb = new VBox();
		mvb.setPadding(new Insets(8,8,8,8));
		mvb.setSpacing(8);
		Scene scene = new Scene(mvb, 350, 225);
		s.setScene(scene);
		s.show();
		
		Text mt = new Text("First-time Vendor information.");
		mt.setWrappingWidth(350);
		mvb.getChildren().add(mt);
		
		// Create GUI
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		mvb.getChildren().add(grid);

		Label userName = new Label("First Name:");
		grid.add(userName, 0, 0);
		final TextField userTextField = new TextField();
		grid.add(userTextField, 1, 0);

		Label pw = new Label("Last Name:");
		grid.add(pw, 0, 1);
		final TextField pwBox = new TextField();
		grid.add(pwBox, 1, 1);

		Label ph = new Label("Telephone:");
		grid.add(ph, 0, 2);
		final TextField phBox = new TextField();
		grid.add(phBox, 1, 2);
		
		Button go = new Button("Create Vending Account");
		mvb.getChildren().add(go);
		go.setOnAction(e -> {
			try {
				Connection c = DBConnect.getConnection();
				PreparedStatement ss = c.prepareStatement("INSERT INTO Vendor(VendorID,VendorFirstName,VendorLastName,VendorPhone) VALUES(?,?,?,?)");
				ss.setInt(1, LoginInformation.UserId);
				ss.setString(2, userTextField.getText());
				ss.setString(3, pwBox.getText());
				ss.setString(4, phBox.getText());
				ss.execute();
				
				String SQL = "UPDATE User SET AccessLevel=3 WHERE AccountID=" + LoginInformation.UserId;
				boolean s2 = c.createStatement().execute(SQL);
				
				LoginInformation.AccessLevel = 3;

				listItem(product, localDate);
				s.close();
				return;
			}catch(Exception ee2) {
				ee2.printStackTrace();
			}
		});
	}
	
	private void listItem(Product product, LocalDate localDate) {
		try {
			Product.createProduct(product);
			
			String SQL = "INSERT INTO Bidding_Items(BiddingItemID,StartTime,EndTime) VALUES(?,?,?)";
			Connection c = DBConnect.getConnection();
			
			LocalTime localTime = LocalTime.now();
			localTime = localTime.minusNanos(localTime.getNano());
			LocalDate nowDate = LocalDate.now();
			
			
			PreparedStatement s = c.prepareStatement(SQL);
			s.setInt(1, product.getProductID());
			s.setString(2, nowDate + " " + localTime);
			s.setString(3, localDate + " " + localTime);
			s.execute();
			
			Launcher.error("Item sucessfully listed!");
			Launcher.loadPage(new BrowsePage(BiddingItem.getBiddingItem(product)));
		} catch (SQLException e) {
			Launcher.error("Error listing product. Please try again later!");
			e.printStackTrace();
		}
	}

	private String[] getProductTypes() {
		if ( cached_product_types == null ) {
			cached_product_types = new HashMap<String,Integer>();
			String SQL = "SELECT * FROM Product_Type";
			try {
				Connection c = DBConnect.getConnection();
				ResultSet r = c.createStatement().executeQuery(SQL);
				if ( r.isBeforeFirst() ) {
					while(r.next()) {
						cached_product_types.put(r.getString(2), r.getInt(1));
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		int i = 0;
		String[] ret = new String[cached_product_types.keySet().size()];
	    Iterator it = cached_product_types.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ret[i++] = (String) pair.getKey();
	    }
	    
	    return ret;
	}

	private int getProductID(Object value) {
		try {
			String v = (String)value;
			int id = cached_product_types.get(v);
			return id;
		}catch(Exception e) {
			
		}
		return -1;
	}
	
}

