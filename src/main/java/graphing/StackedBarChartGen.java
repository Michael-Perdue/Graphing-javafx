package graphing;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class StackedBarChartGen extends BarChartGens{
    private static StackedBarChartGen stackedBarChartGen; // The singleton of the class

    /***
     * Returns the singleton instance of the class and creates a new instance of the class if no instance exists
     * @return the singleton instance of the class
     */
    public static StackedBarChartGen getInstance(){
        if(stackedBarChartGen == null)
            stackedBarChartGen = new StackedBarChartGen();
        return stackedBarChartGen;
    }

    /***
     * Constructor
     */
    private StackedBarChartGen(){
        super();
    }

    /***
     * This function calls GenerateXYseries to get the datapoints and,
     * then turn the data into a stacked bar chart.
     * @return a StackedBarChart which was made from the data points of the CSV files
     * @throws IllegalArgumentException Thrown an error if a CSV file contains incorrectly formatted data
     */
    protected StackedBarChart<String, Number> loadchart() throws IllegalArgumentException{
        // Gets the data points from the CSV files
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        // Sets the names of axis to be the text fields
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        StackedBarChart stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.getData().addAll(series);
        // Sets the title of the chart to be the text field value
        stackedBarChart.setTitle(titletextField.getText());
        // Sets preferred width and height so the ratio is 1:1
        stackedBarChart.setPrefWidth(3000);
        stackedBarChart.setPrefHeight(3000);
        // Adds tooltips to each data point and resizes the data points to be smaller
        addTooltip(series,true);
        return stackedBarChart;
    }

}
