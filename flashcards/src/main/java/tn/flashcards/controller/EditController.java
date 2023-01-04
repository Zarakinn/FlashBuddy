package tn.flashcards.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.util.converter.IntegerStringConverter;
import tn.flashcards.Utils.FileHandler;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.DateFormat;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditController implements Initializable, Observateur {

    @FXML
    TableColumn<Pile, String> cName, cCreateur, cTags;
    @FXML
    TableColumn<Pile, Integer> cNb;
    @FXML
    TableColumn<Pile, String> cDate;
    @FXML
    TableColumn<Pile, ImageView> cEdit, cExport;

    @FXML
    TableView<Pile> table;

    public EditController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(0));
        cards.add(new Card(1));
        final ObservableList<Pile> dataTest = FXCollections.observableArrayList(
                new Pile("user1", "pile1", "moi", cards, 0, "tag1"),
                new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag2"),
                new Pile("user1", "pile1", "moi", new ArrayList<Card>(), 0, "tag3")
        );

        cName.setCellFactory(TextFieldTableCell.forTableColumn());
        cName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        cName.setOnEditCommit(event -> {
            event.getRowValue().setName(event.getNewValue());
            System.out.println(dataTest.get(0).getName());
        });

        cCreateur.setCellFactory(TextFieldTableCell.forTableColumn());
        cCreateur.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCreator()));
        cCreateur.setEditable(false);

        cTags.setCellFactory(TextFieldTableCell.forTableColumn());
        cTags.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTags()));
        cTags.setOnEditCommit(event -> event.getRowValue().setTags(event.getNewValue()));

        cNb.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        cNb.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getCards().size()));
        cNb.setEditable(false);

        cDate.setCellFactory(TextFieldTableCell.forTableColumn());
        cDate.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(LocalDateTime.now().format(DateFormat.getDateTimeFormatter())));
        cDate.setEditable(false);

        table.getItems().addAll(Data.getInstance().getPiles());
        table.setEditable(true);
        System.out.println("Table initialized");
    }

    public void newStack()
    {


    }

/*    public void importStack()
    {
        Pile stack = FileHandler.LoadStack( b.getScene().getWindow());
        if (stack !=null)
        {
            // ajouter à la liste
            Data.getInstance();
            //Vérifier qu'on réagit bien
        }
    }*/

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