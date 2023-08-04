import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        // set the titles for the axes
        xAxis.setLabel("src/main/data/years");
        yAxis.setLabel("values");


        // create the line chart. The values of the chart are given as numbers
        // and it uses the axes we created earlier
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        System.out.println(this.getClass().getClassLoader().getResource("src/main/data/years/data.txt"));
        File file = new File(this.getClass().getClassLoader().getResource("src/main/data/years/data.txt").toString());
        lineChart.setTitle(file.getName());
        Scanner scanner = new Scanner(file);
        XYChart.Series data = new XYChart.Series();
        data.setName(file.getName());
        while (scanner.hasNextLine()){
            String[] line = scanner.nextLine().split("");
            data.getData().add(new XYChart.Data<>(Integer.parseInt(line[0]),Integer.parseInt(line[1])));

        }
        lineChart.getData().add(data);

        // display the line chart
        Scene view = new Scene(lineChart, 640, 480);
        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}