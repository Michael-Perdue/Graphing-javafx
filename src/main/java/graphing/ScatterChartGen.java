package graphing;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class ScatterChartGen extends Charts{

    private static ScatterChartGen scatterChartGen;

    public static ScatterChartGen getInstance(){
        if(scatterChartGen == null)
            scatterChartGen = new ScatterChartGen();
        return scatterChartGen;
    }

    private ScatterChartGen(){
        super();
    }

    protected ScatterChart<Number, Number> loadchart() {
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0] - xstep),Math.floor(xMaxMin[1] + xstep),xstep);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        ScatterChart scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setPrefHeight(3000);
        scatterChart.setPrefWidth(3000);
        scatterChart.getData().addAll(series);
        scatterChart.setTitle(titletextField.getText());
        addTooltip(series,false);
        return scatterChart;
    }

}
