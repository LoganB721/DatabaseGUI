package gsu.dbs.auction.wrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import gsu.dbs.auction.DBConnect;

public class Customer {
	private String firstName;
	private String lastName;
	private int customerID;
	
	public Customer(int customerID) {
		this.customerID = customerID;
		
		String SQL = "SELECT * FROM Customer WHERE CustomerID=" + customerID;
		try {
			Connection c = DBConnect.getConnection();
			ResultSet r = c.createStatement().executeQuery(SQL);
			if ( r.isBeforeFirst() && r.next() ) {
				firstName = r.getString("FirstName");
				lastName = r.getString("LastName");
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
	
	public int getCustomerID() {
		return customerID;
	}

}
