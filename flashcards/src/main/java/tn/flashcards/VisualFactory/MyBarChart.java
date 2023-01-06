package tn.flashcards.VisualFactory;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Prend un tableau d'entiers créé par model.stats.Calculs.tenLastDays(Pile p))
 * comme paramètre et l'utilise pour créer un BarChart JavaFX.
 */
public class MyBarChart extends BarChart<String, Number> {
    public MyBarChart(int[] donnees) {
        super(new CategoryAxis(), new NumberAxis());

        String[] legends = {"9 jours", "8 jours", "7 jours", "6 jours", "5 jours", "4 jours", "3 jours", "2 jours",
                "Hier", "Aujourd'hui"};

        this.getXAxis().setLabel("Date");
        this.getYAxis().setLabel("Nombre de parties");

        XYChart.Series<String, Number> singleSeries = new XYChart.Series<String, Number>();

        for (int i = 0; i < 10; i++) {
            singleSeries.getData().add(new XYChart.Data<String, Number>(legends[i], donnees[i]));
        }

        this.getData().addAll(singleSeries);

        this.setLegendVisible(false);
    }
}
