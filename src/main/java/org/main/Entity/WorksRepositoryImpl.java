package org.main.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class WorksRepositoryImpl implements WorksRepository {
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;

    @Override
    public Works getWorkByID(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Works> typedQuery = entityManager.createQuery("SELECT w FROM Works w WHERE w.idSemiProduct=:id", Works.class);
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
}
