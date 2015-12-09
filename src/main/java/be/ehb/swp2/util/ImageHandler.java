package be.ehb.swp2.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by arnaudcoel on 03/12/15.
 */
public class ImageHandler {
    public static byte[] toByteArray(URL path) throws IOException {
        BufferedImage source = ImageIO.read(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] converted;

        ImageIO.write(source, "png", out);
        out.flush();
        converted = out.toByteArray();

        return converted;
    }

    public static ByteArrayInputStream toBlobType(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }
}
