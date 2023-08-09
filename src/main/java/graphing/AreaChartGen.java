package graphing;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class AreaChartGen extends Charts{
    private static AreaChartGen areaChartGen;  // The singleton of the class

    /***
     * Returns the singleton instance of the class and creates a new instance of the class if no instance exists.
     * @return the singleton instance of the class.
     */
    public static AreaChartGen getInstance(){
        if(areaChartGen == null)
            areaChartGen = new AreaChartGen();
        return areaChartGen;
    }

    /***
     * Constructor
     */
    private AreaChartGen(){
        super();
    }

    /***
     * This function calls GenerateXYseries to get the datapoints and then turn the data into an area chart.
     * @return a AreaChart which was made from the data points of the CSV files.
     * @throws IllegalArgumentException Thrown an error if a CSV file contains incorrectly formatted data.
     */
    protected AreaChart<Number, Number> loadchart() throws IllegalArgumentException{
        // Gets the data points from the CSV files
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        // Generates the step the X axis so that there is 30 steps between min and max, it rounds the step to nearest 5 if it's greater than 5 so that the axis numbers are smooth
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0]),Math.floor(xMaxMin[1]),xstep);
        NumberAxis yAxis = new NumberAxis();
        // Allows yAxis to create its own step as the y values should be already nicely formatted
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        // Sets the names of axis to be the text fields
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        AreaChart areaChart = new AreaChart(xAxis, yAxis);
        // Sets preferred width and height so the ratio is 1:1
        areaChart.setPrefHeight(3000);
        areaChart.setPrefWidth(3000);
        areaChart.getData().addAll(series);
        // Sets the title of the chart to be the text field value
        areaChart.setTitle(titletextField.getText());
        // Adds tooltips to each data point
        addTooltip(series,false);
        return areaChart;
    }
}
