package tn.flashcards.Utils;

import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import tn.flashcards.model.Data;
import tn.flashcards.model.pile.Card;
import tn.flashcards.model.pile.Pile;
import tn.flashcards.model.stats.*;

import com.google.gson.Gson;
import tn.flashcards.model.pile.QRType;
import tn.flashcards.model.pile.QuestionReponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileHandler {

    private static FileChooser fcImg;
    private static FileChooser fcPile;

    public static FileChooser getImageFileChooser() {
        if (fcImg == null) {
            fcImg = new FileChooser();
            fcImg.setTitle("Sélectionner une image");
            FileChooser.ExtensionFilter fileExtensions =
                    new FileChooser.ExtensionFilter(
                            "Image", "*.png", "*.jpeg", "*.bmp", "*.tiff", "*.ico");

            fcImg.getExtensionFilters().add(fileExtensions);
        }
        return fcImg;
    }

    public static FileChooser getPileFileChooser() {
        if (fcPile == null) {
            fcPile = new FileChooser();
            fcPile.setTitle("Sélectionner une pile");
            FileChooser.ExtensionFilter fileExtensions =
                    new FileChooser.ExtensionFilter(
                            "Pile flashCard", "*.zip");

            fcPile.getExtensionFilters().add(fileExtensions);
        }
        return fcPile;
    }

    public static void loadStats() {

    }

    public static void saveStats() {
        /*
        ---------------------------------------------------

            Cette fonction ne fonctionne pas, j'ai beau essayé, il est difficile de séréalizer stats et le temps presse.

        -------------------------------------------------
        */
        try {
            File directory = new File("Stats");
            if (!directory.exists())
                directory.mkdir();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            HashMap<String, StatsPile> stats = Data.getInstance().getStatsPile();

            for (HashMap.Entry<String, StatsPile> entry : stats.entrySet()) {
                String key = entry.getKey();
                StatsPile value = entry.getValue();

                File file = new File("Stats/" + key + ".json");
                FileWriter writer = new FileWriter(file);
                Gson gson = new Gson();
                String json = gson.toJson(value);
                writer.write(json);
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fail to save stats");
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
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'insertion de bytes dans le zip.");
            alert.show();
        }
    }

    private static byte[] imageToData(String fileName) {
        if (fileName.contains(".zip")){
            int index = fileName.indexOf("/", fileName.indexOf(".zip") + ".zip".length());
            String zipPath = fileName.substring(0, index);
            String imagePath = fileName.substring(index + 1);
            File file = new File(zipPath);

            try (ZipInputStream inputStream = new ZipInputStream(new FileInputStream(file));) {
                ZipEntry entry = inputStream.getNextEntry();

                while (entry != null) {
                    if (entry.getName().equals(imagePath)) {

                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                        byte[] imageData = outputStream.toByteArray();
                        outputStream.close();
                        return imageData;
                    }
                    entry = inputStream.getNextEntry();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement d'une image depuis le zip");
                alert.show();
            }
        } else {
            try (FileInputStream inputStream = new FileInputStream(fileName)) {
                byte[] data = new byte[inputStream.available()];
                inputStream.read(data);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la conversion de l'image en bytes pour le zip.");
                alert.show();
            }
        }
        return null;
    }

    public static void HandleImageExport(ZipOutputStream outputStream, QuestionReponse qr)
    {
        if (qr.getType() == QRType.IMAGE) {
            byte[] data = FileHandler.imageToData(Path.of(qr.getContent()).toString());
            if (data == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a eu une erreur dans l'enregistrement de la pile, lors de l'import de l'image " + qr.getContent());
                alert.show();
            } else {
                Path path = Path.of(qr.getContent());
                qr.setContent("img/" + path.getFileName().toString()); // Modification uniquement sur le clone
                addDataToZip(outputStream, "img/" + path.getFileName().toString(), data);
            }
        }
    }

    public static void SaveStackInZip(Pile originalPile) {
        Pile pile = Data.getInstance().clonePile(originalPile); // Un clone qui n'est pas ajouté à Data

        String outputname = pile.getName();
        while (true)
        {
            File f = new File(outputname + ".zip");
            if(f.exists() && !f.isDirectory()) {
                outputname += "(bis)";
            }
            else
            {break;}
        }
        try (ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(outputname + ".zip"))) {
            for (Card c : pile.getCards()) {
                QuestionReponse q = c.getQuestion();
                QuestionReponse r = c.getReponse();

                HandleImageExport(outputStream, q);
                HandleImageExport(outputStream, r);
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(pile);
            addDataToZip(outputStream, "Pile.json", json.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur dans l'enregistrement de la pile au format zip.");
            alert.show();
        }

    }

    public static Image loadImageFromZip(String path) {
        int index = path.indexOf("/", path.indexOf(".zip") + ".zip".length());
        String zipPath = path.substring(0, index);
        String imagePath = path.substring(index + 1);
        File file = new File(zipPath);
        try (ZipInputStream inputStream = new ZipInputStream(new FileInputStream(file));) {
            ZipEntry entry = inputStream.getNextEntry();

            while (entry != null) {
                if (entry.getName().equals(imagePath)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024];

                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                        baos.write(buffer, 0, len);
                    }
                    InputStream is = new ByteArrayInputStream(baos.toByteArray());
                    Image image = new Image(is);
                    return image;
                }
                entry = inputStream.getNextEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement d'une image depuis le zip");
            alert.show();
        }
        return null;
    }

    public static Pile LoadStackFromZip(Window window) {
        File file = FileHandler.getPileFileChooser().showOpenDialog(window);
        if (file != null) {
            try (ZipInputStream inputStream = new ZipInputStream(new FileInputStream(file))) {
                Pile stack = null;
                ZipEntry entry;
                while ((entry = inputStream.getNextEntry()) != null) {
                    if (entry.getName().equals("Pile.json")) {
                        Gson gson = new Gson();
                        stack = gson.fromJson(new InputStreamReader(inputStream), Pile.class);
                    }
                }
                for (Card c : stack.getCards()) {
                    QuestionReponse q = c.getQuestion();
                    QuestionReponse r = c.getReponse();

                    if (q.getType() == QRType.IMAGE) {
                        q.setContent(file.getAbsolutePath() + "/" + q.getContent());
                    }
                    if (r.getType() == QRType.IMAGE) {
                        r.setContent(file.getAbsolutePath() + "/" + r.getContent());
                    }
                }
                return stack;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Fichier non valide, veuillez sélectionner un ZIP créé par cette application.");
                alert.show();
            }
        }
        return null;
    }

    //endregion
}
