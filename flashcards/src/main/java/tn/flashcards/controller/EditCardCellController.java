package tn.flashcards.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import tn.flashcards.Utils.FileHandler;
import tn.flashcards.model.pile.Card;

import java.io.File;

public class EditCardCellController {
    public Card card;

    @FXML
    public Pane QPane, RPane;
    @FXML
    public ToggleGroup RGroup, QGroup;
    public TextArea r_txt_area, q_txt_area;
    public AnchorPane q_txt_pane, q_img_pane,r_txt_pane,r_img_pane;
    public ImageView q_img,r_img;


    @FXML
    public void initialize() {
        unblur(r_txt_area);
        unblur(q_txt_area);
    }

    public void setVueCarte(Card carte) {
        this.card = carte;
    }

    public void unblur(TextArea textArea){
        // https://stackoverflow.com/questions/23728517/blurred-text-in-javafx-textarea
        textArea.setCache(false);
        if(textArea.getChildrenUnmodifiable().size() > 0){
            ScrollPane sp = (ScrollPane)textArea.getChildrenUnmodifiable().get(0);
            sp.setCache(false);
            for (Node n : sp.getChildrenUnmodifiable()) {
                n.setCache(false);
            }
        }
    }

    public void q_txt_btn(ActionEvent actionEvent) {
        q_txt_pane.setVisible(true);
        q_img_pane.setVisible(false);
    }

    public void q_img_btn(ActionEvent actionEvent) {
        q_txt_pane.setVisible(false);
        q_img_pane.setVisible(true);
    }

    public void r_txt_btn(ActionEvent actionEvent) {
        r_txt_pane.setVisible(true);
        r_img_pane.setVisible(false);
    }

    public void r_img_btn(ActionEvent actionEvent) {
        r_txt_pane.setVisible(false);
        r_img_pane.setVisible(true);
    }

    public void r_parcourir(ActionEvent actionEvent) {
        File f = FileHandler.getImageFileChooser().showOpenDialog(r_img.getScene().getWindow());
        if(f != null){
            card.getReponse().setContent(f.getAbsolutePath());
            r_img.setImage(new Image(f.getAbsolutePath()));
        }
    }

    public void q_parcourir(ActionEvent actionEvent) {
        File f = FileHandler.getImageFileChooser().showOpenDialog(r_img.getScene().getWindow());
        if(f != null){
            card.getReponse().setContent(f.getAbsolutePath());
            q_img.setImage(new Image(f.getAbsolutePath()));
        }
    }
}
