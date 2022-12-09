module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;

    opens org.example to javafx.fxml;
    exports org.example;
}
