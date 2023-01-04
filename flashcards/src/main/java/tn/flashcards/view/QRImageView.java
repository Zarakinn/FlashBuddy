package tn.flashcards.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QRImageView extends QRView {
    private ImageView img ;

    public QRImageView(String path) {
        super() ;
        this.img = new ImageView();
        this.img.setImage(new Image(path)) ;
        this.getChildren().add(this.img) ;
    }
}
