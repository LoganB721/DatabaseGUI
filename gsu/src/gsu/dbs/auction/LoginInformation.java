package gsu.dbs.auction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginInformation {
	public static String User = null;
	public static String UserSalt = null;
	public static int UserId = 0;
	public static int AccessLevel = 0;

	public static String error;

	/**
	 * This is a blocking action that will check your login information.
	 * Return true if user credentials are correct.
	 * Return false if user supplied incorrect login information.
	 * @param text
	 * @param text2
	 * @return
	 */
	public static boolean login(String username, String password) {
		if ( username == null || password == null ) {
			error = "You must type a username or password.";
			return false;
		}
		
		try {
			Connection c = DBConnect.getConnection();
			PreparedStatement s = c.prepareStatement("SELECT Username,AccountId,AccessLevel FROM User WHERE Username = ? AND Password = ?");
			s.setString(1, username);
			s.setString(2, password);
			ResultSet result = s.executeQuery();
			
			if ( !result.next() ) {
				error = "Incorrect username/password.";
				return false;
			} else {
				// Set the logged in user-name. Used throughout application.
				User = result.getString(1);
				UserSalt = "TemporarySalt"; // NOT USED YET
				UserId = result.getInt(2);
				AccessLevel = result.getInt(3);
				
				// Logged in!
				return true;
			}
		}catch(Exception e) {
			error = "Code 1";
			e.printStackTrace();
			return false;
		}
	}

	public static boolean adminlogin(String username, String password) {
		// This is just a test. To check the failed login screen. 
		if (!username.equals("Admin") || !password.equals("password") || username == null || password == null || username.length() == 0 || password.length() == 0) {
			return false;
		}


		// Set the logged in user-name. Used throughout application.
		User = username;
		UserSalt = "TemporarySalt";
		UserId = 1;
		AccessLevel = 3; //3 is admin level

		// Tell login screen we're logged in.
		return true;
	}

}


