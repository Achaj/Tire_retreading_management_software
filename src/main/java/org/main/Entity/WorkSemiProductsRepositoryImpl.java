package org.main.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class WorkSemiProductsRepositoryImpl implements WorkSemiProductsRepository {
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;

    public boolean save(WorkSemiProducts workSemiProducts) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.persist(workSemiProducts);
            entityTransaction.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
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

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
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
