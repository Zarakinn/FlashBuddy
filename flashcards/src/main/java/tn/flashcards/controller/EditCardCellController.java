package tn.flashcards.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import tn.flashcards.Utils.FileHandler;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.QRType;

import java.io.File;

public class EditCardCellController {
    public Card card;

    @FXML public Pane QPane, RPane;
    @FXML public ToggleGroup RGroup, QGroup;
    @FXML public TextArea r_txt_area, q_txt_area;
    @FXML public AnchorPane q_txt_pane, q_img_pane, r_txt_pane, r_img_pane;
    @FXML public ImageView q_img, r_img;
    @FXML public RadioButton q_txt_btn, q_img_btn, r_txt_btn, r_img_btn;


    @FXML
    public void initialize() {
        unblur(r_txt_area);
        unblur(q_txt_area);
    }

    public void setVueCarte(Card carte) {
        this.card = carte;
        QRType qType = carte.getQuestion().getType();
        QRType rType = carte.getReponse().getType();

        q_txt_pane.setVisible(qType == QRType.TEXT);
        q_img_pane.setVisible(qType == QRType.IMAGE);

        r_txt_pane.setVisible(rType == QRType.TEXT);
        r_img_pane.setVisible(rType == QRType.IMAGE);

        q_txt_btn.setSelected(qType == QRType.TEXT);
        q_img_btn.setSelected(qType == QRType.IMAGE);

        r_txt_btn.setSelected(rType == QRType.TEXT);
        r_img_btn.setSelected(rType == QRType.IMAGE);

        if (qType == QRType.TEXT) {
            q_txt_area.setText(carte.getQuestion().getContent());
        } else {
            q_img.setImage(new Image(carte.getQuestion().getContent()));
        }
        if (rType == QRType.TEXT) {
            r_txt_area.setText(carte.getReponse().getContent());
        } else {
            r_img.setImage(new Image(carte.getReponse().getContent()));
        }
    }

    public void unblur(TextArea textArea) {
        // https://stackoverflow.com/questions/23728517/blurred-text-in-javafx-textarea
        textArea.setCache(false);
        if (textArea.getChildrenUnmodifiable().size() > 0) {
            ScrollPane sp = (ScrollPane) textArea.getChildrenUnmodifiable().get(0);
            sp.setCache(false);
            for (Node n : sp.getChildrenUnmodifiable()) {
                n.setCache(false);
            }
        }
    }

    @FXML
    public void q_txt_btn() {
        q_txt_pane.setVisible(true);
        q_img_pane.setVisible(false);
        card.getQuestion().setType(QRType.TEXT);
    }

    @FXML
    public void q_img_btn() {
        q_txt_pane.setVisible(false);
        q_img_pane.setVisible(true);
        card.getQuestion().setType(QRType.IMAGE);
    }

    @FXML
    public void r_txt_btn() {
        r_txt_pane.setVisible(true);
        r_img_pane.setVisible(false);
        card.getReponse().setType(QRType.TEXT);
    }

    @FXML
    public void r_img_btn() {
        r_txt_pane.setVisible(false);
        r_img_pane.setVisible(true);
        card.getReponse().setType(QRType.IMAGE);
    }

    @FXML
    public void r_parcourir() {
        File f = FileHandler.getImageFileChooser().showOpenDialog(r_img.getScene().getWindow());
        if (f != null) {
            card.getReponse().setContent(f.getAbsolutePath());
            r_img.setImage(new Image(f.getAbsolutePath()));
        }
    }

    @FXML
    public void q_parcourir() {
        File f = FileHandler.getImageFileChooser().showOpenDialog(r_img.getScene().getWindow());
        if (f != null) {
            card.getReponse().setContent(f.getAbsolutePath());
            q_img.setImage(new Image(f.getAbsolutePath()));
        }
    }

    @FXML
    public void q_textarea() {
        card.getQuestion().setContent(q_txt_area.getText());
    }

    @FXML
    public void r_textarea() {
        card.getReponse().setContent(r_txt_area.getText());
    }
}
