package graphing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LineCharts {
    private static double[] xMaxMin = new double[2];
    private static double[] yMaxMin = new double[2];
    private static boolean unpopulated = true;

    private static ArrayList<File> filesSelected = new ArrayList<File>();

    private static ArrayList<XYChart.Series> generateXYseries() throws FileNotFoundException{
        ArrayList<XYChart.Series> series = new ArrayList<>();
        try {
            for(File file : filesSelected) {
                if (file.getName().contains(".csv")) {
                    XYChart.Series data = new XYChart.Series();
                    data.setName(file.getName());
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String a = scanner.nextLine();
                        String[] line = a.split(",");
                        double[] numbers = {Double.parseDouble(line[0]), Double.parseDouble(line[1])};
                        if (unpopulated) {
                            Arrays.fill(xMaxMin, numbers[0]);
                            Arrays.fill(yMaxMin, numbers[1]);
                            unpopulated = false;
                        }
                        xMaxMin[0] = Math.min(numbers[0], xMaxMin[0]);
                        xMaxMin[1] = Math.max(numbers[0], xMaxMin[1]);
                        yMaxMin[0] = Math.min(numbers[1], yMaxMin[0]);
                        yMaxMin[1] = Math.max(numbers[1], yMaxMin[1]);
                        System.out.println(line[0] + line[1]);
                        data.getData().add(new XYChart.Data<>(numbers[0],numbers[1]));

                    }
                    scanner.close();
                    series.add(data);
                }
            }
        }catch (Exception e){System.out.println("Error no files to process");throw e;}
        return series;
    }
    public static LineChart<Number, Number> loadchart() throws FileNotFoundException{
        ArrayList<XYChart.Series> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        double ystep = Math.ceil((yMaxMin[1] - yMaxMin[0]) /10) > 5 ? Math.ceil(((((yMaxMin[1] - yMaxMin[0]) /10) + 5) /10)*10) : Math.ceil((yMaxMin[1] - yMaxMin[0]) /10);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0] - xstep),Math.floor(xMaxMin[1] + xstep),xstep);
        NumberAxis yAxis = new NumberAxis(Math.ceil(yMaxMin[0]- ystep),Math.floor(yMaxMin[1] + ystep),ystep);
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for(XYChart.Series data : series)
            lineChart.getData().add(data);
        lineChart.setTitle("Line chart");
        return lineChart;
    }

    public static Stage LineChartConfig(){
        TilePane tilePane = new TilePane();
        File[] files = new File("src/main/resources/years").listFiles();

        for(File file : files) {
            CheckBox checkBox = new CheckBox(file.getName());
            tilePane.getChildren().add(checkBox);
            checkBox.setIndeterminate(true);
            EventHandler<ActionEvent> event = e -> {
                try {
                    if(checkBox.isSelected())
                        filesSelected.add(file);
                    else
                        filesSelected.remove(file);
                }catch (Exception ex){ex.printStackTrace();}
            };
            checkBox.setOnAction(event);
        }
        Button button = new Button("Generate linechart");
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
        Scene scene = new Scene(tilePane,640, 480);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
