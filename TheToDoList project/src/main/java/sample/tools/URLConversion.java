package sample.tools;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;

public class URLConversion {

    private URLConversion() {
    }

    private static String getCellResourceLocation(File file) {
        String localUrl = "";

        try {
            localUrl = file.toURI().toURL().toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return localUrl;
    }

    public static ImageView dialogImage(String pathname) {
        File file = new File(
                pathname);

        Image newImage = new Image(getCellResourceLocation(file));
        return new ImageView(newImage);
    }
}
