package tn.flashcards.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EditPileController implements Initializable, Observateur {

    @FXML
    ListView<Card> view;


    public EditPileController()
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        List<Card> cards = Arrays.asList( new Card(26), new Card(38));

        ObservableList<Card> oCards = FXCollections.observableList(cards);
        view.setItems(oCards);
        view.setCellFactory(pile ->new EditCell()
        );

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
