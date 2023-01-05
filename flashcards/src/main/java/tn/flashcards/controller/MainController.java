package tn.flashcards.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import tn.flashcards.model.Data;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, Observateur {

    @FXML
    RadioButton b1, b2, b3, b4;

    @FXML
    Pane edit, parameter, stats, training, editpile;

    List<Pane> onglets;

    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Arrays.asList(b1, b2, b3, b4).forEach(
                radioButton -> {
                    radioButton.getStyleClass().remove("radio-button");
                });
        b2.setSelected(true);
        onglets = Arrays.asList(edit, parameter, stats, training, editpile);
        //setEditPile();
    }

    @FXML
    public void setViewEdit() {
        //setView(edit);
        Data.getInstance().setMode(Data.Mode.EDITION);
    }

    @FXML
    public void setViewParameter() {
        //setView(parameter);
        Data.getInstance().setMode(Data.Mode.PARAM);
    }

    @FXML
    public void setViewStats() {
        //setView(stats);
        Data.getInstance().setMode(Data.Mode.STATS);
    }

    @FXML
    public void setViewTraining() {
        //setView(training);
        Data.getInstance().setMode(Data.Mode.APPRENTISSAGE_SELECTION);
    }

    public void setEditPile() {
        //setView(editpile);
    }

    public void setView(Pane pane) {
        //for (Pane p : onglets) {
        //    p.setVisible(p == pane);
        //}
    }

    @Override
    public void reagir() {
        onglets.forEach(pane -> pane.setVisible(false));
        switch (Data.getInstance().getMode()) {
            case EDITION -> edit.setVisible(true);
            case PARAM -> parameter.setVisible(true);
            case STATS -> stats.setVisible(true);
            case APPRENTISSAGE_SELECTION -> training.setVisible(true);
            case EDIT_PILE -> editpile.setVisible(true);
        }
        switch (Data.getInstance().getMode()) {
            case EDITION -> b2.setSelected(true);
            case PARAM -> b4.setSelected(true);
            case STATS -> b3.setSelected(true);
            case APPRENTISSAGE_SELECTION -> b1.setSelected(true);
        }
    }
}