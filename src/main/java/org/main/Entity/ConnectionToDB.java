package org.main.Entity;

import javafx.scene.control.Alert;
import org.main.Utils.MyLogger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionToDB {

    public static EntityManagerFactory getEntityManagerFactory() {

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("DBCONNECTION");
            if (!entityManagerFactory.isOpen()) {
                entityManagerFactory = Persistence.createEntityManagerFactory("DBCONNECTION");
            }

        } catch (Exception exception) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Nie można połączyć się z bazą danych");
            alert.showAndWait();
            MyLogger.getInstance().getLogger().log(Level.WARNING, "Conention FAIL");

        }
        return entityManagerFactory;
    }

    public static EntityManagerFactory entityManagerFactory = null;
    public static EntityManager entityManager = ConnectionToDB.getEntityManagerFactory().createEntityManager();
    public static EntityTransaction entityTransaction = entityManager.getTransaction();

}
