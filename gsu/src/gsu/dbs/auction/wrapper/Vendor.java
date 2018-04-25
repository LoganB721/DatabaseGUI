package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gsu.dbs.auction.DBConnect;

public class Vendor {
	private String firstName;
	private String lastName;
	private int vendorID;
	private int totalSales;
	
	public Vendor(int vendorID) {
		this.vendorID = vendorID;
		
		String SQL = "SELECT * FROM Vendor WHERE VendorID=" + vendorID;
		try {
			Connection c = DBConnect.getConnection();
			ResultSet r = c.createStatement().executeQuery(SQL);
			if ( r.isBeforeFirst() && r.next() ) {
				firstName = r.getString("VendorFirstName");
				lastName = r.getString("VendorLastName");
				totalSales = r.getInt("TotalSales");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}
	
	public int getVendorID() {
		return vendorID;
	}

	public int getSales() {
		return totalSales;
	}

	public Review[] getReviews() {
		String SQL = "SELECT * FROM Customer_Review WHERE VendorID=" + this.vendorID;
		try {
			Connection c = DBConnect.getConnection();
			ResultSet r = c.createStatement().executeQuery(SQL);

			ArrayList<Review> temp = new ArrayList<Review>();
			while(r.next()) {
				int cid = r.getInt(1);
				int rat = r.getInt(3);
				String comment = r.getString(4);
				
				Review rev = new Review( new Customer(cid), this, rat, comment );
				temp.add(rev);
			}
			
			return temp.toArray(new Review[temp.size()]);
		} catch (SQLException e) {
			//
		}
		
		return new Review[0];
	}

}
