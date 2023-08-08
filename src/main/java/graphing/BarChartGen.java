package graphing;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class BarChartGen extends BarChartGens{
    private static BarChartGen barChartGen;

    public static BarChartGen getInstance(){
        if(barChartGen == null)
            barChartGen = new BarChartGen();
        return barChartGen;
    }

    protected BarChartGen(){
        super();
    }

    protected BarChart<String, Number> loadchart(){
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        BarChart barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().addAll(series);
        barChart.setTitle(titletextField.getText());
        barChart.setPrefWidth(3000);
        barChart.setPrefHeight(3000);
        addTooltip(series,true);
        return barChart;
    }

}
