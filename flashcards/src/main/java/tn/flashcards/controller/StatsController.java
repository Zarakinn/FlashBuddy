package tn.flashcards.controller;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Pile;

import java.net.URL;
import java.util.*;

public class StatsController implements Initializable, Observateur {

    @FXML
    private ListView lv ;
    @FXML
    private VBox right ;
    @FXML
    private Pane statsActivity ;
    @FXML
    private Pane statsAnswers ;

    public StatsController()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void reagir() {
        ObservableList<Pile> piles = Data.getInstance().getPiles() ;

        ObservableList<String> list = FXCollections.observableArrayList() ;

        list.add("Générales") ;
        for (Pile p:piles) {
            list.add(p.getName()) ;
        }

        lv.setItems(list);
    }

    @FXML
    public void printStats(MouseEvent e) {
        this.right.setVisible(true);
    }
}