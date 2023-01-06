package tn.flashcards.VisualFactory;

import javafx.scene.control.Label;


public class QRTextView extends QRView {
    private Label content;

    public QRTextView(String txt) {
        super();
        this.content = new Label(txt);
        this.getChildren().add(this.content);
    }
}
