package org.example.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class WorkerRepositoryImpl implements WorkerRepository{
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
                worker = entityManager.merge(worker);
            }
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            return false;
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
    public Workers getWorkers(String email){
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
        }
    }
}
