package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gsu.dbs.auction.DBConnect;
import gsu.dbs.auction.Launcher;

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
		String SQL = "SELECT bi.*,ProductName,ImageURL FROM Bidding_Items bi JOIN Products p ON bi.BiddingItemID = p.ProductID WHERE bi.SaleStatus = "+(onSale?0:1)+";";
		try {
			Connection c = DBConnect.getConnection();
			ResultSet result = c.createStatement().executeQuery(SQL);
			ArrayList<BiddingItem> temp = new ArrayList<BiddingItem>();
			while(result.next()) {
				int productID = result.getInt(1);
				String productName = result.getString(5);
				String imageURL = result.getString(6);
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
}
