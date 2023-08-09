package graphing;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * This function is called upon the start of the program and generates a tab for each type of chart,
     * it then adds these tabs to the scene and stage so that it is visable to the user.
     * @param stage the window the content is getting added to.
     */
    @Override
    public void start(Stage stage){
        //Creates tabs for each graph by setting the tab to be their configuration page(Hbox)
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
        Scene scene = new Scene(tabPane, 1200, 680);
        //Adds the stylesheet to allow for the nodes to turn black when hovered over
        scene.getStylesheets().add(Main.class.getResource("/Main.css").toExternalForm());
        stage.setTitle("Chart Generator");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches JavaFX
     */
    public static void main(String[] args) {
        launch();
    }
}