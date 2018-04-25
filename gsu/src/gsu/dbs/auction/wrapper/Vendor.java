package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}
