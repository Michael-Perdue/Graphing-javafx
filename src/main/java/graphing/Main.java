package graphing;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Tab lineTab = new Tab("Generate Line Chart",LineChartGen.getInstance().chartConfig(stage));
        lineTab.setClosable(false);
        Tab barTab = new Tab("Generate Bar Chart",BarChartGen.getInstance().chartConfig(stage));
        barTab.setClosable(false);
        Tab scatterTab = new Tab("Generate Scatter Chart",ScatterChartGen.getInstance().chartConfig(stage));
        scatterTab.setClosable(false);
        TabPane tabPane = new TabPane(lineTab,barTab,scatterTab);
        Scene scene = new Scene(tabPane, 1200, 640);
        scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}