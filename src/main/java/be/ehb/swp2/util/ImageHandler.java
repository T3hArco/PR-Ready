package be.ehb.swp2.util;

import be.ehb.swp2.exception.BadFileException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;

/**
 * Created by arnaudcoel on 03/12/15.
 */
public class ImageHandler {

    /**
     * Converts an image to a byte array
     *
     * @param path gets the path of the image
     * @return byte array
     * @throws IOException
     * @deprecated
     */
    public static byte[] toByteArray(URL path) throws IOException {
        BufferedImage source = ImageIO.read(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] converted;

        ImageIO.write(source, "png", out);
        out.flush();
        converted = out.toByteArray();

        return converted;
    }

    /**
     * Converts byte array to a blob
     *
     * @param bytes the byte array
     * @return ByteArrayInputStream
     * @deprecated no longer using blobs
     */
    public static ByteArrayInputStream toBlobType(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Converts the image to base64
     *
     * @param path
     * @return
     * @throws BadFileException
     */
    public static String toBase64(URL path) throws BadFileException {
        String[] allowedTypes = new String[]{"jpeg", "jpg", "png", "gif"};
        String base64 = null;

        try {
            BufferedImage source = ImageIO.read(path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String extension = path.toString().substring(path.toString().lastIndexOf(".") + 1);

            if (!Arrays.asList(allowedTypes).contains(extension))
                throw new BadFileException("Extension not allowed");

            ImageIO.write(source, extension, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            base64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadFileException("Failed to convert");
        }

        if (base64 == null)
            throw new BadFileException("Failed to convert");

        return base64;
    }
}
