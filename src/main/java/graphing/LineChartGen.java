package graphing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        ArrayList<XYChart.Series> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        double ystep = Math.ceil((yMaxMin[1] - yMaxMin[0]) /10) > 5 ? Math.ceil(((((yMaxMin[1] - yMaxMin[0]) /10) + 5) /10)*10) : Math.ceil((yMaxMin[1] - yMaxMin[0]) /10);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0] - xstep),Math.floor(xMaxMin[1] + xstep),xstep);
        NumberAxis yAxis = new NumberAxis(Math.ceil(yMaxMin[0]- ystep),Math.floor(yMaxMin[1] + ystep),ystep);
        xAxis.setLabel(xtextField.getText());
        yAxis.setLabel(ytextField.getText());
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for(XYChart.Series data : series)
            lineChart.getData().add(data);
        lineChart.setTitle("Line chart");
        return lineChart;
    }

    public Stage LineChartConfig(){
        File[] files = new File("src/main/resources/years").listFiles();

        EventHandler<ActionEvent> event = e -> {
            try {
                LineChart<Number, Number> lineChart = loadchart();
                Scene scene = new Scene(lineChart, 640, 480);
                Stage linechartStage = new Stage();
                linechartStage.setScene(scene);
                linechartStage.show();
            }catch (Exception ex){ex.printStackTrace();}
        };

        HBox hBox = chartConfig(event,"linechart");
        Scene scene = new Scene(hBox,400, (files.length*50)+100);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
