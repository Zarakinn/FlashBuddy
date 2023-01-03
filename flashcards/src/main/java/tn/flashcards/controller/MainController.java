package tn.flashcards.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, Observateur {

    @FXML
    StackPane ViewHolder;

    @FXML
    RadioButton b1, b2, b3, b4;

    @FXML
    Pane edit, parameter, stats, training;

    List<Pane> onglets;

    public MainController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Arrays.asList(b1, b2, b3, b4).forEach(radioButton -> radioButton.getStyleClass().remove("radio-button"));
        onglets = Arrays.asList(edit, parameter, stats, training);
        setViewTraining();
    }

    @FXML
    public void setViewEdit() {
        setView(edit);
    }

    @FXML
    public void setViewParameter() {
        setView(parameter);
    }

    @FXML
    public void setViewStats() {
        setView(stats);
    }

    @FXML
    public void setViewTraining() {
        setView(training);
    }


    public void setView(Pane pane) {
        for (Pane p : onglets) {
            p.setVisible(p == pane);
        }
    }

    @Override
    public void reagir() {

    }
}