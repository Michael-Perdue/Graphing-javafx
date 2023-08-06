package graphing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BarChartGen extends Charts{
    private static BarChartGen barChartGen;
    public static BarChartGen getInstance(){
        if(barChartGen == null)
            barChartGen = new BarChartGen();
        return barChartGen;
    }

    private BarChartGen(){
        super();
    }

    protected ArrayList<XYChart.Series<Number,Number>> generateXYseries() throws FileNotFoundException {
        ArrayList<XYChart.Series<Number,Number>> series = new ArrayList<>();
        try {
            for(File file : filesSelected) {
                if (file.getName().contains(".csv")) {
                    XYChart.Series data = new XYChart.Series();
                    data.setName(file.getName());
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String a = scanner.nextLine();
                        String[] line = a.split(",");
                        data.getData().add(new XYChart.Data<>(line[0],Double.parseDouble(line[1])));
                    }
                    scanner.close();
                    series.add(data);
                }
            }
        }catch (Exception e){System.out.println("Error no files to process");throw e;}
        return series;
    }

    private BarChart<String, Number> loadchart() throws FileNotFoundException {
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        for(XYChart.Series data : series)
            barChart.getData().add(data);
        barChart.setTitle("Bar chart");
        addTooltip(series,true);
        return barChart;
    }

    public Stage barChartConfig(){
        File[] files = new File("src/main/resources/years").listFiles();

        EventHandler<ActionEvent> event = e -> {
            try {
                BarChart<String, Number> barChart = loadchart();
                Scene scene = new Scene(barChart, 1000, 480);
                scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
                Stage barchartStage = new Stage();
                barchartStage.setScene(scene);
                barchartStage.show();
            }catch (Exception ex){ex.printStackTrace();}
        };

        HBox hBox = chartConfig(event,"barchart");

        Scene scene = new Scene(hBox,400, (files.length*50)+100);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
