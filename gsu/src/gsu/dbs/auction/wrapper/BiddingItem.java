package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private Date startTime;
	private Date endTime;
	
	public BiddingItem( Product p, Date start, Date end ) {
		this.product = p;
		this.startTime = start;
		this.endTime = end;
	}
	
	public static BiddingItem[] getItemsLive() {
		return getItems(true);
	}
	
	public static BiddingItem[] getItemsSold() {
		return getItems(false);
	}
	
	private static BiddingItem[] getItems(boolean onSale) {
		String SQL = "SELECT bi.* FROM Bidding_Items bi JOIN Products p ON bi.BiddingItemID = p.ProductID WHERE bi.SaleStatus = "+(onSale?0:1)+";";
		try {
			Connection c = DBConnect.getConnection();
			ResultSet result = c.createStatement().executeQuery(SQL);
			ArrayList<BiddingItem> temp = new ArrayList<BiddingItem>();
			while(result.next()) {
				int productID = result.getInt(1);
				Date startTime = result.getDate(2);
				Date endTime = result.getDate(3);
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
						
						String SQL = "UPDATE User SET AccessLevel=2 WHERE AccountID=" + LoginInformation.UserId;
						boolean s2 = c.createStatement().execute(SQL);
						
						LoginInformation.AccessLevel = 2;
						bidOnItem(item, bidPrice);
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
}
