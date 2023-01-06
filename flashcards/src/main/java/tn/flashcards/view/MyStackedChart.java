package tn.flashcards.view;

import java.util.ArrayList;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

public class MyStackedChart extends StackedAreaChart<String, Number> {
    public MyStackedChart(ArrayList<Integer>[] donnees) {
        super(new CategoryAxis(), new NumberAxis());

        XYChart.Series<String, Number> evidentSeries = new XYChart.Series<String, Number>();
        evidentSeries.setName("Evident");
        for (int e : donnees[0]) {
            evidentSeries.getData().add(new XYChart.Data("", e));
        }

        XYChart.Series<String, Number> facileSeries = new XYChart.Series<String, Number>();
        facileSeries.setName("Facile");
        for (int e : donnees[1]) {
            facileSeries.getData().add(new XYChart.Data("", e));
        }

        XYChart.Series<String, Number> moyenSeries = new XYChart.Series<String, Number>();
        moyenSeries.setName("Moyen");
        for (int e : donnees[2]) {
            moyenSeries.getData().add(new XYChart.Data("", e));
        }

        XYChart.Series<String, Number> difficileSeries = new XYChart.Series<String, Number>();
        difficileSeries.setName("Difficile");
        for (int e : donnees[3]) {
            difficileSeries.getData().add(new XYChart.Data("", e));
        }

        XYChart.Series<String, Number> impossibleSeries = new XYChart.Series<String, Number>();
        impossibleSeries.setName("Impossible");
        for (int e : donnees[4]) {
            impossibleSeries.getData().add(new XYChart.Data("", e));
        }

        this.getData().addAll(evidentSeries, facileSeries, moyenSeries, difficileSeries, impossibleSeries);

    }
}