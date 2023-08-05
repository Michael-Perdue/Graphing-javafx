package graphing;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        VBox vBox = new VBox(2);
        vBox.setAlignment(Pos.CENTER_LEFT);

        EventHandler<ActionEvent> lineEvent = e -> {
            try {
                Stage lineChartConfig = LineChartGen.getInstance().LineChartConfig();
                lineChartConfig.show();
            }catch (Exception ex){ex.printStackTrace();}
        };
        EventHandler<ActionEvent> barEvent = e -> {
            try {
                Stage barChartConfig = BarChartGen.getInstance().barChartConfig();
                barChartConfig.show();
            }catch (Exception ex){ex.printStackTrace();}
        };

        Button lineButton = new Button("Generate linecharts");
        lineButton.setOnAction(lineEvent);
        vBox.getChildren().add(lineButton);
        Button barButton = new Button("Generate barcharts");
        barButton.setOnAction(barEvent);
        vBox.getChildren().add(barButton);

        HBox hBox = new HBox();
        hBox.getChildren().add(vBox);
        hBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hBox, 200, 200);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}