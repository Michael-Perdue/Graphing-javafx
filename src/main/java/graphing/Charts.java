package graphing;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public abstract class Charts {
    protected double[] xMaxMin = new double[2]; // The min and max of the files selected
    protected VBox vBoxGraph = new VBox(1); // The node the graph is contained in used to change graph displayed
    protected TextField xtextField; // The text field the user enters to set the x axis
    protected TextField ytextField; // The text field the user enters to set the y axis
    protected TextField titletextField; // The text field the user enters to set the title
    protected ArrayList<File> filesSelected = new ArrayList<File>(); // The files the user has selected to use
    protected XYChart chart; // The chart being displayed

    /**
     * This function adds a tooltip to each node of data which upon the user hovering for 0.1 seconds,
     * it tells them the value of the data point. It also optionally decreases the data points physical size.
     * @param series An array of series of data that you want the tool tip added to.
     * @param resize a flag stating whether you want the data points physical size reduced.
     */
    protected void addTooltip(ArrayList<XYChart.Series<Number,Number>> series,boolean resize){
        // Loops through each series data points
        for(XYChart.Series<Number,Number> dataSeries: series) {
            for (XYChart.Data<Number,Number> data :dataSeries.getData()) {
                // Creates a tooltip with the value nicely formatted and the y label added to the end of it
                Tooltip tooltip = new Tooltip(new DecimalFormat("#,###.###").format(data.getYValue()) + " " + ytextField.getText());
                Tooltip.install(data.getNode(), tooltip);
                tooltip.setShowDelay(Duration.millis(100)); // Reduces time it takes to show tooltip from 1 second to 0.1
                // Makes the data point black when hovered over for clarity
                data.getNode().setOnMouseEntered(eventH -> data.getNode().getStyleClass().add("onHover"));
                // Makes the data point go back to normal when the mouse is no longer hovering over
                data.getNode().setOnMouseExited(eventH -> data.getNode().getStyleClass().remove("onHover"));
                // To reduce node size, it converts the node to a stackPane and sets its height and width
                if(resize) {
                    StackPane stackPane = (StackPane) data.getNode();
                    stackPane.setPrefWidth(7);
                    stackPane.setPrefHeight(7);
                }
            }
        }
    }

    /***
     * The function gets the data from the selected CSV files and translates it into XYChart.Series,
     * so that it can be added to the charts.
     * @return An ArrayList of XYChart.Series populated from the CSV files
     * @throws IllegalArgumentException Thrown an error if a CSV file contains incorrectly formatted data
     */
    protected ArrayList<XYChart.Series<Number,Number>> generateXYseries() throws IllegalArgumentException {
        Boolean unpopulated = true; // States if the xMaxMin has been populated with the first value yet
        ArrayList<XYChart.Series<Number,Number>> series = new ArrayList<>();
        // Loops through each file the user has selected
        for(File file : filesSelected) {
            // Only processes CSV files
            if (file.getName().contains(".csv")) {
                try {
                    XYChart.Series data = new XYChart.Series();
                    data.setName(file.getName());   // Sets the Legend of the data to be the file name
                    Scanner scanner = new Scanner(file);
                    // Loops through the data in the CSV file adding it to the series
                    while (scanner.hasNextLine()) {
                        String a = scanner.nextLine();
                        String[] line = a.split(",");
                        double[] numbers = {Double.parseDouble(line[0]), Double.parseDouble(line[1])};
                        // If the line of data being processed is the first line from all files then it sets the Min and Max to be the x value of the line
                        if (unpopulated) {
                            Arrays.fill(xMaxMin, numbers[0]);
                            unpopulated = false;
                        }
                        xMaxMin[0] = Math.min(numbers[0], xMaxMin[0]);  // Checks if a new Min has been found
                        xMaxMin[1] = Math.max(numbers[0], xMaxMin[1]);  // Checks if a new Max has been found
                        data.getData().add(new XYChart.Data<>(numbers[0], numbers[1])); // Adds the data as a datapoint to the series

                    }
                    scanner.close();
                    series.add(data);
                }catch (Exception e){
                    // Given an error creates a popup window stating why and that an error has happened
                    VBox vbox = new VBox(1);
                    Label label = new Label("An error has occurred whilst processing the CSV file due to incorrect formatting in the csv file");
                    vbox.getChildren().add(label);
                    Scene scene = new Scene(vbox,500, 30);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("ERROR");
                    stage.show();
                    // Removes the erroneous file from the files selected
                    filesSelected.remove(file);
                    // Throws the error so that checkbox for the file can be unchecked
                    throw new IllegalArgumentException("CSV file formatted incorrectly");
                }
            }
        }
        return series;
    }

    /***
     * The function updates the chart by swapping out the current chart with the new chart gotten from loadchart funtion.
     */
    protected void updateChart(){
        chart = loadchart();
        vBoxGraph.getChildren().clear();    // Removes old chart
        vBoxGraph.getChildren().add(chart);
    }

    /***
     * THis function should call GenerateXYseries to get the datapoints and,
     * then turn them into the chart the sub-class generates.
     * @return an XYChart which was made from the data points
     * @throws IllegalArgumentException Thrown an error if a CSV file contains incorrectly formatted data
     */
    protected abstract XYChart loadchart() throws IllegalArgumentException;

    /***
     * This function takes a snapshot of the chart and writes it to a png in the downloads folder with the name being the title of the graph
     */
    protected void savePNG(){
        // Gets a snapshot of the chart
        WritableImage image = chart.snapshot(null,null);
        // Creates an empty file for the image in downloads
        File imageFile = new File(System.getProperty("user.home") +"/Downloads/" + chart.getTitle() + ".png");
        try{
            // Writes the image into the empty image file in downloads
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
        }catch (Exception e){e.printStackTrace();}
    }

    /***
     * This function generates the HBox which contains 2 sections,
     * the configuration of the graph and the graph itself
     * @return A HBox with a graph and the config settings of the graph
     */
    protected HBox chartConfig() {
        // Gets all the files in the data folder
        File[] files = new File("src/main/resources/data").listFiles();
        // Creates a VBox to hold all the configuration settings
        VBox vBox = new VBox(files.length + 5);
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);
        vBox.setPrefWidth(200);
        Label label = new Label("Select files to load");
        vBox.getChildren().add(label);
        // Creates a checkbox for each file which on checking and unchecking updates the graph
        for (File file : files) {
            CheckBox checkBox = new CheckBox(file.getName());
            vBox.getChildren().add(checkBox);
            checkBox.setOnAction(e -> {
                // Upon checking the box the chart is updated to contain the files data
                if (checkBox.isSelected()) {
                    filesSelected.add(file);
                    try {
                        updateChart();
                    }catch (Exception ex){checkBox.setSelected(false);} // If an error happens while updating the box is unticked
                } else { // Upon unchecking the box the chart is updated to no longer contain the files data
                    filesSelected.remove(file);
                    try {
                        updateChart();
                    }catch (Exception ex){checkBox.setSelected(true);} // If an error happens while updating the box is reticked
                }
            });
        }
        // Creates 3 tile panes which each contains a label and text field for setting the x/y axis and the title of the chart
        TilePane xaxisPane = new TilePane();
        TilePane yaxisPane = new TilePane();
        TilePane titlePane = new TilePane();
        Label xaxisLabel = new Label("X axis name");
        Label yaxisLabel = new Label("Y axis name");
        Label titleLabel = new Label("Title name");
        xtextField = new TextField("x");
        ytextField = new TextField("y");
        titletextField = new TextField("Title");
        xaxisPane.getChildren().add(xtextField);
        xaxisPane.getChildren().add(xaxisLabel);
        yaxisPane.getChildren().add(ytextField);
        yaxisPane.getChildren().add(yaxisLabel);
        titlePane.getChildren().add(titletextField);
        titlePane.getChildren().add(titleLabel);
        // Adds the 3 tile panes to the config vBox
        vBox.getChildren().add(xaxisPane);
        vBox.getChildren().add(yaxisPane);
        vBox.getChildren().add(titlePane);
        // Creates and adds to the config vBox a button that upon clicking saves the chart to a png
        Button button = new Button("Save graph as PNG");
        button.setOnAction(actionEvent -> savePNG());
        vBox.getChildren().add(button);
        // Creates the HBox that will contain both the chart and config vBox
        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.TOP_LEFT);
        // Creates a blank line chart to display when no files have been selected
        chart = new LineChart<>(new NumberAxis(), new NumberAxis());
        // Creates the vBox for the graph and adds it to the Hbox
        vBoxGraph.getChildren().add(chart);
        vBoxGraph.prefWidth(1000);
        vBoxGraph.prefHeight(1000);
        vBoxGraph.setAlignment(Pos.CENTER);
        hBox.getChildren().add(vBoxGraph);
        // Sets the charts desired size and labels
        chart.setPrefHeight(1000);
        chart.setPrefWidth(1000);
        chart.getXAxis().setLabel(xtextField.getText());
        chart.getYAxis().setLabel(ytextField.getText());
        chart.setTitle(titletextField.getText());
        // Sets the text fields to update the graph upon a key being released in its text field box
        titletextField.setOnKeyReleased(keyEvent -> chart.setTitle(titletextField.getText()));
        xtextField.setOnKeyReleased(keyEvent -> chart.getXAxis().setLabel(xtextField.getText() + " "));
        ytextField.setOnKeyReleased(keyEvent -> chart.getYAxis().setLabel(ytextField.getText() + " "));
        // Returns the Hbox which contains the config settings and chart.
        return hBox;
    }

}
