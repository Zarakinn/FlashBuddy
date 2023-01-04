package tn.flashcards.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.flashcards.Launcher;

public class QRImageView extends QRView {
    public ImageView imgView ;

    public QRImageView(String path) {
        super() ;
        this.imgView = new ImageView();
        try {
            Image img = new Image(path);
            imgView.setImage(img);
        }
        catch (Exception e)
        {
            Image img = new Image(Launcher.class.getResource("/tn/flashcards/img/notfound.jpeg").toString());
            imgView.setImage(img);
        }
        imgView.setPreserveRatio(true);
        this.getChildren().add(this.imgView);
    }
}
