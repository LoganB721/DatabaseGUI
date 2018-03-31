package gsu.dbs.auction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
	private static String SERVER 	= "jdbc:mysql://45.79.216.182";
	private static String DATABASE 	= "AuctionDB";
	private static String USER 		= "root";
	private static String PASSWORD 	= "Orange!9739";
	
	public static void main(String[] args) {
		System.out.println("Connecting to database...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(SERVER + "/" + DATABASE, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
