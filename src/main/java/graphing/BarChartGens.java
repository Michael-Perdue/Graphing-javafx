package graphing;

import javafx.scene.chart.XYChart;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class BarChartGens extends Charts{
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
}
