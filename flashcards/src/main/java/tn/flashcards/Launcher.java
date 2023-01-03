package tn.flashcards;

import atlantafx.base.theme.NordDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.flashcards.controller.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        // Création modèle
        // TODO -

        FXMLLoader editLoader = new FXMLLoader();
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
        Pane main = mainLoader.load();

        Scene scene = new Scene(main, 1000, 700);
        scene.getStylesheets().add(Objects.requireNonNull(Launcher.class.getResource("css/style.css")).toURI().toString());
        stage.setScene(scene);
        stage.show();

        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        //Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
    }

    public static void main(String[] args) {
        launch();
    }
}