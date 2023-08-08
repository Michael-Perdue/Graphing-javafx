package graphing;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.ArrayList;

public class LineChartGen extends Charts{

    private static LineChartGen lineChartGen;

    public static LineChartGen getInstance(){
        if(lineChartGen == null)
            lineChartGen = new LineChartGen();
        return lineChartGen;
    }

    private LineChartGen(){
        super();
    }

    protected LineChart<Number, Number> loadchart(){
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0]),Math.floor(xMaxMin[1]),xstep);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        lineChart.setPrefHeight(3000);
        lineChart.setPrefWidth(3000);
        lineChart.getData().addAll(series);
        lineChart.setTitle(titletextField.getText());
        addTooltip(series,true);
        return lineChart;
    }

    protected void updateChart(){
        chart = loadchart();
        vBoxGraph.getChildren().clear();
        vBoxGraph.getChildren().add(chart);
    }

}
