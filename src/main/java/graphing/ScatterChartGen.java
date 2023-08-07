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
        scatterChart.setTitle(titletextField.getText());
        addTooltip(series,false);
        return scatterChart;
    }

    protected void updateChart(){
        try {
            chart = loadchart();
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
