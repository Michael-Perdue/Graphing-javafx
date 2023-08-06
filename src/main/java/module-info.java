module graphing.graphing_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;
    exports graphing;
    opens graphing to javafx.fxml;
}