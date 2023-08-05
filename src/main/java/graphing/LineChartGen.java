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

    public LineChart<Number, Number> loadchart(String xName, String yName) throws FileNotFoundException{
        ArrayList<XYChart.Series> series = generateXYseries();
        double xstep = Math.ceil((xMaxMin[1] - xMaxMin[0]) /30) > 5 ? Math.ceil(((((xMaxMin[1] - xMaxMin[0]) / 30) + 5) /10)*10) : Math.ceil((xMaxMin[1] - xMaxMin[0]) /30);
        double ystep = Math.ceil((yMaxMin[1] - yMaxMin[0]) /10) > 5 ? Math.ceil(((((yMaxMin[1] - yMaxMin[0]) /10) + 5) /10)*10) : Math.ceil((yMaxMin[1] - yMaxMin[0]) /10);
        NumberAxis xAxis = new NumberAxis(Math.ceil(xMaxMin[0] - xstep),Math.floor(xMaxMin[1] + xstep),xstep);
        NumberAxis yAxis = new NumberAxis(Math.ceil(yMaxMin[0]- ystep),Math.floor(yMaxMin[1] + ystep),ystep);
        xAxis.setLabel(xName);
        yAxis.setLabel(yName);
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        for(XYChart.Series data : series)
            lineChart.getData().add(data);
        lineChart.setTitle("Line chart");
        return lineChart;
    }

    public Stage LineChartConfig(){
        File[] files = new File("src/main/resources/years").listFiles();

        VBox vBox = new VBox(files.length +3);
        vBox.setPadding(new Insets(5,5,5,5));
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setSpacing(10);

        Label label = new Label("Select which files you want to use");
        vBox.getChildren().add(label);

        for(File file : files) {
            CheckBox checkBox = new CheckBox(file.getName());
            vBox.getChildren().add(checkBox);
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

        TilePane xaxisPane = new TilePane();
        TilePane yaxisPane = new TilePane();
        Label xaxisLabel = new Label("X axis name");
        Label yaxisLabel = new Label("Y axis name");
        TextField xtextField = new TextField("x");
        TextField ytextField = new TextField("y");
        xaxisPane.getChildren().add(xtextField);
        xaxisPane.getChildren().add(xaxisLabel);
        yaxisPane.getChildren().add(ytextField);
        yaxisPane.getChildren().add(yaxisLabel);
        vBox.getChildren().add(xaxisPane);
        vBox.getChildren().add(yaxisPane);

        Button button = new Button("Generate linechart");
        EventHandler<ActionEvent> event = e -> {
            try {
                LineChart<Number, Number> lineChart = loadchart(xtextField.getText(),ytextField.getText());
                Scene scene = new Scene(lineChart, 640, 480);
                Stage linechartStage = new Stage();
                linechartStage.setScene(scene);
                linechartStage.show();
            }catch (Exception ex){ex.printStackTrace();}
        };
        button.setOnAction(event);
        vBox.getChildren().add(button);

        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(hBox,400, (files.length*50)+100);
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
}
