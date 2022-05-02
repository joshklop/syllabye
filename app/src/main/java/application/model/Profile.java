package application.model;

/**
 * Profile is used to hold the profiles of the account
 * @author
 */
public class Profile {
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private String securityQuesAns;
	
	/**
	 * Creates the profile and sets the parameters to the private variables of the given profile
	 * @param firstName is set to private variable firstName
	 * @param lastName is set to private variable lastName
	 * @param userName is set to private variable userName
	 * @param email is set to private variable email
	 * @param password is set to private variable password
	 * @param securityQuesAns is set to private variable securityQuesAns
	 */
	public Profile (String firstName, String lastName, String userName, String email, String password, String securityQuesAns ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.securityQuesAns = securityQuesAns;
	}
	
	/**
	 * returns every private variable in one string separated by :
	 */
	public String toString() {
		return firstName + ":" + lastName + ":" + userName + ":" + email + ":" + password + ":" + securityQuesAns + "\n";
	}
	
	/**
	 * Returns firstName of the profile
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the given firstName to the private variable firstName
	 * @param firstName is used to set private variable firstName to it
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Returns lastName of the profile
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the given LastName to the private variable lastName
	 * @param lastName is used to set private variable lastName to it
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Returns userName of the profile
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Sets the given userName to the private variable userName
	 * @param userName is used to set private variable userName to it
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Returns email of the profile
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the given email to the private variable email 
	 * @param email is used to set private variable email to it
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns password of the profile
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the given password to the private variable password 
	 * @param password is used to set private variable password to it
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Returns security question answer of the profile
	 * @return securityQuesAns
	 */
	public String getSecurityQuesAns() {
		return securityQuesAns;
	}
	
	/**
	 * Sets the given securityQuesAns the private variable securityQuesAns 
	 * @param securityQuesAns is used to set private variable securityQuesAns to it
	 */
	public void setSecurityQuesAns(String securityQuesAns) {
		this.securityQuesAns = securityQuesAns;
	}
	
	
}

