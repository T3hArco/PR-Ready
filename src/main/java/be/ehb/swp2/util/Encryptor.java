package be.ehb.swp2.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        return md5(password);
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
}
