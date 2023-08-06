package graphing;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ScatterChartGen extends Charts{

    private static ScatterChartGen scatterChartGen;
    private ScatterChart scatterChart;

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
        ScatterChart scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.setPrefHeight(3000);
        scatterChart.setPrefWidth(3000);
        scatterChart.getData().addAll(series);
        scatterChart.setTitle("Scatter chart");
        addTooltip(series,false);
        return scatterChart;
    }

    protected void updateChart(){
        try {
            scatterChart = loadchart();
            vBoxGraph.getChildren().clear();
            vBoxGraph.getChildren().add(scatterChart);
        }catch (Exception ex){ex.printStackTrace();}
    }

    protected void savePNG(){
        WritableImage image = scatterChart.snapshot(null,null);
        File imageFile = new File(System.getProperty("user.home") +"/Downloads/" + scatterChart.getTitle() + ".png");
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
        }catch (Exception e){e.printStackTrace();}
    }

    public Stage chartConfig(){

        scatterChart = new ScatterChart<>(new NumberAxis(),new NumberAxis());
        hBox = chartConfig("Scatterchart");

        vBoxGraph.getChildren().add(scatterChart);
        vBoxGraph.prefWidth(1000);
        vBoxGraph.prefHeight(1000);
        vBoxGraph.setAlignment(Pos.CENTER);

        hBox.getChildren().add(vBoxGraph);

        scatterChart.setPrefHeight(1000);
        scatterChart.setPrefWidth(1000);

        xtextField.setOnKeyPressed(keyEvent -> scatterChart.getXAxis().setLabel(xtextField.getText()));
        ytextField.setOnKeyPressed(keyEvent -> scatterChart.getYAxis().setLabel(ytextField.getText()));

        Scene scene = new Scene(hBox,1200,640);
        scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
