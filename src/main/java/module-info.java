module com.example.jbilliard {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    opens com.example.jbilliard to javafx.fxml;
    opens com.example.jbilliard.controller to javafx.fxml;
    opens com.example.jbilliard.view to javafx.fxml;
    opens com.example.jbilliard.model to javafx.fxml;
    exports com.example.jbilliard;
}