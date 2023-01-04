package tn.flashcards.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EditPileController implements Initializable, Observateur {

    @FXML
    TextField name;

    @FXML
    ListView<Card> view;

    Pile pile;

    List<Card> cards;

    public EditPileController(Pile pile)
    {
        this.pile = pile;
        cards = new ArrayList<Card>();
        cards.add(new Card(26));
        cards.add(new Card(38));
        cards.get(0).setQuestion(new QuestionReponse(QRType.TEXT, "la première question"));
        cards.get(0).setReponse(new QuestionReponse(QRType.TEXT, "la première réponse"));
        cards.get(1).setQuestion(new QuestionReponse(QRType.TEXT, "la deuxième question"));
        cards.get(1).setReponse(new QuestionReponse(QRType.TEXT, "la deuxième réponse"));


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Card> oCards = FXCollections.observableList(cards);
        view.setItems(oCards);
        view.setCellFactory(pile ->new EditCell()
        );
        name.setText(pile.getName());
        name.textProperty().addListener((observable, oldValue, newValue) -> {pile.setName(newValue);});
    }

    public void getRows()
    {

    }

    public void display()
    {

    }

    @FXML
    public void AddCard()
    {
        cards.add(Data.getInstance().createDefaultCard(pile.getUniqueId()));

        ObservableList<Card> oCards = FXCollections.observableList(cards);
        view.setItems(oCards);
    }

    @Override
    public void reagir() {

    }
}
