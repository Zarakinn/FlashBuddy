package tn.flashcards.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.flashcards.components.EditCell;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPileController implements Initializable, Observateur {

    @FXML
    TextField name;
    @FXML
    TextArea desc;
    @FXML
    ListView<Card> view;

    public EditPileController() {
        Pile pile = Data.getInstance().getCurrentPile();
        if(pile != null) {
            Data.getInstance().createCard(pile.getUniqueId(), QRType.TEXT, "la première question", QRType.TEXT, "la première réponse");
            Data.getInstance().createCard(pile.getUniqueId(), QRType.TEXT, "la deuxième question", QRType.TEXT, "la deuxième réponse");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Pile pile = Data.getInstance().getCurrentPile();
        //view.setCellFactory(view -> new EditCell(pile));
        view.setCellFactory(view -> new EditCell());

        name.setText(pile.getName());
        desc.setText(pile.getDesc());

        display();
    }

    public void display() {
        name.setText(Data.getInstance().getCurrentPile().getName());
        desc.setText(Data.getInstance().getCurrentPile().getDesc());
        ObservableList<Card> oCards = FXCollections.observableList(Data.getInstance().getCurrentPile().getCards());
        view.setItems(oCards);
    }

    @FXML
    public void AddCard() {
        Data.getInstance().createDefaultCard(Data.getInstance().getCurrentPile().getUniqueId());
    }

    @Override
    public void reagir() {
        if(Data.getInstance().getMode() == Data.Mode.EDIT_PILE) {
            display();
        }
    }

    @FXML
    public void Retour() {
        Data.getInstance().setMode(Data.Mode.EDITION_SELECTION);
    }

    public void setTitre() {
        Data.getInstance().getCurrentPile().setName(name.getText());
    }

    public void setDesc() {
        Data.getInstance().getCurrentPile().setDesc(desc.getText());
    }
}
