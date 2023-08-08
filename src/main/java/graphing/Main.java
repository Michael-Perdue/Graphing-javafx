package graphing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        Tab lineTab = new Tab("Generate Line Chart",LineChartGen.getInstance().chartConfig());
        lineTab.setClosable(false);
        Tab barTab = new Tab("Generate Bar Chart",BarChartGen.getInstance().chartConfig());
        barTab.setClosable(false);
        Tab scatterTab = new Tab("Generate Scatter Chart",ScatterChartGen.getInstance().chartConfig());
        scatterTab.setClosable(false);
        Tab areaTab = new Tab("Generate Area Chart",AreaChartGen.getInstance().chartConfig());
        areaTab.setClosable(false);
        Tab stackedAreaTab = new Tab("Generate Stacked Area Chart",StackedAreaChartGen.getInstance().chartConfig());
        stackedAreaTab.setClosable(false);
        Tab stackedBarTab = new Tab("Generate Stacked Bar Chart",StackedBarChartGen.getInstance().chartConfig());
        stackedBarTab.setClosable(false);
        TabPane tabPane = new TabPane(lineTab,barTab,stackedBarTab,scatterTab,areaTab,stackedAreaTab);
        Scene scene = new Scene(tabPane, 1200, 640);
        scene.getStylesheets().add(Main.class.getResource("/Charts.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}