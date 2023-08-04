package graphing;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Button button = new Button("Generate linecharts");
        StackPane tilePane = new StackPane();
        EventHandler<ActionEvent> event = e -> {
            try {
                LineChart<Number, Number> lineChart = LineCharts.loadchart();
                Scene scene = new Scene(lineChart, 640, 480);
                Stage linechartStage = new Stage();
                linechartStage.setScene(scene);
                linechartStage.show();
            }catch (Exception ex){ex.printStackTrace();}
        };
        button.setOnAction(event);
        tilePane.getChildren().add(button);
        Scene scene = new Scene(tilePane, 200, 200);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}