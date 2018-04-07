package domain;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	private String hashtext;
	private BigInteger number;
	private byte[] messageDigest;
	private MessageDigest md;
	
	public MD5(){
		
	}
	 
    public String getMD5(String input) throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("MD5");
        messageDigest = md.digest(input.getBytes());
        number = new BigInteger(1, messageDigest);
        hashtext = number.toString(16);
        
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}