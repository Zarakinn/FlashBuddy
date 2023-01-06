package tn.flashcards.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;
import org.kordamp.ikonli.feather.Feather;
import tn.flashcards.Utils.FileHandler;
import tn.flashcards.components.ActionButtonTableCell;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.StatsPile;

import java.net.URL;
import java.util.ResourceBundle;

import static atlantafx.base.theme.Styles.*;

public class EditController implements Initializable, Observateur {

    @FXML
    public Button startBtn, importBtn;
    @FXML
    TableColumn<Pile, String> cName, cCreateur, cTags, cDesc;
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
    }

    public void createViewTable() {
        cName.setCellFactory(TextFieldTableCell.forTableColumn());
        cName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getName()));
        cName.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        cDesc.setCellFactory(TextFieldTableCell.forTableColumn());
        cDesc.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getDesc()));

        cCreateur.setCellFactory(TextFieldTableCell.forTableColumn());
        cCreateur.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCreator()));
        cCreateur.setOnEditCommit(event -> event.getRowValue().setCreator(event.getNewValue()));

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
                    Data.getInstance().deletePile(p);
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
                    FileHandler.SaveStackInZip(p);
                    return p;
                }));
        cExport.setEditable(false);

        table.setItems(Data.getInstance().getPiles());
        table.setEditable(true);
    }

    @FXML
    public void createPile() {
        Data.getInstance().createPile();
    }

    public void importePile() {
        Pile p = FileHandler.LoadStackFromZip(importBtn.getScene().getWindow());
        if (p != null) {
            Data.getInstance().addPile(p);
        } else {
            System.out.println("Fail to import");
        }
    }

    @Override
    public void reagir() {
        if (Data.getInstance().getMode() == Data.Mode.EDITION_SELECTION) {
            table.setItems(Data.getInstance().getPiles());
            table.refresh();
        }
    }
}