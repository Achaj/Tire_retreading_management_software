package org.main.Entity;

import org.jetbrains.annotations.NotNull;
import org.main.Entity.Temporaty.EmployeesOverworkedTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        } finally {
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
    public Workers getWorkerByEmail(String email) {
        if (!entityTransaction.isActive()) {
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
        if (!entityTransaction.isActive()) {
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
        } finally {
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
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public List<EmployeesOverworkedTime> getEmployeesOverworkedTimeList(Workers workers ,Date date) {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;
        if (workers != null) {
            sumHql = " SELECT w.first_name,w.last_name,w.email, DATE_FORMAT(DATE_ADD(LAST_DAY(:date), INTERVAL -1 DAY),'%Y-%m-01') AS FirstDay, " +
                    " LAST_DAY(:date) AS LastDay, SUM( TIMESTAMPDIFF(MINUTE, wt.date_login, wt.date_logout)) AS WorkingMinutsAtMounth " +
                    " FROM working_time wt INNER JOIN workers w ON wt.id_worker=w.id_worker Where wt.id_worker=:id and  wt.date_logout IS NOT NULL  AND  " +
                    " DATE(wt.date_logout) BETWEEN DATE_FORMAT(DATE_ADD(LAST_DAY(:date), INTERVAL -1 DAY),'%Y-%m-01') " +
                    " AND LAST_DAY(:date) GROUP BY w.email;";
            results = entityManager.createNativeQuery(sumHql).setParameter("id", workers.getIdWorker()).setParameter("date",date).getResultList();
        } else {
            sumHql = " SELECT w.first_name,w.last_name,w.email, DATE_FORMAT(DATE_ADD(LAST_DAY(:date), INTERVAL -1 DAY),'%Y-%m-01') AS FirstDay, " +
                    " LAST_DAY(:date) AS LastDay, SUM( TIMESTAMPDIFF(MINUTE, wt.date_login, wt.date_logout)) AS WorkingMinutsAtMounth " +
                    " FROM working_time wt INNER JOIN workers w ON wt.id_worker=w.id_worker Where wt.date_logout IS NOT NULL  AND  " +
                    " DATE(wt.date_logout) BETWEEN DATE_FORMAT(DATE_ADD(LAST_DAY(:date), INTERVAL -1 DAY),'%Y-%m-01') " +
                    " AND LAST_DAY(:date) GROUP BY w.email;";
            results = entityManager.createNativeQuery(sumHql).setParameter("date",date).getResultList();
        }

        List<EmployeesOverworkedTime> employeesOverworkedTimes = new ArrayList<>();
        for (Object[] result : results) {

            employeesOverworkedTimes.add(new EmployeesOverworkedTime(
                    (String) result[0], (String) result[1], (String) result[2],
                    Date.valueOf((String) result[3]), (Date) result[4],
                    minutesToHoursAndMinutesFloat(((BigDecimal) result[5]).intValue())));
            // dailyStatusWorks.add(new DaytimeWork((Date) result[0],((BigDecimal) result[1]).intValue()));
        }

        return employeesOverworkedTimes.isEmpty() ? null : employeesOverworkedTimes;
    }

    @Override
    public List<EmployeesOverworkedTime> getEmployeesOverworkedTimeByDepatmentList(Departments departments, Date date) {

        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;

            sumHql = "SELECT w.first_name,w.last_name,w.email," +
                    " DATE_FORMAT(DATE_ADD(LAST_DAY(:date), INTERVAL -1 DAY),'%Y-%m-01') AS FirstDay, LAST_DAY(:date) AS LastDay," +
                    " SUM( TIMESTAMPDIFF(MINUTE,wt.date_login,wt.date_logout)) AS WorkingMinutsAtMounth " +
                    " FROM working_time wt INNER JOIN workers w ON wt.id_worker=w.id_worker " +
                    " INNER JOIN departments d On w.id_department=d.id_department " +
                    " Where d.id_department=:id AND wt.date_logout IS NOT NULL " +
                    " AND DATE(wt.date_logout) BETWEEN DATE_FORMAT(DATE_ADD(LAST_DAY(:date), INTERVAL -1 DAY),'%Y-%m-01') " +
                    " AND LAST_DAY(:date) GROUP By w.email;";
            results = entityManager.createNativeQuery(sumHql).setParameter("id",departments.getIdDepartment()).setParameter("date",date).getResultList();


        List<EmployeesOverworkedTime> employeesOverworkedTimes = new ArrayList<>();
        for (Object[] result : results) {

            employeesOverworkedTimes.add(new EmployeesOverworkedTime(
                    (String) result[0], (String) result[1], (String) result[2],
                    Date.valueOf((String) result[3]), (Date) result[4],
                    minutesToHoursAndMinutesFloat(((BigDecimal) result[5]).intValue())));
            // dailyStatusWorks.add(new DaytimeWork((Date) result[0],((BigDecimal) result[1]).intValue()));
        }

        return employeesOverworkedTimes.isEmpty() ? null : employeesOverworkedTimes;
    }

    @Override
    public List<EmployeesOverworkedTime> getEmployeesOverworkedTimeByDaysList(@NotNull Workers workers, Date date) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;

            sumHql = "SELECT w.first_name,w.last_name,w.email,DATE(wt.date_login),DATE(wt.date_logout)," +
                    " SUM( TIMESTAMPDIFF(MINUTE,wt.date_login,wt.date_logout)) AS WorkingMinutsAtMounth " +
                    " FROM working_time wt INNER JOIN workers w ON wt.id_worker=w.id_worker " +
                    " Where wt.id_worker=:id AND wt.date_logout IS NOT NULL  AND   " +
                    " DATE(wt.date_logout) BETWEEN DATE_FORMAT(DATE_ADD(LAST_DAY(:date), INTERVAL -1 DAY),'%Y-%m-01')  AND LAST_DAY(:date) " +
                    " GROUP BY DATE(wt.date_logout);";
            results = entityManager.createNativeQuery(sumHql).setParameter("id", workers.getIdWorker()).setParameter("date",date).getResultList();


        List<EmployeesOverworkedTime> employeesOverworkedTimes = new ArrayList<>();
        for (Object[] result : results) {

            employeesOverworkedTimes.add(new EmployeesOverworkedTime(
                    (String) result[0], (String) result[1], (String) result[2],
                    (Date) result[3], (Date) result[4],
                    minutesToHoursAndMinutesFloat(((BigDecimal) result[5]).intValue())));
            // dailyStatusWorks.add(new DaytimeWork((Date) result[0],((BigDecimal) result[1]).intValue()));
        }

        return employeesOverworkedTimes.isEmpty() ? null : employeesOverworkedTimes;
    }

    public static float minutesToHoursAndMinutesFloat(int minutes) {
        float hours = (float) Math.floor((float) minutes / 60);
        float remainderMinutes = ((float) minutes % 60) / 100;
        DecimalFormat df = new DecimalFormat("#.##");
        // df.setDecimalSeparatorAlwaysShown(false);
        float val = hours + remainderMinutes;
        return val;
    }

}
