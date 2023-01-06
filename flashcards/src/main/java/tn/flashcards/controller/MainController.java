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
        b1.setSelected(true);
        onglets = Arrays.asList(edit, parameter, stats, training, editpile);
    }

    @FXML
    public void setViewEdit() {
        Data.getInstance().setMode(Data.Mode.EDITION_SELECTION);
    }

    @FXML
    public void setViewParameter() {
        Data.getInstance().setMode(Data.Mode.PARAM);
    }

    @FXML
    public void setViewStats() {
        Data.getInstance().setMode(Data.Mode.STATS);
    }

    @FXML
    public void setViewTraining() {
        Data.getInstance().setMode(Data.Mode.APPRENTISSAGE_SELECTION);
    }

    @Override
    public void reagir() {
        onglets.forEach(pane -> pane.setVisible(false));
        switch (Data.getInstance().getMode()) {
            case EDITION_SELECTION -> edit.setVisible(true);
            case PARAM -> parameter.setVisible(true);
            case STATS -> stats.setVisible(true);
            case APPRENTISSAGE_SELECTION -> training.setVisible(true);
            case EDIT_PILE -> editpile.setVisible(true);
        }
        switch (Data.getInstance().getMode()) {
            case EDITION_SELECTION, EDIT_PILE -> b2.setSelected(true);
            case PARAM -> b4.setSelected(true);
            case STATS -> b3.setSelected(true);
            case APPRENTISSAGE_SELECTION -> b1.setSelected(true);
        }
    }
}