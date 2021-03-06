package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

import gsu.dbs.auction.DBConnect;
import gsu.dbs.auction.Launcher;
import gsu.dbs.auction.LoginInformation;
import gsu.dbs.auction.login.DisplayItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BiddingItem {
	private Product product;
	private Timestamp startTime;
	private Timestamp endTime;
	
	public BiddingItem( Product p, Timestamp start, Timestamp end ) {
		this.product = p;
		this.startTime = start;
		this.endTime = end;
	}
	
	public static BiddingItem[] getItemsLive() {
		return getItems(-1);
	}
	
	public static BiddingItem[] getItemsSoldByVendor(int VendorID) {
		return getItems(VendorID);
	}
	
	/**
	 * Return a list of items that are currently up for bid.
	 * <br>
	 * If a sellerID is set, it will return a list of items that are currently up for bid
	 * that are also being sold by the specified seller.
	 * @param sellerId
	 * @return
	 */
	private static BiddingItem[] getItems(int sellerId) {
		String append = "";
		if ( sellerId != -1 ) {
			append = " WHERE p.VendorID = " + sellerId;
		}
		String SQL = "SELECT bi.* FROM Bidding_Items bi JOIN Products p ON bi.BiddingItemID = p.ProductID" + append;
		try {
			Connection c = DBConnect.getConnection();
			ResultSet result = c.createStatement().executeQuery(SQL);
			ArrayList<BiddingItem> temp = new ArrayList<BiddingItem>();
			while(result.next()) {
				int productID = result.getInt(1);
				Timestamp startTime = result.getTimestamp(2);
				Timestamp endTime = result.getTimestamp(3);
				BiddingItem o = new BiddingItem(Product.getProductFromID(productID), startTime, endTime);
				temp.add(o);
			}
			return temp.toArray(new BiddingItem[temp.size()]);
		} catch (SQLException e) {
			Launcher.error("Error connecting to database. Please try again later");
		}
		
		return null;
	}

	public Product getProduct() {
		return this.product;
	}
	
	/**
	 * Get a list of all bids made for this specific product.
	 * @return
	 */
	public BidHistory[] getBidHistory() {
		String SQL = "SELECT * FROM Bid_History WHERE BiddingItemID=" + this.getProduct().getProductID() + " ORDER BY BidPrice DESC";
		try {
			Connection c = DBConnect.getConnection();
			ResultSet res = c.createStatement().executeQuery(SQL);
			ArrayList<BidHistory> temp = new ArrayList<BidHistory>();
			while(res.next()) {
				int bidUser = res.getInt(2);
				int currentBid = res.getInt(4);
				BidHistory o = new BidHistory(this.getProduct(), bidUser, currentBid);
				temp.add(o);
			}
			return temp.toArray(new BidHistory[temp.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public int getCurrentHighestBid() {
		BidHistory[] bids = getBidHistory();
		if ( bids.length > 0 ) {
			return bids[0].getBidAmount();
		}
		
		return -1;
	}

	/**
	 * Called when a client wishes to place a bid on an item.
	 * @param item
	 * @param bidPrice
	 */
	public static void bidOnItem(BiddingItem item, int bidPrice) {
		Connection c;
		try {
			c = DBConnect.getConnection();
			
			if ( LoginInformation.AccessLevel == 1 ) {
				
				Stage s = Launcher.popup();
				VBox mvb = new VBox();
				mvb.setPadding(new Insets(8,8,8,8));
				mvb.setSpacing(8);
				Scene scene = new Scene(mvb, 350, 200);
				s.setScene(scene);
				s.show();
				
				Text mt = new Text("Fill out your customer information.");
				mt.setWrappingWidth(300);
				mvb.getChildren().add(mt);
				
				// Create GUI
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(25, 25, 25, 25));
				mvb.getChildren().add(grid);
	
				Label userName = new Label("First Name:");
				grid.add(userName, 0, 1);
				final TextField userTextField = new TextField();
				grid.add(userTextField, 1, 1);
	
				Label pw = new Label("Last Name:");
				grid.add(pw, 0, 2);
				final TextField pwBox = new TextField();
				grid.add(pwBox, 1, 2);
				
				Button go = new Button("Bid");
				mvb.getChildren().add(go);
				go.setOnAction(e -> {
					try {
						PreparedStatement ss = c.prepareStatement("INSERT INTO Customer(CustomerID,FirstName,LastName) VALUES(?,?,?)");
						ss.setInt(1, LoginInformation.UserId);
						ss.setString(2, userTextField.getText());
						ss.setString(3, pwBox.getText());
						ss.execute();
						
						if ( LoginInformation.AccessLevel < 2 ) {
							String SQL = "UPDATE User SET AccessLevel=2 WHERE AccountID=" + LoginInformation.UserId;
							boolean s2 = c.createStatement().execute(SQL);
							
							LoginInformation.AccessLevel = 2;
							bidOnItem(item, bidPrice);
						}
						return;
					}catch(Exception ee2) {
						ee2.printStackTrace();
					}
				});
			} else {
				PreparedStatement s = c.prepareStatement("INSERT INTO Bid_History(BidNumber,CustomerID,BiddingItemID,BidPrice,SaleStatus) VALUES(?,?,?,?,?)");
				s.setObject(1, null);
				s.setInt(2, LoginInformation.UserId);
				s.setInt(3, item.getProduct().getProductID());
				s.setDouble(4, bidPrice);
				s.setInt(5, 0);
				System.out.println(s.toString());
				s.execute();
				
				Launcher.error("You have sucessfully bid on " + item.getProduct().getProductName() + "!");
			}
			
		} catch (SQLException e) {
			Launcher.error("Error processing bid. Please try again later");
			e.printStackTrace();
		}
	}

	/**
	 * Return a bidding item from a product (if one exists).
	 * @param product
	 * @return
	 * @throws SQLException
	 */
	public static BiddingItem getBiddingItem(Product product) throws SQLException {
		String SQL = "SELECT * FROM Bidding_Items WHERE BiddingItemID = " + product.getProductID();
		Connection c = DBConnect.getConnection();
		ResultSet result = c.createStatement().executeQuery(SQL);
		
		if ( result.isBeforeFirst() && result.next() ) {
			int productID = result.getInt(1);
			Timestamp startTime = result.getTimestamp(2);
			Timestamp endTime = result.getTimestamp(3);
			return new BiddingItem(product, startTime, endTime);
		}
		return null;
	}

	public Timestamp getEndDate() {
		return this.endTime;
	}
	
	public boolean canBidOn() {
		Timestamp now = Timestamp.from(Instant.now());
		Timestamp end = getEndDate();
		long millis = end.getTime()-now.getTime();
		
		return millis > 0;
	}
	
	public String getBidString() {
		Timestamp now = Timestamp.from(Instant.now());
		Timestamp end = getEndDate();
		long millis = end.getTime()-now.getTime();
		
		String remaining = "";
		if ( millis > 0 ) {
			int seconds = (int) ( millis / 1000) % 60 ;
			int minutes = (int) ((millis / (1000*60)) % 60);
			int hours   = (int) ((millis / (1000*60*60)) % 24);
			int days    = (int) ( millis / (1000*60*60*24));
			
			if ( days > 0 ) {
				remaining += days + " day(s) ";
			}
			if ( hours > 0 ) {
				remaining += hours + " hour(s) ";
			} else {
				if ( minutes > 0 ) {
					remaining += minutes + " minute(s) ";
				}			
			}
			if ( days == 0 ) {
				if ( minutes > 0 ) {
					remaining += minutes + " minute(s) ";
				}
				if ( hours < 1 ) {
					remaining += seconds + " second(s)";
				}
			}
		} else {
			remaining = "Bid Finished!";
		}
		
		return remaining;
	}
}
