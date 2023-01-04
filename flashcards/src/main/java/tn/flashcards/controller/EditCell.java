package tn.flashcards.controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import tn.flashcards.model.pile.Card;

public class EditCell extends ListCell<Card> {

    HBox view = new HBox();

    public EditCell()
    {

    }

    @Override
    protected void updateItem(Card c, boolean empty)
    {
        super.updateItem(c,empty);
        if (c==null || empty)
        {
            setGraphic(null);
        }
        else
        {
            view.getChildren().addAll(new Label(String.valueOf(c.getId())), new Button("Appuie ici"));
            setGraphic(view);
        }
    }
}
