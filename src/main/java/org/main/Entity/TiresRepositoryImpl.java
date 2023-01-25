package org.main.Entity;


import org.main.Entity.Temporaty.DailyStatusWork;
import org.main.Entity.Temporaty.TireDepartmentTime;
import org.main.Utils.MyLogger;
import org.main.Utils.Temporary;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiresRepositoryImpl implements TiresRepository {

    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;

    Logger logger = MyLogger.getInstance().getLogger();

    @Override
    public Tires getTireById(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Tires> tiresTypedQuery = entityManager.createQuery("SELECT t FROM Tires t WHERE t.idTire=:id", Tires.class);
        tiresTypedQuery.setParameter("id", id);
        return tiresTypedQuery.getResultList().isEmpty() ? null : tiresTypedQuery.getResultList().get(0);
    }

    @Override
    public Tires getTireByTag(String tag) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Tires> tiresTypedQuery = entityManager.createQuery("SELECT t FROM Tires t WHERE t.tag=:tag", Tires.class);
        tiresTypedQuery.setParameter("tag", tag);
        return tiresTypedQuery.getResultList().isEmpty() ? null : tiresTypedQuery.getResultList().get(0);
    }

    @Override
    public List<Tires> getTires() {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Tires> tiresTypedQuery = entityManager.createQuery("SELECT t FROM Tires t", Tires.class);
        return tiresTypedQuery.getResultList();
    }

    @Override
    public boolean saveTire(Tires tire) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (tire.getIdTire() == 0) {
                entityManager.persist(tire);
            } else {
                entityManager.merge(tire);
            }
            entityTransaction.commit();
            logger.log(Level.INFO, "Save:" + tire.toString() + " By:" + Temporary.getWorkers().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR BY" + Temporary.getWorkers(), e);
            return false;
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }

    }

    @Override
    public Tires save(Tires tire) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (tire.getIdTire() == 0) {
                entityManager.persist(tire);
            } else {
                entityManager.merge(tire);
            }
            entityTransaction.commit();
            logger.log(Level.INFO, "Save:" + tire.toString() + " By:" + Temporary.getWorkers().toString());
            return tire;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR BY" + Temporary.getWorkers(), e);
            return null;
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public boolean changeTire(Tires tire) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(tire);
            entityTransaction.commit();
            logger.log(Level.INFO, "CHANGE:" + tire.toString() + " By:" + Temporary.getWorkers().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR BY" + Temporary.getWorkers(), e);
            return false;
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public boolean delateTire(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            Tires tire = getTireById(id);
            entityManager.remove(tire);
            entityTransaction.commit();
            logger.log(Level.INFO, "REMOVE:" + tire.toString() + " By:" + Temporary.getWorkers().toString());
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "ERROR BY" + Temporary.getWorkers(), e);
            entityTransaction.rollback();
            return false;
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public List<TireDepartmentTime> getTiresStockLevel(Departments departments) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        String sumHql;
        List<Object[]> results;


        sumHql = " SELECT t.id_tire,t.height,t.width,t.diameter,t.tag,t.load_index,t.speed_index,d.name," +
                " GREATEST(w.date_start, IFNULL(w.date_stop, w.date_start)) FROM works w " +
                " INNER JOIN departments d On w.id_department=d.id_department " +
                " INNER Join tires t On w.id_tire=t.id_tire GROUP BY t.tag;";
        results = entityManager.createNativeQuery(sumHql).getResultList();


        List<TireDepartmentTime> tireDepartmentTimeList = new ArrayList<>();
        for (Object[] result : results) {
            //dailyStatusWorks.add(new DailyStatusWork((String) result[0],(String) result[1],((BigInteger) result[2]).intValue()));
            if (departments != null) {
                if (departments.getName().equals((String) result[7])) {
                    tireDepartmentTimeList.add(new TireDepartmentTime((Integer) result[0], (Integer) result[1], (Integer) result[2], (Integer) result[3],
                            (String) result[4], (String) result[5], (String) result[6], (String) result[7], ((Timestamp) result[8]).toLocalDateTime()));
                }

            } else {
                tireDepartmentTimeList.add(new TireDepartmentTime((Integer) result[0], (Integer) result[1], (Integer) result[2], (Integer) result[3],
                        (String) result[4], (String) result[5], (String) result[6], (String) result[7], ((Timestamp) result[8]).toLocalDateTime()));
            }
        }

        return tireDepartmentTimeList.isEmpty() ? null : tireDepartmentTimeList;
    }

}
