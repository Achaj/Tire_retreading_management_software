package org.main.Entity;

import org.main.Utils.MyLogger;
import org.main.Utils.Temporary;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkSemiProductsRepositoryImpl implements WorkSemiProductsRepository {
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;
    Logger logger = MyLogger.getInstance().getLogger();
    public boolean save(WorkSemiProducts workSemiProducts) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.persist(workSemiProducts);
            entityTransaction.commit();
            logger.log(Level.INFO, "SAVE:" + workSemiProducts.toString() + " BY:" + Temporary.getWorkers());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR:", e);
            return false;
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public boolean change(WorkSemiProducts workSemiProducts) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(workSemiProducts);
            entityTransaction.commit();
            logger.log(Level.INFO, "CHANGE:" + workSemiProducts.toString() + " BY:" + Temporary.getWorkers());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR:", e);
            return false;
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public boolean remove(int id) {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            WorkSemiProducts workSemiProducts=getWorkSemiProductsByID(id);
            entityManager.remove(workSemiProducts);

            entityTransaction.commit();
            logger.log(Level.INFO, "REMOVE:" + workSemiProducts.toString() + " BY:" + Temporary.getWorkers());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR:", e);
            return false;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    public boolean removed(WorkSemiProducts workSemiProducts) {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {

            entityManager.remove(workSemiProducts);
            entityTransaction.commit();
            logger.log(Level.INFO, "REMOVE:" + workSemiProducts.toString() + " BY:" + Temporary.getWorkers());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR:", e);
            return false;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public WorkSemiProducts getWorkSemiProductsByID(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<WorkSemiProducts> typedQuery = entityManager.createQuery("SELECT w FROM WorkSemiProducts w WHERE w.idWorkSemiProduct=:id", WorkSemiProducts.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList().get(0);
    }

    @Override
    public List<WorkSemiProducts> getSemiProductsList() {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<WorkSemiProducts> typedQuery = entityManager.createQuery("SELECT w FROM WorkSemiProducts w", WorkSemiProducts.class);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }

    @Override
    public List<WorkSemiProducts> getSemiProductsByWork(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<WorkSemiProducts> typedQuery = entityManager.createQuery("SELECT wsp FROM WorkSemiProducts wsp" +
                " INNER JOIN wsp.works w" +
                " WHERE w.idWork=:id", WorkSemiProducts.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }
}
