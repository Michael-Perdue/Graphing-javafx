package graphing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        scatterChart.autosize();
        for(XYChart.Series data : series)
            scatterChart.getData().add(data);
        scatterChart.setTitle("Scatter chart");
        addTooltip(series,false);
        return scatterChart;
    }

    public Stage scatterChartConfig(){
        File[] files = new File("src/main/resources/years").listFiles();

        EventHandler<ActionEvent> event = e -> {
            try {
                ScatterChart<Number, Number> scatterChart = loadchart();
                Scene scene = new Scene(scatterChart, 640, 480);
                scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
                Stage scatterchartStage = new Stage();
                scatterchartStage.setScene(scene);
                scatterchartStage.show();
            }catch (Exception ex){ex.printStackTrace();}
        };

        HBox hBox = chartConfig(event,"barchart");

        Scene scene = new Scene(hBox,400, (files.length*50)+100);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
