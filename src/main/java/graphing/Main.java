package graphing;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Button button = new Button("Generate linecharts");
        StackPane stackPane = new StackPane();
        EventHandler<ActionEvent> event = e -> {
            try {
                Stage lineChartConfig = LineChartGen.getInstance().LineChartConfig();
                lineChartConfig.show();
            }catch (Exception ex){ex.printStackTrace();}
        };
        button.setOnAction(event);
        stackPane.getChildren().add(button);
        Scene scene = new Scene(stackPane, 200, 200);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}