package org.main.Entity;

import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ConnectionToDB {

    public static EntityManagerFactory getEntityManagerFactory() {
        try {
            entityManagerFactory= Persistence.createEntityManagerFactory("DBCONNECTION");
            if (!entityManagerFactory.isOpen()) {
                entityManagerFactory = Persistence.createEntityManagerFactory("DBCONNECTION");
            }
        } catch (Exception exception) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Nie można połączyć się z bazą danych");
            alert.showAndWait();
        }
        return entityManagerFactory;
    }

    static EntityManagerFactory entityManagerFactory=null;
    static EntityManager entityManager = ConnectionToDB.getEntityManagerFactory().createEntityManager();
    static EntityTransaction entityTransaction = entityManager.getTransaction();

}
