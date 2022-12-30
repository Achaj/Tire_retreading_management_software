package org.main.Entity;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class TiresRepositoryImpl implements  TiresRepository{

    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;



    @Override
    public Tires getTireById(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Tires> tiresTypedQuery = entityManager.createQuery("SELECT t FROM Tires t WHERE t.idTire=:id", Tires.class);
        tiresTypedQuery.setParameter("id", id);
        return  tiresTypedQuery.getResultList().isEmpty() ? null : tiresTypedQuery.getResultList().get(0);
    }

    @Override
    public Tires getTireByTag(String tag) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Tires> tiresTypedQuery = entityManager.createQuery("SELECT t FROM Tires t WHERE t.tag=:tag", Tires.class);
        tiresTypedQuery.setParameter("tag", tag);
        return  tiresTypedQuery.getResultList().isEmpty() ? null : tiresTypedQuery.getResultList().get(0);
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
            return tire;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
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
    public boolean delateTire(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            Tires tire = getTireById(id);
            entityManager.remove(tire);
            entityTransaction.commit();
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            return false;
        } finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

}
