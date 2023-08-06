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
    private BarChart barChart;

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
        BarChart barChart = new BarChart<>(xAxis, yAxis);
        barChart.getData().addAll(series);
        barChart.setTitle("Bar chart");
        barChart.setPrefWidth(3000);
        barChart.setPrefHeight(3000);
        addTooltip(series,true);
        return barChart;
    }

    protected void updateChart(){
        try{
            barChart = loadchart();
            vBoxGraph.getChildren().clear();
            vBoxGraph.getChildren().add(barChart);
        }catch (Exception ex){ex.printStackTrace();}
    }

    protected void savePNG(){
        WritableImage image = barChart.snapshot(null,null);
        File imageFile = new File(System.getProperty("user.home") +"/Downloads/" + barChart.getTitle() + ".png");
        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
        }catch (Exception e){e.printStackTrace();}
    }

    public Stage chartConfig(){

        barChart = new BarChart<>(new CategoryAxis(),new NumberAxis());
        hBox = chartConfig("barchart");

        vBoxGraph.getChildren().add(barChart);
        vBoxGraph.prefWidth(1000);
        vBoxGraph.prefHeight(1000);
        vBoxGraph.setAlignment(Pos.CENTER);

        hBox.getChildren().add(vBoxGraph);


        barChart.setPrefHeight(1000);
        barChart.setPrefWidth(1000);

        xtextField.setOnKeyPressed(keyEvent -> barChart.getXAxis().setLabel(xtextField.getText()));
        ytextField.setOnKeyPressed(keyEvent -> barChart.getYAxis().setLabel(ytextField.getText()));

        Scene scene = new Scene(hBox,1200,640);
        scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
