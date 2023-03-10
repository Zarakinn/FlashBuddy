package tn.flashcards;

import atlantafx.base.theme.NordDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.flashcards.controller.*;
import tn.flashcards.model.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        // Création modèle
        Data model = Data.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Launcher.class.getResource("fxml/main-view.fxml"));

        var controllers = Arrays.asList(
                new EditController(),
                new ParameterController(),
                new StatsController(),
                new TrainingController(),
                new MainController(),
                new EditPileController());

        // Ajout des vues au modèle
        controllers.forEach(model::ajouterObservateur);

        fxmlLoader.setControllerFactory(ic ->
                controllers.stream()
                        .filter(c -> c.getClass().equals(ic))
                        .findFirst().orElse(null)
        );

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(Objects.requireNonNull(Launcher.class.getResource("css/style.css")).toURI().toString());

        Image icon = new Image(Launcher.class.getResource("img/icon.png").toURI().toString());
        stage.getIcons().add(icon);
        stage.show();

        stage.setScene(scene);
        stage.setTitle("FlashBuddy");
        stage.show();


        Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        model.notifierObservateur();
    }

    public static void main(String[] args) {
        launch();
    }
}