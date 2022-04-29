package application.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {
	
	public String hash256(String pw) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    byte[] byteOfTextToHash = pw.getBytes(StandardCharsets.UTF_8);
	    byte[] hashedByetArray = digest.digest(byteOfTextToHash);
	    String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
	    return encoded;
	}
}
