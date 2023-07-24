module module11.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens module11.server to javafx.fxml;
    exports module11.server;
}