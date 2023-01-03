package tn.flashcards.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, Observateur {

    @FXML
    StackPane ViewHolder;

    Pane Edit;
    Pane Parameter;
    Pane Stats;
    Pane Training;

    List<Pane> onglets;


    public MainController(Pane Edit, Pane Parameter, Pane Stats, Pane Training)
    {
        this.Edit = Edit;
        this.Parameter = Parameter;
        this.Stats = Stats;
        this.Training = Training;

        onglets = Arrays.asList(Edit,Parameter,Stats,Training);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        for (Pane p:onglets)
        {
            ViewHolder.getChildren().add(p);
            p.setVisible(false);
        }
        setView(Training);
    }

    @FXML
    public void setViewEdit()
    {
        setView(Edit);
    }

    @FXML
    public void setViewParameter()
    {
        setView(Parameter);
    }

    @FXML
    public void setViewStats()
    {
        setView(Stats);
    }

    @FXML
    public void setViewTraining()
    {
        setView(Training);
    }


    public void setView(Pane pane)
    {
        for (Pane p: onglets)
        {
            if (p == pane)
                p.setVisible(true);
            else
                p.setVisible(false);
        }
    }




    @Override
    public void reagir() {

    }
}