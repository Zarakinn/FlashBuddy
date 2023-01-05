package tn.flashcards.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.chart.*;

/**
 * Prend un tableau d'entiers créé par model.stats.Calculs.camembert(Pile p))
 * comme paramètre et l'utilise pour créer un PieChart JavaFX.
 */
public class MyPieChart extends PieChart {
    public MyPieChart(int[] donnees) {
        super();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Evident", donnees[0]),
                new PieChart.Data("Facile", donnees[1]),
                new PieChart.Data("Moyen", donnees[2]),
                new PieChart.Data("Difficile", donnees[3]),
                new PieChart.Data("Impossible", donnees[4]),
                new PieChart.Data("Non jouées", donnees[5]));

        this.setData(pieChartData);
        this.setLabelLineLength(10);
        this.setLegendSide(Side.LEFT);
    }
}