package application.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Used to hash the given value
 * @author
 */
public class Hash {
	
	/**
	 * Hashes the given string
	 * @param pw is used to become encoded 
	 * @return the string encoded
	 * @throws NoSuchAlgorithmException if there is an exception of the same type
	 */
	public String hash256(String pw) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    byte[] byteOfTextToHash = pw.getBytes(StandardCharsets.UTF_8);
	    byte[] hashedByetArray = digest.digest(byteOfTextToHash);
	    String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
	    return encoded;
	}
}
