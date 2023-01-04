package tn.flashcards.controller;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import lombok.val;
import tn.flashcards.Launcher;
import tn.flashcards.model.Data;
import tn.flashcards.model.settings.AlgoAffichage;
import tn.flashcards.model.settings.StrategyChoix;
import tn.flashcards.model.settings.StrategyChoixProba;
import tn.flashcards.model.settings.StrategyChoixProbaEgales;
import tn.flashcards.model.settings.StrategyChoixTemps;
import tn.flashcards.model.settings.Theme;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;

public class ParameterController implements Initializable, Observateur {

    public ParameterController() {
        /* Theme */
        themeChoices = new ArrayList<String>();
        themeChoices.add("Foncé");
        themeChoices.add("Clair");
        themes = new ArrayList<Theme>();
        themes.add(Theme.FONCE);
        themes.add(Theme.CLAIR);
        sheets = new ArrayList<String>();
        sheets.add(new NordDark().getUserAgentStylesheet());
        sheets.add(new NordLight().getUserAgentStylesheet());
        /* AlgoChoix */
        algoChoixChoices = new ArrayList<String>();
        algoChoixChoices.add("Aléatoire");
        /*
        algoChoixChoices.add("Par probabilité");
        algoChoixChoices.add("Par temps");
        */
        algoChoixs = new ArrayList<StrategyChoix>();
        algoChoixs.add(new StrategyChoixProbaEgales());
        /*
        algoChoixs.add(new StrategyChoixProba());
        algoChoixs.add(new StrategyChoixTemps());
        */
        /* AlgoAffichage */
        algoAffichageChoices = new ArrayList<String>();
        algoAffichageChoices.add("Clic");
        /*
        algoAffichageChoices.add("Chrono");
        */
        algoAffichages = new ArrayList<AlgoAffichage>();
        algoAffichages.add(AlgoAffichage.CLIC);
        /*
        algoAffichages.add(AlgoAffichage.TEMPS);
         */
    }

    
    @FXML
    ChoiceBox<String> themeChoiceBox;
    private ArrayList<String> themeChoices;
    private ArrayList<Theme> themes;
    private ArrayList<String> sheets;

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
    private ArrayList<String> algoChoixChoices;
    private ArrayList<StrategyChoix> algoChoixs;

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
    private ArrayList<String> algoAffichageChoices;
    private ArrayList<AlgoAffichage> algoAffichages;

    private class AlgoAffichageListener implements ChangeListener<Number> {
        public void changed(ObservableValue ov, Number value, Number new_value) {
            Data.getInstance().getSettings().setAlgoAffichage(algoAffichages.get(new_value.intValue()));
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
        themeChoiceBox.setValue(themeChoices.get(themes.indexOf(Data.getInstance().getSettings().getTheme())));
        algoAffichageChoiceBox.setValue(algoAffichageChoices.get(algoAffichages.indexOf(Data.getInstance().getSettings().getAlgoAffichage())));

        /* à gérer avec des instance of */
        if (Data.getInstance().getSettings().getAlgoChoix() instanceof StrategyChoixProbaEgales) {
            algoChoixChoiceBox.setValue(algoChoixChoices.get(0));
        } /* else if (Data.getInstance().getSettings().getAlgoChoix() instanceof StrategyChoixProba) {
            algoChoixChoiceBox.setValue(algoChoixChoices.get(1));
        } else if (Data.getInstance().getSettings().getAlgoChoix() instanceof StrategyChoixTemps) {
            algoChoixChoiceBox.setValue(algoChoixChoices.get(2));
        }*/
    }
}