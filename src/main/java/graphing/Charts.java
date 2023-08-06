package graphing;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Charts {
    protected double[] xMaxMin = new double[2];
    private boolean unpopulated = true;

    protected TextField xtextField;
    protected TextField ytextField;
    protected ArrayList<File> filesSelected = new ArrayList<File>();

    protected void addTooltip(ArrayList<XYChart.Series<Number,Number>> series,boolean resize){
        for(XYChart.Series<Number,Number> dataSeries: series) {
            for (XYChart.Data<Number,Number> data :dataSeries.getData()) {
                Tooltip tooltip = new Tooltip(data.getYValue().toString());
                Tooltip.install(data.getNode(), tooltip);
                tooltip.setShowDelay(Duration.millis(100));
                data.getNode().setOnMouseEntered(eventH -> data.getNode().getStyleClass().add("onHover"));
                data.getNode().setOnMouseExited(eventH -> data.getNode().getStyleClass().remove("onHover"));
                if(resize) {
                    StackPane stackPane = (StackPane) data.getNode();
                    stackPane.setPrefWidth(7);
                    stackPane.setPrefHeight(7);
                }
            }
        }
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
                        double[] numbers = {Double.parseDouble(line[0]), Double.parseDouble(line[1])};
                        if (unpopulated) {
                            Arrays.fill(xMaxMin, numbers[0]);
                            unpopulated = false;
                        }
                        xMaxMin[0] = Math.min(numbers[0], xMaxMin[0]);
                        xMaxMin[1] = Math.max(numbers[0], xMaxMin[1]);
                        data.getData().add(new XYChart.Data<>(numbers[0],numbers[1]));

                    }
                    scanner.close();
                    series.add(data);
                }
            }
        }catch (Exception e){System.out.println("Error no files to process");throw e;}
        return series;
    }

    protected HBox chartConfig(EventHandler<ActionEvent> event,String name){
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
            EventHandler<ActionEvent> event1 = e -> {
                try {
                    if(checkBox.isSelected())
                        filesSelected.add(file);
                    else
                        filesSelected.remove(file);
                }catch (Exception ex){ex.printStackTrace();}
            };
            checkBox.setOnAction(event1);
        }

        TilePane xaxisPane = new TilePane();
        TilePane yaxisPane = new TilePane();
        Label xaxisLabel = new Label("X axis name");
        Label yaxisLabel = new Label("Y axis name");
        xtextField = new TextField("x");
        ytextField = new TextField("y");
        xaxisPane.getChildren().add(xtextField);
        xaxisPane.getChildren().add(xaxisLabel);
        yaxisPane.getChildren().add(ytextField);
        yaxisPane.getChildren().add(yaxisLabel);
        vBox.getChildren().add(xaxisPane);
        vBox.getChildren().add(yaxisPane);

        Button button = new Button("Generate " + name);
        button.setOnAction(event);
        vBox.getChildren().add(button);

        HBox hBox = new HBox(vBox);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }
}
