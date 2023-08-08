package graphing;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class StackedAreaChartGen extends Charts{
    private static StackedAreaChartGen stackedAreaChartGen;

    public static StackedAreaChartGen getInstance(){
        if(stackedAreaChartGen == null)
            stackedAreaChartGen = new StackedAreaChartGen();
        return stackedAreaChartGen;
    }

    private StackedAreaChartGen(){
        super();
    }

    protected StackedAreaChart<Number, Number> loadchart() {
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0]),Math.floor(xMaxMin[1]),xstep);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        StackedAreaChart stackedAreaChart = new StackedAreaChart(xAxis, yAxis);
        stackedAreaChart.setPrefHeight(3000);
        stackedAreaChart.setPrefWidth(3000);
        stackedAreaChart.getData().addAll(series);
        stackedAreaChart.setTitle(titletextField.getText());
        addTooltip(series,false);
        return stackedAreaChart;
    }
}
