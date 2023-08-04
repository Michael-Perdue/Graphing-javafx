package graphing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        File file = new File("src/main/resources/years/data.txt");
        System.out.println(file.exists());
        int[] xMaxMin = new int[2];
        int[] yMaxMin = new int[2];
        Scanner scanner = new Scanner(file);
        XYChart.Series data = new XYChart.Series();
        data.setName(file.getName());
        while (scanner.hasNextLine()){
            String[] line = scanner.nextLine().split(" ");
            int[] numbers = {Integer.parseInt(line[0]),Integer.parseInt(line[1])};
            xMaxMin[0] = Math.min(numbers[0], xMaxMin[0]);
            xMaxMin[1] = Math.max(numbers[0], xMaxMin[1]);
            yMaxMin[0] = Math.min(numbers[1], yMaxMin[0]);
            yMaxMin[1] = Math.max(numbers[1], yMaxMin[1]);
            System.out.println(line[0] + line[1]);
            data.getData().add(new XYChart.Data<>(Integer.parseInt(line[0]),Integer.parseInt(line[1])));

        }
        NumberAxis xAxis = new NumberAxis(xMaxMin[0],xMaxMin[1],1);
        NumberAxis yAxis = new NumberAxis(yMaxMin[0],yMaxMin[1],1);
        xAxis.setLabel("src/main/data/years");
        yAxis.setLabel("values");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.getData().add(data);
        lineChart.setTitle(file.getName());

        // display the line chart
        Scene scene = new Scene(lineChart, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}