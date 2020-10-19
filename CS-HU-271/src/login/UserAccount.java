package login;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * An user account.
 * @author youngmamba8
 *
 */
public class UserAccount implements Serializable{
	private String userName;
	private String password;
	private String email;

	/**
	 * Create an user account
	 * @param username The account name for the new user
	 * @param password The password for the new user
	 * @param email An email address for the new user
	 */
	public UserAccount(String username, String password, String email) {
		this.userName = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * Check that account name is of the proper length.
	 * @param username String to check
	 * @return True if meets length requirements, false otherwise
	 */
	public static boolean validUsername(String username) {
		return username.length() >= 3 && username.length() <= 16;
	}

	/**
	 * Check that account name exists in the account manager.
	 * @param username String to check
	 * @return True if account exists, false otherwise
	 */
	public static boolean usernameExists(String username) {
		boolean usernameExists = false;
		for(UserAccount userAccount: UserAccountManager.accountManager) {
			usernameExists |= userAccount.userName.equals(username);
		}
		return usernameExists;
	}

	/**
	 * Check that the user really entered an email.
	 * @param email String to check
	 * @return True if email, false otherwise
	 */
	public static boolean validEmail(String email) {
		return Pattern.matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$", email);
	}

	/**
	 * Check that email exists in the account manager.
	 * @param email String to check
	 * @return True if account exists, false otherwise
	 */
	public static boolean emailExists(String email) {
		boolean emailExists = false;
		for(UserAccount userAccount: UserAccountManager.accountManager) {
			emailExists |= userAccount.email.equals(email);
		}
		return emailExists;
	}

	/**
	 * Check that password is of the proper length.
	 * @param password String to check
	 * @return True if meets length requirements, false otherwise
	 */
	public static boolean validatePassword(String password) {
		return password.length() >= 8 && password.length() <= 128;
	}

	/**
	 * Getter for account username.
	 * @return username
	 */
	public String getUsername() {
		return userName;
	}

	/**
	 * Getter for account password.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter for email.
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email.
	 * @param email Account email address
	 * @return true
	 */
	public boolean setEmail(String email) {
		this.email = email;
		return true;
	}

	/**
	 * Setter for username.
	 * @param username Account username
	 */
	public void setUsername(String username) {
		this.userName = username;
	}

	/**
	 * Setter for password.
	 * @param password Account password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Authenticate!
	 * @param userName Account name
	 * @param password Account password
	 * @return True if able to authenticate based on user input, false otherwise.
	 */
	public boolean isValidCredential(String userName, String password) {
        return matchUserName(userName) && matchPassword(password);
   }
   
	/**
	 * Authenticate account user name.
	 * @param userName Account name
	 * @return True if account usernames match, false otherwise.
	 */
   public boolean matchUserName(String userName) {
        return userName != null && userName.equals(this.userName);
   }
   
   /**
    * Authenticate user password.
    * @param password Account password
    * @return True if account passwords match, false otherwise.
    */
   private boolean matchPassword(String password) {
       return password != null && password.equals(this.password);
  }

}
