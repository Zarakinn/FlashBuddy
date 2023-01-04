package tn.flashcards.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tn.flashcards.Utils.FileHandler;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Pile;

import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable, Observateur {

    @FXML
    Button b;

    public EditController()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void newStack()
    {


    }

    public void importStack()
    {
        Pile stack = FileHandler.LoadStack( b.getScene().getWindow());
        if (stack !=null)
        {
            // ajouter à la liste
            Data.getInstance();
            //Vérifier qu'on réagit bien
        }
    }

    public void saveStack()
    {
        Pile stack = new Pile(); // Fetch selected
        FileHandler.SaveStackAs(stack,String.valueOf(stack.hashCode())); // changer en stack.name quand dispo
    }


    public void display()
    {

    }


    @Override
    public void reagir() {
        // On pourra vérifier si cette vue est Visible avant de la rafraichir si il y a des problèmes de performances
        display();
    }
}