package tn.flashcards.VisualFactory;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import tn.flashcards.Launcher;


public class QRTextView extends QRView {
    private final Label content;

    public QRTextView(String txt) {
        super();
        this.content = new Label(txt);
        content.setFont(new Font("Webdings", 18));
        this.getChildren().add(this.content);
    }
}
