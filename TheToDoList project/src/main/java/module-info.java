module thetodolist {
    requires javafx.fxml;
    requires javafx.controls;
//    requires java.naming;
    requires com.jfoenix;
    requires mysql.connector.java;
    requires java.sql;

    opens sample;
    opens sample.controller to javafx.fxml;

//    added the following VM arguments:
//            --add-exports javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
//            --add-exports javafx.controls/com.sun.javafx.scene.control=com.jfoenix
//            --add-exports javafx.base/com.sun.javafx.binding=com.jfoenix
//            --add-exports javafx.graphics/com.sun.javafx.stage=com.jfoenix
//            --add-exports javafx.base/com.sun.javafx.event=com.jfoenix

    exports sample;
}