package gsu.dbs.auction;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

//Class for connecting to the server
public class DBConnect {
	
	private static String SERVER 	= "jdbc:mysql://45.79.216.182";
	private static String USER 		= "root";
	private static String PASSWORD 	= "Orange!9739";
	
	private static HashMap<String,Connection> connections = new HashMap<String,Connection>();
	
	public static Connection connect(String database) throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch (InstantiationException ie) {
				System.err.println("ie: " + ie.getMessage());
		}catch (ClassNotFoundException cnf) {
			System.err.println("cnf: " + cnf.getMessage());
		}catch (IllegalAccessException ia) {
			System.err.println("ia: " + ia.getMessage());
		}
		
		Connection c = DriverManager.getConnection(SERVER + "/" + database, USER, PASSWORD);
		connections.put(database, c);
		return c;
	}

	public static Connection getConnection() throws SQLException {
		return getConnection("AuctionDB");
	}
	
	public static Connection getConnection(String database) throws SQLException {
		Connection c = connections.get(database);
		if(c != null && !c.isClosed()) {
			return c;
		}
		return connect(database);
	}
}
