package org.main.Entity;

import org.main.Entity.Temporaty.DailyStatusWork;
import org.main.Entity.Temporaty.DaytimeWork;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
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
    public boolean remove(WorkingTime workingTime) {
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

    @Override
    public List<DaytimeWork> getListOfEmployeeMinutesWorked(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;

        sumHql = " SELECT DATE(wt.date_logout), COUNT( TIMESTAMPDIFF(MINUTE,wt.date_login,wt.date_logout)) " +
                " FROM working_time wt Where wt.date_logout IS NOT NULL  AND wt.id_worker=:id " +
                " GROUP BY DATE(wt.date_logout) ORDER BY DATE(wt.date_logout)";
        results = entityManager.createNativeQuery(sumHql).setParameter("id",id).getResultList();

        List<DaytimeWork> dailyStatusWorks=new ArrayList<>();
        for (Object[] result : results) {
            dailyStatusWorks.add(new DaytimeWork((Date) result[0],((BigInteger) result[1]).intValue()));
        }

        return dailyStatusWorks.isEmpty() ? null:dailyStatusWorks;
    }
}
