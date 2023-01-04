package tn.flashcards.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;
import tn.flashcards.Utils.FileHandler;
import tn.flashcards.components.ActionButtonTableCell;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.DateFormat;
import tn.flashcards.model.stats.StatsPile;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static atlantafx.base.theme.Styles.*;

public class EditController implements Initializable, Observateur {

    @FXML
    TableColumn<Pile, String> cName, cCreateur, cTags;
    @FXML
    TableColumn<Pile, Integer> cNb;
    @FXML
    TableColumn<Pile, String> cDate;
    @FXML
    TableColumn<Pile, Button> cEdit, cExport, cDelete;

    @FXML
    TableView<Pile> table;

    public EditController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createViewTable();
        //Data.getIstance().getStatsPile().get(Data.getInstance().getPiles().get(0).getUniqueId()).updateLastOpened(LocalDateTime.now().minusDays(10));
        //Systems.out.println(Data.getInstance().getStatsPile().get(Data.getInstance().getPiles().get(0).getUniqueId()).getLastOpenedFormated());



    }

    public Button createButton(Pile pile){
        var removeBtn = new Button("", new FontIcon(Feather.TRASH));
        removeBtn.getStyleClass().addAll(BUTTON_ICON, BUTTON_OUTLINED, DANGER);
        removeBtn.setOnAction(e -> {
            Data.getInstance().deletePile(pile);
        });
        return removeBtn;
    }

    public void createViewTable() {
        cName.setCellFactory(TextFieldTableCell.forTableColumn());
        cName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        cName.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

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
        cDate.setCellValueFactory(data -> {
            StatsPile pile = Data.getInstance().getStatsPile().get(data.getValue().getUniqueId());
            if (pile == null) {
                return new ReadOnlyObjectWrapper<>("—");
            }
            return new ReadOnlyStringWrapper(pile.getLastOpenedFormated());
        });


        cDelete.setCellFactory(ActionButtonTableCell.forTableColumn("Remove", (Pile p) -> {
            table.getItems().remove(p);
            return p;
        }));

        cDate.setEditable(false);

        table.setItems(Data.getInstance().getPiles());
        table.setEditable(true);
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
    }
}