package graphing;

import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class BarChartGens extends Charts{

    /***
     * The function gets the data from the selected CSV files and translates it into XYChart.Series,
     * so that it can be added to the charts.
     * @return An ArrayList of XYChart.Series populated from the CSV files
     * @throws IllegalArgumentException Thrown an error if a CSV file contains incorrectly formatted data
     */
    protected ArrayList<XYChart.Series<Number,Number>> generateXYseries() throws IllegalArgumentException{
        ArrayList<XYChart.Series<Number,Number>> series = new ArrayList<>();
        // Loops through each file the user has selected
        for(File file : filesSelected) {
            try {
                // Only processes CSV files
                if (file.getName().contains(".csv")) {
                    XYChart.Series data = new XYChart.Series();
                    data.setName(file.getName());   // Sets the Legend of the data to be the file name
                    Scanner scanner = new Scanner(file);
                    // Loops through the data in the CSV file adding it to the series
                    while (scanner.hasNextLine()) {
                        String a = scanner.nextLine();
                        String[] line = a.split(",");
                        data.getData().add(new XYChart.Data<>(line[0], Double.parseDouble(line[1])));
                    }
                    scanner.close();
                    series.add(data);
                }
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
        return series;
    }
}
