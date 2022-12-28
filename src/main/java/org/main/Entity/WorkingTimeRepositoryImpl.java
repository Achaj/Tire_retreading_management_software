package org.main.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class WorkingTimeRepositoryImpl implements WorkingTimeRepository {
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;

    @Override
    public WorkingTime getWorkingTimeByID(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<WorkingTime> typedQuery = entityManager.createQuery("SELECT t FROM WorkingTime t WHERE t.idWorkingTime=:id", WorkingTime.class);
        typedQuery.setParameter("id", id);
        return  typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList().get(0);
    }

    @Override
    public List<WorkingTime> getListWorkingTime() {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<WorkingTime> typedQuery = entityManager.createQuery("SELECT t FROM WorkingTime t", WorkingTime.class);
        return  typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }

    @Override
    public boolean save(WorkingTime workingTime) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (workingTime.getIdWorkingTime() == 0) {
                entityManager.persist(workingTime);
            } else {
                entityManager.merge(workingTime);
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
    public boolean change(WorkingTime workingTime) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(workingTime);
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
    public boolean delate(WorkingTime workingTime) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.remove(workingTime);
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
