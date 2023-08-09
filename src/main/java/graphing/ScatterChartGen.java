package graphing;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class ScatterChartGen extends Charts{

    private static ScatterChartGen scatterChartGen; // The singleton of the class

    /***
     * Returns the singleton instance of the class and creates a new instance of the class if no instance exists.
     * @return the singleton instance of the class.
     */
    public static ScatterChartGen getInstance(){
        if(scatterChartGen == null)
            scatterChartGen = new ScatterChartGen();
        return scatterChartGen;
    }

    /***
     * Constructor
     */
    private ScatterChartGen(){
        super();
    }

    /***
     * This function calls GenerateXYseries to get the datapoints and then turn the data into a scatter chart.
     * @return a ScatterChart which was made from the data points of the CSV files.
     * @throws IllegalArgumentException Thrown an error if a CSV file contains incorrectly formatted data.
     */
    protected ScatterChart<Number, Number> loadchart() throws IllegalArgumentException{
        // Gets the data points from the CSV files
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        // Generates the step the X axis so that there is 30 steps between min and max, it rounds the step to nearest 5 if it's greater than 5 so that the axis numbers are smooth
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0] - xstep),Math.floor(xMaxMin[1] + xstep),xstep);
        NumberAxis yAxis = new NumberAxis();
        // Allows yAxis to create its own step as the y values should be already nicely formatted
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        // Sets the names of axis to be the text fields
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        ScatterChart scatterChart = new ScatterChart<>(xAxis, yAxis);
        // Sets preferred width and height so the ratio is 1:1
        scatterChart.setPrefHeight(3000);
        scatterChart.setPrefWidth(3000);
        scatterChart.getData().addAll(series);
        // Sets the title of the chart to be the text field value
        scatterChart.setTitle(titletextField.getText());
        // Adds tooltips to each data point
        addTooltip(series,false);
        return scatterChart;
    }

}
