package graphing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        barChart.setPrefWidth(3000);
        barChart.setPrefHeight(3000);
        addTooltip(series,true);
        return barChart;
    }

    public Stage barChartConfig(){

        EventHandler<ActionEvent> event = e -> {
            try {
                BarChart<String, Number> barChart = loadchart();
                vBoxGraph.getChildren().clear();
                vBoxGraph.getChildren().add(barChart);
            }catch (Exception ex){ex.printStackTrace();}
        };

        BarChart barChart = new BarChart<>(new CategoryAxis(),new NumberAxis());
        hBox = chartConfig(event,"barchart");
        vBoxGraph.getChildren().add(barChart);
        vBoxGraph.prefWidth(1000);
        vBoxGraph.prefHeight(1000);
        hBox.getChildren().add(vBoxGraph);
        vBoxGraph.setAlignment(Pos.CENTER);
        barChart.setPrefHeight(1000);
        barChart.setPrefWidth(1000);
        Scene scene = new Scene(hBox,1200,640);
        scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
