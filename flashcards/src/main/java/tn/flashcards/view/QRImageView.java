package tn.flashcards.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.flashcards.Launcher;

import java.io.File;

public class QRImageView extends QRView {
    public ImageView imgView ;

    public QRImageView(String path) {
        super() ;
        this.imgView = new ImageView();
        try {
            File file = new File(path);
            Image img = new Image(file.toURI().toString());
            if (img.isError())
            {
                throw new Exception();
            }
            imgView.setImage(img);
        }
        catch (Exception e)
        {
            Image img = new Image(Launcher.class.getResource("/tn/flashcards/img/notfound.jpeg").toString());
            imgView.setImage(img);
        }
        finally {
            imgView.setPreserveRatio(true);
            this.getChildren().add(this.imgView);
        }
    }
}
