package gsu.dbs.auction;

public class LoginInformation {
	public static String User = null;
	public static String UserSalt = null;
	public static int UserId = 0;
	public static int AccessLevel = 0;
	
	public static boolean error = false;;
	
	/**
	 * This is a blocking action that will check your login information.
	 * Return true if user credentials are correct.
	 * Return false if user supplied incorrect login information.
	 * @param text
	 * @param text2
	 * @return
	 */
	public static boolean login(String username, String password) {
		// This is just a test. To check the failed login screen. 
		if (username.equals("fail") || username == null || password == null || username.length() == 0 || password.length() == 0) {
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


