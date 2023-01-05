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
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.Calculs;
import tn.flashcards.view.MyBarChart;
import tn.flashcards.view.MyPieChart;

import java.net.URL;
import java.util.*;

public class StatsController implements Initializable, Observateur {
    @FXML
    private Label labelActivty;
    @FXML
    private Label labelAnswers ;
    @FXML
    private ListView lv ;
    @FXML
    private ScrollPane right ;
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
        ObservableList<Pile> piles = Data.getInstance().getPiles() ;
        this.right.setVisible(true);
        int i = lv.getSelectionModel().getSelectedIndex() ;

        if (i >= 1) {
            MyBarChart bc = new MyBarChart(Calculs.tenLastDays(piles.get(i-1))) ;
            this.statsActivity.getChildren().setAll(bc) ;
            MyPieChart pc = new MyPieChart(Calculs.camembert(piles.get(i-1))) ;
            this.statsAnswers.getChildren().setAll(pc) ;
            this.labelActivty.setManaged(true);
            this.statsActivity.setManaged(true);
            this.labelActivty.setVisible(true);
            this.statsActivity.setVisible(true);

        }
        else if (i == 0) {
            MyPieChart pc = new MyPieChart(Calculs.camembert()) ;
            this.statsAnswers.getChildren().setAll(pc) ;
            this.labelActivty.setManaged(false);
            this.statsActivity.setManaged(false);
            this.labelActivty.setVisible(false);
            this.statsActivity.setVisible(false);
        }
    }
}