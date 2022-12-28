package org.main.Entity;

import javax.persistence.*;
import java.util.List;

public class WorkersRepositoryImpl implements WorkersRepository {
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;
    @Override
    public boolean saveWorker(Workers worker) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (worker.getIdWorker() == 0) {
                entityManager.persist(worker);
            } else {
                entityManager.merge(worker);
            }
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
    public Workers getWorker(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Workers> productTypedQuery = entityManager.createQuery("FROM Workers p WHERE p.idWorker=:id", Workers.class);
        productTypedQuery.setParameter("id", id);
        return productTypedQuery.getResultList().get(0);
    }
    @Override
    public Workers getWorkerByEmail(String email){
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Workers> userTypedQuery = entityManager.createQuery("FROM Workers p WHERE p.email=:email", Workers.class);
        userTypedQuery.setParameter("email", email);
        if (userTypedQuery.getResultList().size() == 0) {
            return null;
        } else {
            return userTypedQuery.getResultList().get(0);
        }
    }

    @Override
    public Workers getWorkerByTag(String tag) {
        if(!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Workers> userTypedQuery = entityManager.createQuery("FROM Workers p WHERE p.tag=:tag", Workers.class);
        userTypedQuery.setParameter("tag", tag);
        if (userTypedQuery.getResultList().size() == 0) {
            return null;
        } else {
            return userTypedQuery.getResultList().get(0);
        }
    }

    @Override
    public List<Workers> getWorkers() {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Workers> workersTypedQuery = entityManager.createQuery("SELECT p FROM Workers p", Workers.class);
        return workersTypedQuery.getResultList();
    }

    @Override
    public List<Workers> getWorkersByPosition(String position) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Workers> workersTypedQuery = entityManager.createQuery("SELECT p FROM Workers p WHERE p.position=:position", Workers.class);
        workersTypedQuery.setParameter("position", position);
        return workersTypedQuery.getResultList();
    }

    @Override
    public List<Workers> getWorkersByDepartment(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Workers> workersTypedQuery = entityManager.createQuery("SELECT w FROM Workers w " +
                " INNER JOIN w.departments d " +
                " WHERE d.idDepartment=:id", Workers.class);
        workersTypedQuery.setParameter("id", id);
        return workersTypedQuery.getResultList();
    }


    @Override
    public boolean changeDadataWorker(Workers worker) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(worker);
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
    public boolean delateWorker(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            Workers pracownik = getWorker(id);
            entityManager.remove(pracownik);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            return false;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }
}
