package graphing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
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

    private LineChart<Number, Number> loadchart() throws FileNotFoundException{
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0] - xstep),Math.floor(xMaxMin[1] + xstep),xstep);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        lineChart.setPrefHeight(3000);
        lineChart.setPrefWidth(3000);
        lineChart.getData().addAll(series);
        lineChart.setTitle("Line chart");
        addTooltip(series,true);
        return lineChart;
    }

    public Stage LineChartConfig(){

        EventHandler<ActionEvent> event = e -> {
            try {
                LineChart<Number, Number> lineChart = loadchart();
                vBoxGraph.getChildren().clear();
                vBoxGraph.getChildren().add(lineChart);
            }catch (Exception ex){ex.printStackTrace();}
        };
        LineChart lineChart = new LineChart<>(new NumberAxis(),new NumberAxis());
        hBox = chartConfig(event,"linechart");
        vBoxGraph.getChildren().add(lineChart);
        vBoxGraph.prefWidth(1000);
        vBoxGraph.prefHeight(1000);
        hBox.getChildren().add(vBoxGraph);
        vBoxGraph.setAlignment(Pos.CENTER);
        lineChart.setPrefHeight(1000);
        lineChart.setPrefWidth(1000);
        Scene scene = new Scene(hBox,1200,640);
        scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
