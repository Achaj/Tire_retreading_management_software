module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.persistence;
    requires java.sql;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    requires org.hibernate.orm.core;
    requires mysql.connector.java;
    requires java.naming;
    requires net.bytebuddy;
    requires kernel;
    requires layout;

    opens org.example to javafx.fxml;
    opens org.example.Admin to javafx.fxml;


    opens org.example.Entity to org.hibernate.orm.core, javafx.base;

    exports org.example;
    exports org.example.Login;
    opens org.example.Login to javafx.fxml;
    exports org.example.Utils;
    opens org.example.Utils to javafx.fxml;
}
