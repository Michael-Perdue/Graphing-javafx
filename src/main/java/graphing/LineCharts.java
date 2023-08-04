package graphing;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LineCharts {
    private static int[] xMaxMin = new int[2];
    private static int[] yMaxMin = new int[2];
    private static boolean unpopulated = true;

    private static ArrayList<XYChart.Series> generateXYseries() throws FileNotFoundException {
        ArrayList<XYChart.Series> series = new ArrayList<>();
        try {
            File[] files = new File("src/main/resources/years").listFiles();
            for(File file : files){
                XYChart.Series data = new XYChart.Series();
                data.setName(file.getName());
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(" ");
                    int[] numbers = {Integer.parseInt(line[0]), Integer.parseInt(line[1])};
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
                    data.getData().add(new XYChart.Data<>(Integer.parseInt(line[0]), Integer.parseInt(line[1])));

                }
                scanner.close();
                series.add(data);
            }
        }catch (Exception e){System.out.println("Error no files to process");throw e;}
        return series;
    }
    public static LineChart<Number, Number> loadchart() throws FileNotFoundException {
        File file = new File("src/main/resources/years/data.txt");
        System.out.println(file.exists());
        ArrayList<XYChart.Series> series = generateXYseries();
        NumberAxis xAxis = new NumberAxis(xMaxMin[0],xMaxMin[1],Math.round((double) (xMaxMin[1] - xMaxMin[0]) /30));
        NumberAxis yAxis = new NumberAxis(yMaxMin[0],yMaxMin[1],Math.round((double) (yMaxMin[1] - yMaxMin[0]) /10));
        xAxis.setLabel("x");
        yAxis.setLabel("y");
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for(XYChart.Series data : series)
            lineChart.getData().add(data);
        lineChart.setTitle("Line chart");
        return lineChart;
    }
}
