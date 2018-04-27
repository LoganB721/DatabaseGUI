package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import gsu.dbs.auction.DBConnect;
import gsu.dbs.auction.LoginInformation;

public class Product {
	private int productID;
	private int vendorID;
	private String productName;
	private String productImage;
	private int productStartingPrice;
	private int productTypeID;
	private int productStatus;
	private String productDescription;
	
	public Product(int id, int vendor, String pname, String pimg, int j, int k, int l, String string3) {
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
	
	/**
	 * Get a product object by its ID.
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Create a product on the database from a dummy Product Object.
	 * <br>
	 * The Dummy Product Object does not yet contain an id.
	 * <br>
	 * Upon being added to the database, this dummy object is changed into
	 * A proper wrapped product from the database.
	 * @param p
	 * @throws SQLException
	 */
	public static boolean createProduct(Product p) throws SQLException {
		Connection c = DBConnect.getConnection();
		PreparedStatement ss = c.prepareStatement("INSERT INTO Products(VendorID,ProductName,ImageURL,StartingPrice,ProductTypeID,ProductStatus,ProductDesc) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
		ss.setInt(1, LoginInformation.getVendorID());
		ss.setString(2, p.getProductName());
		ss.setString(3, p.getProductImage());
		ss.setInt(4, p.productStartingPrice);
		ss.setInt(5, p.getProductTypeID());
		ss.setInt(6, 4);
		ss.setString(7, p.getProductDescription());
		ss.execute();
		
		ResultSet r = ss.getGeneratedKeys();
		if (r.next()){
		    p.productID=r.getInt(1);
		    return true;
		}
		return false;
	}

	public String getProductDescription() {
		return this.productDescription;
	}
}
