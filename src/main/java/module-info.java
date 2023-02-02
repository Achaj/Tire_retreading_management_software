module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.persistence;
    requires java.sql;
    requires com.fasterxml.classmate;
    requires java.xml.bind;
    requires org.hibernate.orm.core;
    //requires mysql.connector.java;

    requires java.naming;
    requires net.bytebuddy;
    requires kernel;
    requires layout;
    requires commons.codec;
    requires org.jetbrains.annotations;
    requires io;

    opens org.main to javafx.fxml;
    opens org.main.Admin to javafx.fxml;
    opens org.main.User to javafx.fxml;


    opens org.main.Entity to org.hibernate.orm.core, javafx.base;

    exports org.main;
    exports org.main.Login;
    opens org.main.Login to javafx.fxml;
    exports org.main.Utils;
    opens org.main.Utils to javafx.fxml;
}
