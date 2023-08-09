package graphing;

import javafx.scene.chart.*;
import java.util.ArrayList;

public class BarChartGen extends BarChartGens{
    private static BarChartGen barChartGen; // The singleton of the class

    /***
     * Returns the singleton instance of the class and creates a new instance of the class if no instance exists.
     * @return the singleton instance of the class.
     */
    public static BarChartGen getInstance(){
        if(barChartGen == null)
            barChartGen = new BarChartGen();
        return barChartGen;
    }

    /***
     * Constructor
     */
    protected BarChartGen(){
        super();
    }

    /***
     * This function calls GenerateXYseries to get the datapoints and then turn the data into a Stacked bar chart.
     * @return a StackedBarChart which was made from the data points of the CSV files.
     * @throws IllegalArgumentException Thrown an error if a CSV file contains incorrectly formatted data.
     */
    protected BarChart<String, Number> loadchart() throws IllegalArgumentException{
        // Gets the data points from the CSV files
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        // Sets the names of axis to be the text fields
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        BarChart barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().addAll(series);
        // Sets the title of the chart to be the text field value
        barChart.setTitle(titletextField.getText());
        // Sets preferred width and height so the ratio is 1:1
        barChart.setPrefWidth(3000);
        barChart.setPrefHeight(3000);
        // Adds tooltips to each data point and resizes the data points to be smaller
        addTooltip(series,true);
        return barChart;
    }

}
