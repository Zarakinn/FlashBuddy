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
import javafx.scene.layout.BorderPane;
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
import static tn.flashcards.components.ActionButtonTableCell.GRADIENT_BTN;

public class EditController implements Initializable, Observateur {

    @FXML
    public Button startBtn, importBtn;
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
    @FXML
    BorderPane container;

    public EditController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createViewTable();
        //startBtn.getStyleClass().add(GRADIENT_BTN);
        //Data.getIstance().getStatsPile().get(Data.getInstance().getPiles().get(0).getUniqueId()).updateLastOpened(LocalDateTime.now().minusDays(10));
        //Systems.out.println(Data.getInstance().getStatsPile().get(Data.getInstance().getPiles().get(0).getUniqueId()).getLastOpenedFormated());
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


        cDelete.setCellFactory(ActionButtonTableCell.forTableColumn(
                "", new String[]{BUTTON_ICON, BUTTON_OUTLINED, DANGER},
                Feather.TRASH,
                (Pile p) -> {
                    table.getItems().remove(p);
                    return p;
                }));

        cEdit.setCellFactory(ActionButtonTableCell.forTableColumn(
                "", new String[]{BUTTON_ICON, BUTTON_OUTLINED, ACCENT},
                Feather.EDIT,
                (Pile p) -> {
                    Data.getInstance().setCurrentPile(p);
                    Data.getInstance().setMode(Data.Mode.EDIT_PILE);
                    return p;
                }));
        cDate.setEditable(false);

        cExport.setCellFactory(ActionButtonTableCell.forTableColumn(
                "Export", new String[]{BUTTON_ICON, BUTTON_OUTLINED}, Feather.SAVE,
                (Pile p) -> {
                    FileHandler.SaveStackAs(p,p.getName());
                    return p;
                }));
        cExport.setEditable(false);

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

    @FXML
    public void saveStack() {
        Pile stack = new Pile(); // Fetch selected
        FileHandler.SaveStackAs(stack, String.valueOf(stack.getName()));
    }

    public void display() {
        table.setItems(Data.getInstance().getPiles());
    }

    @FXML
    public void createPile() {
        Data.getInstance().createPile("Nom", "Auteur");
    }

    @Override
    public void reagir() {
        // On pourra vérifier si cette vue est Visible avant de la rafraichir s'il y a des problèmes de performances
        display();
    }
}