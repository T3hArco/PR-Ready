package be.ehb.swp2.util;

import java.io.UnsupportedEncodingException;
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
    private static String md5(String text)  {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(text.getBytes("UTF-8"));

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }

            digest = sb.toString();

        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return digest;
    }
}
