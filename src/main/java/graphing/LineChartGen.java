package graphing;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

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
        lineChart.setTitle(titletextField.getText());
        addTooltip(series,true);
        return lineChart;
    }

    protected void updateChart(){
        try{
        chart = loadchart();
        chart.getXAxis().setAnimated(false);
        chart.getYAxis().setAnimated(false);
        vBoxGraph.getChildren().clear();
        vBoxGraph.getChildren().add(chart);
        }catch (Exception ex){ex.printStackTrace();}
    }

    protected void savePNG(){
        WritableImage image = chart.snapshot(null,null);
        File imageFile = new File(System.getProperty("user.home") +"/Downloads/" + chart.getTitle() + ".png");
        try{
           ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
        }catch (Exception e){e.printStackTrace();}
    }

}
