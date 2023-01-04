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
    ListView<Card> view;

    Pile pile;

    public EditPileController(Pile pile)
    {
        this.pile = pile;

        Data.getInstance().createCard(pile.getUniqueId(), QRType.TEXT,"la première question",QRType.TEXT,"la première réponse");
        Data.getInstance().createCard(pile.getUniqueId(), QRType.TEXT,"la deuxième question",QRType.TEXT,"la deuxième réponse");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        view.setCellFactory(view -> new EditCell(pile)
        );
        name.setText(pile.getName());
        name.textProperty().addListener((observable, oldValue, newValue) -> {pile.setName(newValue);});
        display();
    }

    public void display()
    {
        ObservableList<Card> oCards = FXCollections.observableList(pile.getCards());
        view.setItems(oCards);
    }

    @FXML
    public void AddCard()
    {
        Data.getInstance().createDefaultCard(pile.getUniqueId());
    }

    @Override
    public void reagir() {
        display();
    }
}
