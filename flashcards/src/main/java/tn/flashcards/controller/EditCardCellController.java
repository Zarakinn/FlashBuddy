package tn.flashcards.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;
import tn.flashcards.Utils.FileHandler;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.VisualFactory.QRViewFactory;

import java.io.File;

import static atlantafx.base.theme.Styles.*;

public class EditCardCellController {
    public Card card;

    @FXML
    public Pane QPane, RPane;
    @FXML
    public ToggleGroup RGroup, QGroup;
    @FXML
    public TextArea r_txt_area, q_txt_area;
    @FXML
    public AnchorPane q_txt_pane, q_img_pane, r_txt_pane, r_img_pane;
    @FXML
    public ImageView q_img, r_img;
    @FXML
    public RadioButton q_txt_btn, q_img_btn, r_txt_btn, r_img_btn;
    @FXML
    public Button deleteBtn;


    @FXML
    public void initialize() {
        unblur(r_txt_area);
        unblur(q_txt_area);
        FontIcon fontIcon = new FontIcon(Feather.TRASH);
        fontIcon.setIconSize(35);
        deleteBtn.setGraphic(fontIcon);
        deleteBtn.getStyleClass().addAll(BUTTON_ICON, BUTTON_OUTLINED, DANGER);
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
            q_img.setImage(((ImageView)(QRViewFactory.createQRView(carte.getQuestion()).getChildren().get(0))).getImage());
        }
        if (rType == QRType.TEXT) {
            r_txt_area.setText(carte.getReponse().getContent());
        } else {
            r_img.setImage(((ImageView)(QRViewFactory.createQRView(carte.getReponse()).getChildren().get(0))).getImage());
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
            card.getQuestion().setContent(f.getAbsolutePath());
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

    @FXML
    public void delete() {
        Data.getInstance().deleteCard(Data.getInstance().getCurrentPile(), card);
    }
}
