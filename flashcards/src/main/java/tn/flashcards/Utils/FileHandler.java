package tn.flashcards.Utils;

import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import tn.flashcards.model.pile.Pile;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileHandler {

    private static FileChooser fcImg;
    private static FileChooser fcPile;

    public static FileChooser getImageFileChooser()
    {
        if (fcImg == null)
        {
            fcImg = new FileChooser();
            fcImg.setTitle("Sélectionner une image");
            FileChooser.ExtensionFilter fileExtensions =
                    new FileChooser.ExtensionFilter(
                            "Image", "*.png","*.jpeg","*.bmp","*.tiff","*.ico");

            fcImg.getExtensionFilters().add(fileExtensions);
        }
        return fcImg;
    }

    public static FileChooser getPileFileChooser()
    {
        if (fcPile == null)
        {
            fcPile = new FileChooser();
            fcPile.setTitle("Sélectionner une pile");
            FileChooser.ExtensionFilter fileExtensions =
                    new FileChooser.ExtensionFilter(
                            "Pile flashCard", "*.json","*.txt");

            fcImg.getExtensionFilters().add(fileExtensions);
        }
        return fcPile;
    }

    public static Pile LoadStack(Window window) {
        File file = FileHandler.getPileFileChooser().showOpenDialog(window);
        if (file != null) {
            try {
                Gson gson = new Gson();
                Pile stack = gson.fromJson(new FileReader(file), Pile.class);
                return stack;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fichier non valid, veuillez sélectionner un JSON créé par cette application.");
                alert.show();
            }
        }
        return null;
    }

    public static void SaveStack(Pile stack) {
        SaveStackAs(stack, String.valueOf(stack.getName()) + ".json");
    }

    public static void SaveStackAs(Pile stack, String name) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(stack);
        try {
            FileWriter fw = new FileWriter(name);
            fw.write(json);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a eu une erreur dans l'enregistrement de la pile.");
            alert.show();
        }
    }

}
