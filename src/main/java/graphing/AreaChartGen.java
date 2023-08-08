package graphing;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class AreaChartGen extends Charts{
    private static AreaChartGen areaChartGen;

    public static AreaChartGen getInstance(){
        if(areaChartGen == null)
            areaChartGen = new AreaChartGen();
        return areaChartGen;
    }

    private AreaChartGen(){
        super();
    }

    protected AreaChart<Number, Number> loadchart() {
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0]),Math.floor(xMaxMin[1]),xstep);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        AreaChart areaChart = new AreaChart(xAxis, yAxis);
        areaChart.setPrefHeight(3000);
        areaChart.setPrefWidth(3000);
        areaChart.getData().addAll(series);
        areaChart.setTitle(titletextField.getText());
        addTooltip(series,false);
        return areaChart;
    }
}
