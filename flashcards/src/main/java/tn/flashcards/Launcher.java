package tn.flashcards;

import atlantafx.base.controls.Spacer;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.flashcards.controller.*;
import tn.flashcards.model.pile.Pile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        // Création modèle
        // TODO -

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Launcher.class.getResource("fxml/main-view.fxml"));

        // Création des controlleurs
        var editController = new EditController();
        var parameterController = new ParameterController();
        var statsController = new StatsController();
        var trainingController = new TrainingController();
        var mainController = new MainController();
        Pile p = new Pile();
        p.setName("temp");
        var editPileController = new EditPileController(p);
        // Ajout des vues au modèle
        // TODO

        fxmlLoader.setControllerFactory(ic -> {
            if (ic.equals(MainController.class)) return mainController;
            if (ic.equals(EditController.class)) return editController;
            if (ic.equals(ParameterController.class)) return parameterController;
            if (ic.equals(StatsController.class)) return statsController;
            if (ic.equals(TrainingController.class)) return trainingController;
            if (ic.equals(EditPileController.class)) return editPileController;
            // ...
            return null;
        });

/*        FXMLLoader editLoader = new FXMLLoader();
        editLoader.setLocation(Launcher.class.getResource("fxml/edit-view.fxml"));
        editLoader.setControllerFactory(factory -> new EditController());
        Pane edit = editLoader.load();

        FXMLLoader statsLoader = new FXMLLoader();
        statsLoader.setLocation(Launcher.class.getResource("fxml/stats-view.fxml"));
        statsLoader.setControllerFactory(factory -> new StatsController());
        Pane stats = statsLoader.load();

        FXMLLoader parameterLoader = new FXMLLoader();
        parameterLoader.setLocation(Launcher.class.getResource("fxml/parameter-view.fxml"));
        parameterLoader.setControllerFactory(factory -> new ParameterController());
        Pane parameter = parameterLoader.load();

        FXMLLoader trainingLoader = new FXMLLoader();
        trainingLoader.setLocation(Launcher.class.getResource("fxml/training-view.fxml"));
        trainingLoader.setControllerFactory(factory -> new TrainingController());
        Pane train = trainingLoader.load();

        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(Launcher.class.getResource("fxml/main-view.fxml"));
        mainLoader.setControllerFactory(factory -> new MainController(edit,parameter,stats,train));
        Pane main = mainLoader.load();*/

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(Objects.requireNonNull(Launcher.class.getResource("css/style.css")).toURI().toString());
        stage.setScene(scene);
        stage.show();

        var t = new TabPane();

        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        //Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    }

    public static void main(String[] args) {
        launch();
    }
}