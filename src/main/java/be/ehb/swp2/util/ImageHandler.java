package be.ehb.swp2.util;

import be.ehb.swp2.exception.BadFileException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

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

    public static String toBase64(URL path) throws BadFileException {
        /*BufferedImage source = ImageIO.read(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        baos.flush();
        Base64 base = new Base64(false);
        String encodedImage = base.encodeToString(baos.toByteArray());
        baos.close();
        encodedImage = java.net.URLEncoder.encode(encodedImage, "ISO-8859-1");
        request.setRequestBody(encodedImage);*/

        String[] allowedTypes = new String[]{"jpeg", "jpg", "png", "gif"};

        try {
            BufferedImage source = ImageIO.read(path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            String extension = path.toString().substring(path.toString().lastIndexOf(".") + 1);

            if (!Arrays.asList(allowedTypes).contains(extension))
                throw new BadFileException("Extension not allowed");


        } catch (IOException e) {
            throw new BadFileException("test");
        }

        return null;
    }
}
