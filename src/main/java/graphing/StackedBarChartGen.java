package graphing;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class StackedBarChartGen extends BarChartGens{
    private static StackedBarChartGen stackedBarChartGen;

    public static StackedBarChartGen getInstance(){
        if(stackedBarChartGen == null)
            stackedBarChartGen = new StackedBarChartGen();
        return stackedBarChartGen;
    }

    private StackedBarChartGen(){
        super();
    }

    protected StackedBarChart<String, Number> loadchart(){
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        StackedBarChart stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.getData().addAll(series);
        stackedBarChart.setTitle(titletextField.getText());
        stackedBarChart.setPrefWidth(3000);
        stackedBarChart.setPrefHeight(3000);
        addTooltip(series,true);
        return stackedBarChart;
    }

}
