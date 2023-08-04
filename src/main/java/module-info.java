module graphing.graphing_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    exports graphing;
    opens graphing to javafx.fxml;
}