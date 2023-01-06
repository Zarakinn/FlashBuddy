package tn.flashcards.controller;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.flashcards.model.Data;
import tn.flashcards.model.settings.StrategyChoix;
import tn.flashcards.model.settings.StrategyChoixProba;
import tn.flashcards.model.settings.StrategyChoixProbaEgales;
import tn.flashcards.model.settings.Theme;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;

public class ParameterController implements Initializable, Observateur {

    public ParameterController() {
        /* Theme */
        themeChoices = new ArrayList<String>();
        themeChoices.add("Nuit");
        themeChoices.add("Jour");
        themes = new ArrayList<Theme>();
        themes.add(Theme.FONCE);
        themes.add(Theme.CLAIR);
        sheets = new ArrayList<String>();
        sheets.add(new NordDark().getUserAgentStylesheet());
        sheets.add(new NordLight().getUserAgentStylesheet());
        /* AlgoChoix */
        algoChoixChoices = new ArrayList<String>();
        algoChoixChoices.add("Aléatoire");
        algoChoixChoices.add("Par probabilité");
        /*
        algoChoixChoices.add("Par temps");
        */
        algoChoixs = new ArrayList<StrategyChoix>();
        algoChoixs.add(new StrategyChoixProbaEgales());
        algoChoixs.add(new StrategyChoixProba());
        /*
        algoChoixs.add(new StrategyChoixTemps());
        */
        /* AlgoAffichage */
        algoAffichageChoices = new ArrayList<String>();
        algoAffichageChoices.add("Clic");
        algoAffichageChoices.add("5 secondes");
        algoAffichageChoices.add("15 secondes");
        algoAffichageChoices.add("30 secondes");
        algoAffichages = new ArrayList<Integer>();
        algoAffichages.add(-1);
        algoAffichages.add(5);
        algoAffichages.add(15);
        algoAffichages.add(30);
    }

    @FXML
    private TextField auteurArea;

    @FXML
    public void setDefaultAuthor() {
        Data.getInstance().getSettings().setAuteur(this.auteurArea.getText());
    }

    @FXML
    ChoiceBox<String> themeChoiceBox;
    private final ArrayList<String> themeChoices;
    private final ArrayList<Theme> themes;
    private final ArrayList<String> sheets;


    private class themeListener implements ChangeListener<Number> {
        public void changed(ObservableValue ov, Number value, Number new_value) {
            Data.getInstance().getSettings().setTheme(themes.get(new_value.intValue()));
            Application.setUserAgentStylesheet(sheets.get(new_value.intValue()));
        }
    }

    /*
     * ********** ********** **********
     * ********** AlgoChoice **********
     * ********** ********** **********
     */
    @FXML
    ChoiceBox<String> algoChoixChoiceBox;
    private final ArrayList<String> algoChoixChoices;
    private final ArrayList<StrategyChoix> algoChoixs;

    private class AlgoChoixListener implements ChangeListener<Number> {
        public void changed(ObservableValue ov, Number value, Number new_value) {
            Data.getInstance().getSettings().setAlgoChoix(algoChoixs.get(new_value.intValue()));
        }
    }

    /*
     * ********** ********** **********
     * ******* AffichageChoice ********
     * ********** ********** **********
     */
    @FXML
    ChoiceBox<String> algoAffichageChoiceBox;
    private final ArrayList<String> algoAffichageChoices;
    private final ArrayList<Integer> algoAffichages;

    private class AlgoAffichageListener implements ChangeListener<Number> {
        public void changed(ObservableValue ov, Number value, Number new_value) {
            Data.getInstance().getSettings().setTimerAffichage(algoAffichages.get(new_value.intValue()));
        }
    }

    /*
     * ********** ********** **********
     * ********** initialize **********
     * ********** ********** **********
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        themeChoiceBox.setItems(FXCollections.observableArrayList(themeChoices));
        themeChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new themeListener());

        algoChoixChoiceBox.setItems(FXCollections.observableArrayList(algoChoixChoices));
        algoChoixChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new AlgoChoixListener());

        algoAffichageChoiceBox.setItems(FXCollections.observableArrayList(algoAffichageChoices));
        algoAffichageChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new AlgoAffichageListener());

        this.reagir();
    }

    @Override
    public void reagir() {
        auteurArea.setText(Data.getInstance().getSettings().getAuteur());
        themeChoiceBox.setValue(themeChoices.get(themes.indexOf(Data.getInstance().getSettings().getTheme())));
        algoAffichageChoiceBox.setValue(algoAffichageChoices.get(algoAffichages.indexOf(Data.getInstance().getSettings().getTimerAffichage())));

        /* à gérer avec des instance of */
        if (Data.getInstance().getSettings().getAlgoChoix() instanceof StrategyChoixProbaEgales) {
            algoChoixChoiceBox.setValue(algoChoixChoices.get(0));
        } else if (Data.getInstance().getSettings().getAlgoChoix() instanceof StrategyChoixProba) {
            algoChoixChoiceBox.setValue(algoChoixChoices.get(1));
        } /* else if (Data.getInstance().getSettings().getAlgoChoix() instanceof StrategyChoixTemps) {
            algoChoixChoiceBox.setValue(algoChoixChoices.get(2));
        }*/
    }
}