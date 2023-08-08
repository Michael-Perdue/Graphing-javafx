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

    protected ArrayList<XYChart.Series<Number,Number>> generateXYseries(){
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
        }catch (Exception e){System.out.println("Error no files to process");}
        return series;
    }

    protected BarChart<String, Number> loadchart(){
        ArrayList<XYChart.Series<Number,Number>> series = generateXYseries();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        BarChart barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().addAll(series);
        barChart.setTitle(titletextField.getText());
        barChart.setPrefWidth(3000);
        barChart.setPrefHeight(3000);
        addTooltip(series,true);
        return barChart;
    }

    protected void updateChart(){
        try{
            chart = loadchart();
            vBoxGraph.getChildren().clear();
            vBoxGraph.getChildren().add(chart);
        }catch (Exception ex){ex.printStackTrace();}
    }

}
