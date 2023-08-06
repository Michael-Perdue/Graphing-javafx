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

    private ScatterChart<Number, Number> loadchart() throws FileNotFoundException {
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0] - xstep),Math.floor(xMaxMin[1] + xstep),xstep);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setPrefHeight(3000);
        scatterChart.setPrefWidth(3000);
        for(XYChart.Series data : series)
            scatterChart.getData().add(data);
        scatterChart.setTitle("Scatter chart");
        addTooltip(series,false);
        return scatterChart;
    }

    public Stage scatterChartConfig(){

        EventHandler<ActionEvent> event = e -> {
            try {
                ScatterChart<Number, Number> scatterChart = loadchart();
                vBoxGraph.getChildren().clear();
                vBoxGraph.getChildren().add(scatterChart);
            }catch (Exception ex){ex.printStackTrace();}
        };


        ScatterChart scatterChart = new ScatterChart<>(new NumberAxis(),new NumberAxis());
        hBox = chartConfig(event,"Scatterchart");
        vBoxGraph.getChildren().add(scatterChart);
        vBoxGraph.prefWidth(1000);
        vBoxGraph.prefHeight(1000);
        hBox.getChildren().add(vBoxGraph);
        vBoxGraph.setAlignment(Pos.CENTER);
        scatterChart.setPrefHeight(1000);
        scatterChart.setPrefWidth(1000);
        Scene scene = new Scene(hBox,1200,640);
        scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
