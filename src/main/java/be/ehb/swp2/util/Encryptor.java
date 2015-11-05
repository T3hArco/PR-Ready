package be.ehb.swp2.util;

import be.ehb.swp2.entity.User;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;

/**
 * Created by arnaudcoel on 22/10/15.
 */
public class Encryptor {

    /**
     * Will return an MD5 hash of a provided password
     * @param password
     * @return MD5 hash
     */
    public static String hashPassword(String password) {
        if(isMD5(password))
            return password;

        return md5(password);
    }

    /**
     * Generates a unique token for this user to be cross references for his authentication
     * @param user
     * @return unique token
     */
    public static String generateToken(User user) {
        SecureRandom random = new SecureRandom();
        String token = Encryptor.hashPassword(user.getId() + new BigInteger(130, random).toString(32));

        return token;
    }

    /**
     * Creates an MD5 hash
     * @param text
     * @return
     */
    private static String md5(String text) {
        MessageDigest m = null;
        String output = "";

        try {
            m = MessageDigest.getInstance("MD5");

            m.reset();
            m.update(text.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            output = bigInt.toString(16);

            while(output.length() < 32 ){
                output = "0" + output;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static boolean isMD5(String check) {
        return check.matches("[a-fA-F0-9]{32}");
    }
}
