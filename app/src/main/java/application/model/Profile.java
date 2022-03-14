package application.model;

public class Profile {
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private String securityQuesAns;
	
	public Profile (String firstName, String lastName, String userName, String email, String password, String securityQuesAns ) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.securityQuesAns = securityQuesAns;
	}
	
	public String toString() {
		return firstName + ":" + lastName + ":" + userName + ":" + email + ":" + password + ":" + securityQuesAns + "\n";
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
		
	public String getSecurityQuesAns() {
		return securityQuesAns;
	}
	
	public void setSecurityQuesAns(String securityQuesAns) {
		this.securityQuesAns = securityQuesAns;
	}
	
	
}

