package tn.flashcards.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import tn.flashcards.Launcher;
import tn.flashcards.controller.EditCardCellController;
import tn.flashcards.controller.Observateur;
import tn.flashcards.model.pile.Card;

public class EditCell extends ListCell<Card> implements Observateur {

    private final Parent root;
    private final EditCardCellController controller;

    public EditCell() {
        super();
        FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("fxml/edit-cell.fxml"));
        controller = new EditCardCellController();
        loader.setControllerFactory(ic -> {
            if (ic.equals(EditCardCellController.class)) return controller;
            return null;
        });
        try {
            root = loader.load();
            this.setPrefHeight(200);
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    @Override
    protected void updateItem(Card carte, boolean empty) {
        super.updateItem(carte, empty);
        if (empty || carte == null) {
            setGraphic(null);
        } else {
            controller.setVueCarte((Card) carte);
            setGraphic(root);
        }
    }

    @Override
    public void reagir() {

    }
}

