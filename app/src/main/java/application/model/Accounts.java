package application.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * The Accounts class represents a list of accounts
 * 
 * @author
 */
public class Accounts {

    private ArrayList<Profile> profiles = new ArrayList<Profile>();
    private static final String FILENAME = "/data/accountdata.txt";

    /**
     * Returns the name of the file
     * @return File name of the data
     */
    public static String getFilename() {
        return FILENAME;
    }

    /**
     *	Gets the information from the file and loads the data inside it to a profile
     */
    public void loadAccountsInfo() {
        FileReader fr;
        try {
            fr = new FileReader(getClass().getResource(FILENAME).getPath());
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null && !(line.isBlank())) {
                String info[] = line.split(":");
                Profile p = new Profile(info[0], info[1], info[2], info[3], info[4], info[5]);
                addProfile(p);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new profile to the ArrayList
     * @param p becomes a new profile account
     */
    public void addProfile (Profile p) {
        profiles.add(p);
    }

    /**
     * Used to check if any of the parameters is blank and returns true or false based upon that fact
     * @param firstName is checked if blank
     * @param lastName is checked if blank
     * @param userName is checked if blank
     * @param email is checked if blank
     * @param password is checked if blank
     * @param confirmPass is checked if blank
     * @param securityQuesAns is checked if blank
     * @return boolean true or false, based on if any of the parameters were blank
     */
    public boolean isBlank (String firstName, String lastName, String userName, String email, 
            String password, String confirmPass, String securityQuesAns) {
        if (firstName.isBlank() || lastName.isBlank() || userName.isBlank() || email.isBlank() ||
                password.isBlank() || confirmPass.isBlank() || securityQuesAns.isBlank())
            return true;
        return false;
    }

    /**
     * Checks to see if the two parameters are the same
     * @param password is checked with parameter confirmPass to see if they are equal to each other
     * @param confirmPass is checked with parameter password to see if they are equal to each other
     * @return boolean true or false based on whenever or not the two parameters are equal to each other
     */
    public boolean isPasswordSame (String password, String confirmPass) {
        return password.equals(confirmPass);
    }

    /**
     * Checks to see if the userName parameter is in the profiles.  
     * @param userName used to see if its the same as other userNames within the profiles 
     * @return boolean value true or false based on if there is a userName with the same values in the profiles
     */
    public boolean isUserNameValid (String userName) {
        if (profiles.size() == 0)
            return true;
        for (int i = 0; i < profiles.size(); i++) {
            if (userName.equals(profiles.get(i).getUserName()))
                return false;
        }
        return true;
    }
    
    /**
     * Writes a line to the file containing the data of a profile
     * @param firstName is added to String line which is then written to the file
     * @param lastName is added to String line which is then written to the file
     * @param userName is added to String line which is then written to the file
     * @param email is added to String line which is then written to the file
     * @param password is added to String line which is then written to the file
     * @param securityQuesAns is added to String line which is then written to the file
     */
    public void appendToFile(String firstName, String lastName, String userName, String email, 
            String password, String securityQuesAns) {
        String line = firstName + ":" + lastName + ":" + userName + ":" + email + ":" + password + ":" + securityQuesAns + "\n";
        try {
            FileWriter fw = new FileWriter(getClass().getResource(FILENAME).getPath(), true);
            BufferedWriter br = new BufferedWriter(fw);
            br.write(line);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if the account with the given userName and password is valid
     * @param userName is used to check if there is a matching userName
     * @param password is used to check if there is a matching password
     * @return boolean value true or false based on if both userName and password is valid
     * @throws NoSuchAlgorithmException 
     */
    public boolean isValidaccount(String userName, String password) throws NoSuchAlgorithmException {
    	String hashed = new Hash().hash256(password);
    	String userN;
        String passW;
        for (int i = 0; i < profiles.size(); i++) {
            userN = profiles.get(i).getUserName();
            passW = profiles.get(i).getPassword();
            if (userN.equals(userName)) {
                if (passW.equals(hashed))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
    
    /**
     * Checks to see if the account given is valid
     * @param userName is used to check if there is a matching userName
     * @param email is used to check if there is a matching email
     * @param securityAnswer is used to check if there is a matching securityAnswer
     * @return boolean true or false value based on if all parameters was matching or not
     */
    public boolean isPasswordRecoveryAccountValid(String userName, String email, String securityAnswer) {
    	String userN;
    	String eml;
    	String sAns;
    	
    	for (int i = 0; i < profiles.size(); i++) {
    		userN = profiles.get(i).getUserName();
    		eml = profiles.get(i).getEmail();
    		sAns = profiles.get(i).getSecurityQuesAns();
    		if (userN.equals(userName)) {
    			if (eml.equals(email)) {
    				if (sAns.equals(securityAnswer)) {
    					return true;
    				}
    				else {
    					return false;
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Changes the password of an account
     * @param userName is used to check if there is a matching userName
     * @param email is used to check if there is a matching email
     * @param securityAnswer is used to check if there is a matching securityAnswer
     * @param password is used to replace the current password
     * @throws NoSuchAlgorithmException if there is an exception of the matching type
     */
    public void changePassword(String userName, String email, String securityAnswer, String password) throws NoSuchAlgorithmException {
    	String userN;
    	String eml;
    	String sAns;
    	String hashed = new Hash().hash256(password);
    	
    	for (int i = 0; i < profiles.size(); i++) {
    		userN = profiles.get(i).getUserName();
    		eml = profiles.get(i).getEmail();
    		sAns = profiles.get(i).getSecurityQuesAns();
    		if (userN.equals(userName)) {
    			if (eml.equals(email)) {
    				if (sAns.equals(securityAnswer)) {
    					profiles.get(i).setPassword(hashed);
    					//read from initialize method, and override it.
    					
    				}
    			}
    		}
    	}
    }
}
