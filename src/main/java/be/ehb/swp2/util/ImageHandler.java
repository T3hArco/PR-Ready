package be.ehb.swp2.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by arnaudcoel on 03/12/15.
 */
public class ImageHandler {
    public static byte[] toByteArray (String path) throws IOException {
        File imagePath = new File(path);
        BufferedImage bufferedImage = ImageIO.read(imagePath);

        WritableRaster writableRaster = bufferedImage.getRaster();
        DataBufferByte dataBufferByte = (DataBufferByte) writableRaster.getDataBuffer();

        return (dataBufferByte.getData());
    }
}
