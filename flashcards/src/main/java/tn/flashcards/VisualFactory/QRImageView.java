package tn.flashcards.VisualFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.flashcards.Launcher;
import tn.flashcards.Utils.FileHandler;

import java.io.File;

public class QRImageView extends QRView {
    public ImageView imgView ;

    public QRImageView(String path) {
        super() ;
        this.imgView = new ImageView();
        try {
            Image img;
            if (path.contains(".zip"))
            {
                img = FileHandler.loadImageFromZip(path);
                if (img == null)
                    throw new Exception();
            }
            else
            {
                File file = new File(path);
                img = new Image(file.toURI().toString());
                if (img.isError())
                {
                    throw new Exception();
                }
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

    public void setSize(double mH, double mW) {
        this.imgView.setFitHeight(mH); ;
        this.imgView.setFitWidth(mW); ;
    }
}
