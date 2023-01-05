package tn.flashcards.components;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.function.Function;

public class ActionButtonTableCell<S> extends TableCell<S, Button> {

    private final Button actionButton;
    public static final String GRADIENT_BTN = "gradient_btn";

    public ActionButtonTableCell(String label, String[] styles, Feather fontIcon, Function<S, S> function) {
        this.getStyleClass().add("action-button-table-cell");

        if(fontIcon != null) {
            actionButton = new Button("", new FontIcon(fontIcon));
        } else {
            actionButton = new Button(label);
        }
        this.actionButton.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        });
        this.actionButton.getStyleClass().addAll(styles);
    }

    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>>
    forTableColumn(String label,
                   String[] styles,
                   Feather fontIcon,
                   Function<S, S> function) {
        return param -> new ActionButtonTableCell<>(label, styles, fontIcon, function);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}
