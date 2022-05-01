package application.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Accounts {

    private ArrayList<Profile> profiles = new ArrayList<Profile>();
    private static final String FILENAME = "/data/accountdata.txt";

    public static String getFilename() {
        return FILENAME;
    }

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

    public void addProfile (Profile p) {
        profiles.add(p);
    }

    public boolean isBlank (String firstName, String lastName, String userName, String email, 
            String password, String confirmPass, String securityQuesAns) {
        if (firstName.isBlank() || lastName.isBlank() || userName.isBlank() || email.isBlank() ||
                password.isBlank() || confirmPass.isBlank() || securityQuesAns.isBlank())
            return true;
        return false;
    }

    public boolean isPasswordSame (String password, String confirmPass) {
        return password.equals(confirmPass);
    }

    public boolean isUserNameValid (String userName) {
        if (profiles.size() == 0)
            return true;
        for (int i = 0; i < profiles.size(); i++) {
            if (userName.equals(profiles.get(i).getUserName()))
                return false;
        }
        return true;
    }

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
