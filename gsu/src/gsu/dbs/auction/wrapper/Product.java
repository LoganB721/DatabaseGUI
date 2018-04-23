package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gsu.dbs.auction.DBConnect;

public class Product {
	private int productID;
	private int vendorID;
	private String productName;
	private String productImage;
	private int productStartingPrice;
	private int productTypeID;
	private int productStatus;
	private String productDescription;
	
	protected Product(int id, int vendor, String pname, String pimg, int j, int k, int l, String string3) {
		this.productID = id;
		this.vendorID = vendor;
		this.productName = pname;
		this.productImage = pimg;
		this.productStartingPrice = j;
		this.productTypeID = k;
		this.productStatus = l;
		this.productDescription = string3;
	}
	
	public int getProductID() {
		return this.productID;
	}
	
	public int getProductTypeID() {
		return this.productTypeID;
	}
	
	public int getVendorID() {
		return this.vendorID;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public String getProductImage() {
		return this.productImage;
	}
	
	public int getStartingPrice() {
		return this.productStartingPrice;
	}
	
	public int getStatus() {
		return this.productStatus;
	}
	
	public void poll() {
		Product copy = getProductFromID(this.productID);
		this.productStatus = copy.getStatus();
	}
	
	public static Product getProductFromID(int id) {
		String SQL = "SELECT * FROM Products WHERE ProductID=" + id + ";";
		try {
			Connection c = DBConnect.getConnection();
			ResultSet r = c.createStatement().executeQuery(SQL);
			if ( r.isBeforeFirst() && r.next() ) {
				Product p = new Product( id, r.getInt(2), r.getString(3), r.getString(4), r.getInt(5), r.getInt(6), r.getInt(7), r.getString(8) );
				return p;
			}
		} catch (SQLException e) {
			//
		}
		
		return null;
	}

	public String getProductDescription() {
		return this.productDescription;
	}
}
