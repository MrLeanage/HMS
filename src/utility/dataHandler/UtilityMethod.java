package utility.dataHandler;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class UtilityMethod {
    public static String addPrefix(String prefix, String id) {
        return prefix + id;
    }

    public static int seperateID(String strID) {
        String[] part = strID.split("(?<=\\D)(?=\\d)");
        //part[0] gives Prefix, part [1] gives numeric part
        //System.out.println(part[0]);
        //System.out.println(part[1]);
        return Integer.parseInt(part[1]);
    }

    public static InputStream convertImageToInputStream(ImageView imageView)  {
        Image image = imageView.getImage();

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", outputStream);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        InputStream fileInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return fileInputStream;
    }

    public static ImageView convertInputStreamToImage(InputStream inputStream)  {
        ImageView imageView = new ImageView();
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File("photo.jpg"));
            byte[] content = new byte[1024];
            int size = 0;
            while ((size = inputStream.read(content)) != -1){
                outputStream.write(content, 0, size);
            }
            outputStream.close();
            inputStream.close();
            Image image = new Image("file:photo.jpg");
            imageView.setImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageView;
    }

    public static boolean checkDataAvailability(ArrayList<String> list, String value){
        return list.contains(value);
    }
}
