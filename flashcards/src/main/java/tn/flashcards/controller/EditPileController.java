package tn.flashcards.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        List<Card> cards = Arrays.asList( new Card(26), new Card(38));
        cards.get(0).setQuestion(new QuestionReponse(QRType.TEXT, "la première question"));
        cards.get(0).setReponse(new QuestionReponse(QRType.TEXT, "la première réponse"));
        cards.get(1).setQuestion(new QuestionReponse(QRType.TEXT, "la deuxième question"));
        cards.get(1).setReponse(new QuestionReponse(QRType.TEXT, "la deuxième réponse"));


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


    @Override
    public void reagir() {

    }
}
