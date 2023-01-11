package org.main.Entity;

import org.main.Entity.Temporaty.DailyStatusWork;
import org.main.Entity.Temporaty.WorkNameDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class WorksRepositoryImpl implements WorksRepository {
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;


    @Override
    public Works getWorkByID(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Works> typedQuery = entityManager.createQuery("SELECT w FROM Works w WHERE w.idWork=:id", Works.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList().get(0);
    }

    @Override
    public Works getLastWorkByTire(int idTire) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Works> typedQuery = entityManager.createQuery("SELECT w FROM Works w INNER JOIN w.tires  t WHERE t.idTire=:id ORDER BY w.dateStop DESC",Works.class);
        //typedQuery.setParameter("id", idTire);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList().get(0);
    }

    @Override
    public List<Works> getListWorksByTire(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Works> typedQuery = entityManager.createQuery("SELECT w FROM Works w INNER JOIN w.tires  t WHERE t.idTire=:id ORDER BY w.dateStop DESC" ,Works.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }

    @Override
    public List<Works> getListWorksByWorker(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Works> typedQuery = entityManager.createQuery("SELECT w FROM Works w INNER JOIN w.workers  t WHERE t.idWorker=:id " ,Works.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }

    @Override
    public List<Works> getListWorks() {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Works> typedQuery = entityManager.createQuery("SELECT w FROM Works w ", Works.class);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }

    @Override
    public boolean save(Works works) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (works.getIdWork() == 0) {
                entityManager.persist(works);
            } else {
                entityManager.merge(works);
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

    public Works saveWork(Works works) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (works.getIdWork() == 0) {
                entityManager.persist(works);
            } else {
                entityManager.merge(works);
            }
            entityTransaction.commit();

            return works;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            return null;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public boolean change(Works works) {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(works);
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
    public boolean remove(int id) {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            Works works=getWorkByID(id);
            entityManager.remove(works);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            return false;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }



    public boolean removed(Works works) {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.remove(works);
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
    public Map<String, Integer> countWorkStatus(Departments departments) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;
        if(departments==null){
            sumHql = "SELECT status,COUNT(name) FROM works GROUP BY status";
            results= entityManager.createNativeQuery(sumHql).getResultList();
        }
        else{
            sumHql = "SELECT status,COUNT(w.name) FROM works w INNER JOIN departments d ON w.id_department=d.id_department WHERE d.name=:name GROUP BY status";
            results = entityManager.createNativeQuery(sumHql).setParameter("name",departments.getName()).getResultList();
        }


        Map<String, Integer> statusCounts = new HashMap<>();
        for (Object[] result : results) {
            String status = (String) result[0];
            Integer count = ((BigInteger) result[1]).intValue();
            statusCounts.put(status, count);
        }

        return statusCounts.isEmpty() ? null:statusCounts;
    }

    @Override
    public List<WorkNameDate> countWorkname(Departments departments) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;
        if(departments==null){
            sumHql = "SELECT DATE(w.date_stop) ,w.name, COUNT(w.name) FROM works w " +
                    " WHERE DATE(date_stop) BETWEEN DATE_SUB(CURDATE(), INTERVAL 31 DAY) " +
                    " AND CURRENT_DATE GROUP By w.name, DATE(date_stop) ORDER By DATE(date_stop)";
            results= entityManager.createNativeQuery(sumHql).getResultList();
        }
        else{
            sumHql = "SELECT Date(w.date_stop ) , w.name,COUNT(w.name) FROM works w " +
                    " INNER JOIN departments d ON w.id_department=d.id_department " +
                    " WHERE d.name=:name AND DATE(w.date_stop) BETWEEN DATE_SUB(CURDATE(), INTERVAL 31 DAY) AND CURRENT_DATE " +
                    " GROUP By w.name, DATE(w.date_stop)";
            results = entityManager.createNativeQuery(sumHql).setParameter("name",departments.getName()).getResultList();
        }


       List<WorkNameDate> workNameDates=new ArrayList<>();
        for (Object[] result : results) {
            Date date= (Date) result[0];
           workNameDates.add(new WorkNameDate((Date) result[0],(String) result[1],((BigInteger) result[2]).intValue()));
        }

        return workNameDates.isEmpty() ? null:workNameDates;
    }

    @Override
    public List<DailyStatusWork> countDailyWorkStatus(Departments departments) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;
        if(departments==null){
            sumHql = "SELECT w.name,w.status, COUNT(w.name) FROM works w WHERE " +
                    " (CURRENT_DATE = DATE(w.date_stop) OR CURRENT_DATE=DATE(w.date_start)) GROUP By w.name";
            results= entityManager.createNativeQuery(sumHql).getResultList();
        }
        else{
            sumHql = "SELECT w.name,w.status, COUNT(w.name) FROM works w  " +
                    " INNER JOIN departments d ON w.id_department=d.id_department " +
                    " WHERE d.name=:name AND (CURRENT_DATE = DATE(w.date_stop) OR CURRENT_DATE=DATE(w.date_start)) GROUP By w.name ";
            results = entityManager.createNativeQuery(sumHql).setParameter("name",departments.getName()).getResultList();
        }


        List<DailyStatusWork> dailyStatusWorks=new ArrayList<>();
        for (Object[] result : results) {
           dailyStatusWorks.add(new DailyStatusWork((String) result[0],(String) result[1],((BigInteger) result[2]).intValue()));
        }

        return dailyStatusWorks.isEmpty() ? null:dailyStatusWorks;
    }

    @Override
    public Map<String, Integer> countWorkStatusWorker(Workers workers) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;

        sumHql = "SELECT status,COUNT(w.name) FROM works w INNER JOIN workers ws ON w.id_worker=ws.id_worker WHERE ws.id_worker=:id GROUP BY status";
        results = entityManager.createNativeQuery(sumHql).setParameter("id",workers.getIdWorker()).getResultList();

        Map<String, Integer> statusCounts = new HashMap<>();
        for (Object[] result : results) {
            String status = (String) result[0];
            Integer count = ((BigInteger) result[1]).intValue();
            statusCounts.put(status, count);
        }

        return statusCounts.isEmpty() ? null:statusCounts;
    }

    @Override
    public List<WorkNameDate> countWorkNameWorker(Workers workers) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;


            sumHql = " SELECT Date(w.date_stop ) , w.name,COUNT(w.name) FROM works w  " +
                    " INNER JOIN workers ws ON w.id_worker=ws.id_worker " +
                    " WHERE ws.id_worker=:id AND DATE(w.date_stop) BETWEEN DATE_SUB(CURDATE(), INTERVAL 31 DAY) AND CURRENT_DATE " +
                    " GROUP By w.name, DATE(w.date_stop)";
        results = entityManager.createNativeQuery(sumHql).setParameter("id",workers.getIdWorker()).getResultList();


        List<WorkNameDate> workNameDates=new ArrayList<>();
        for (Object[] result : results) {
            Date date= (Date) result[0];
            workNameDates.add(new WorkNameDate((Date) result[0],(String) result[1],((BigInteger) result[2]).intValue()));
        }

        return workNameDates.isEmpty() ? null:workNameDates;
    }

    @Override
    public List<WorkNameDate> countTimeWorkNameWorker(Workers workers) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;

        if(workers==null) {
            sumHql = " SELECT DATE(w.date_stop),w.name,CEILING(AVG(TIMESTAMPDIFF(MINUTE,w.date_start,w.date_stop))) " +
                    " FROM works w WHERE w.date_stop IS NOT NULL " +
                    " AND DATE(w.date_stop) BETWEEN DATE_SUB(CURDATE(), INTERVAL 31 DAY) AND CURRENT_DATE " +
                    "GROUP BY w.name ORDER BY DATE(w.date_stop);";
            results = entityManager.createNativeQuery(sumHql).getResultList();
        }else {
            sumHql = " SELECT DATE(w.date_stop),w.name,CEILING(AVG(TIMESTAMPDIFF(MINUTE,w.date_start,w.date_stop))) FROM works w " +
                    " WHERE id_worker=:id AND w.date_stop IS NOT NULL  " +
                    " AND DATE(w.date_stop) BETWEEN DATE_SUB(CURDATE(), INTERVAL 31 DAY) AND CURRENT_DATE " +
                    " GROUP BY w.name ORDER BY DATE(w.date_stop);";
            results = entityManager.createNativeQuery(sumHql).setParameter("id",workers.getIdWorker()).getResultList();
        }


        List<WorkNameDate> workNameDates=new ArrayList<>();
        for (Object[] result : results) {
            Date date= (Date) result[0];
            workNameDates.add(new WorkNameDate((Date) result[0],(String) result[1],((BigDecimal) result[2]).intValue()));
        }

        return workNameDates.isEmpty() ? null:workNameDates;
    }

    @Override
    public List<DailyStatusWork> countDailyWorkStatusWorker(Workers workers) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;

            sumHql = " SELECT w.name,w.status, COUNT(w.name) FROM works w  " +
                    " INNER  JOIN workers ws ON w.id_worker=ws.id_worker " +
                    " WHERE ws.id_worker=:id AND (CURRENT_DATE = DATE(w.date_stop) OR CURRENT_DATE=DATE(w.date_start)) " +
                    " GROUP By w.name;";
        results = entityManager.createNativeQuery(sumHql).setParameter("id",workers.getIdWorker()).getResultList();



        List<DailyStatusWork> dailyStatusWorks=new ArrayList<>();
        for (Object[] result : results) {
            dailyStatusWorks.add(new DailyStatusWork((String) result[0],(String) result[1],((BigInteger) result[2]).intValue()));
        }

        return dailyStatusWorks.isEmpty() ? null:dailyStatusWorks;
    }
}
