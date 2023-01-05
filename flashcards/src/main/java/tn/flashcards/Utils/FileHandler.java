package tn.flashcards.Utils;

import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;


import com.google.gson.Gson;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

            fcPile.getExtensionFilters().add(fileExtensions);
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
        SaveStackAs(stack, String.valueOf(stack.getName()) + ".json");;
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

    //region zip

    private static void addDataToZip(ZipOutputStream outputStream, String fileName, byte[] data) {
        ZipEntry entry = new ZipEntry(fileName);
        try {
            outputStream.putNextEntry(entry);
            outputStream.write(data);
            outputStream.closeEntry();
        } catch (Exception e) {
        }
    }

    private static byte[] imageToData(String fileName) {
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void HandleImageImport(ZipOutputStream outputStream, QuestionReponse qr)
    {
        if (qr.getType() == QRType.IMAGE)
        {
            byte[] data = FileHandler.imageToData(Path.of(qr.getContent()).toString());
            if (data == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a eu une erreur dans l'enregistrement de la pile, lors de l'import de l'image " + qr.getContent());
                alert.show();
            }
            else{
                Path path = Path.of(qr.getContent());
                addDataToZip(outputStream,"img/" + path.getFileName().toString(),data);
                System.out.println("Filename : " + path.getFileName());

                // TODO - changer dans qr le chemin de l'image pour qu'il soit relatif au zip
                // qr.setContent("img/" + path.getFileName().toString());
            }

        }
    }

    public static void SaveStackInZip(Pile pile)
    {
        try (ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(pile.getName() + ".zip"))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(pile);
            addDataToZip(outputStream,"Pile.json",json.getBytes());

            // TODO - faire une copie pour ques les changements de chemin n'affecte pas l'original

            for (Card c: pile.getCards())
            {
                QuestionReponse q = c.getQuestion();
                QuestionReponse r = c.getReponse();

                HandleImageImport(outputStream,q);
                HandleImageImport(outputStream,r);
            }
        } catch (Exception e) {
        }

    }

    public static Pile LoadStackFromZip(Window window)
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Zip Files", "*.zip")
        );
        File file = fc.showOpenDialog(window);
        if (file != null) {
            try (ZipInputStream  inputStream= new ZipInputStream(new FileInputStream(file))){
                Pile stack = null;
                ZipEntry entry;
                while ((entry = inputStream.getNextEntry()) != null) {
                    System.out.println("Entry name : " + entry.getName());
                    if (entry.getName().equals("Pile.json")) {
                        Gson gson = new Gson();
                        stack = gson.fromJson(new InputStreamReader(inputStream), Pile.class);
                    }
                }
                return stack;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fichier non valide, veuillez sélectionner un ZIP créé par cette application.");
                alert.show();
            }
        }
        System.out.println("File null");
        return null;
    }


    //endregion
}
