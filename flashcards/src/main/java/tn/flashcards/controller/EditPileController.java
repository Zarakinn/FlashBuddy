package tn.flashcards.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
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
        view.setCellFactory(view -> new EditCell(pile)
        );
        name.setText(pile.getName());
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            pile.setName(newValue);
        });
        display();
    }

    public void display() {
        ObservableList<Card> oCards = FXCollections.observableList(Data.getInstance().getCurrentPile().getCards());
        view.setItems(oCards);
    }

    @FXML
    public void AddCard() {
        Data.getInstance().createDefaultCard(Data.getInstance().getCurrentPile().getUniqueId());
    }

    @Override
    public void reagir() {
        display();
    }

    @FXML
    public void Retour() {
        Data.getInstance().setMode(Data.Mode.EDITION);
    }
}
