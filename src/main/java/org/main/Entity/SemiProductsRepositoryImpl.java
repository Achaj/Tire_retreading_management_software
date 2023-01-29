package org.main.Entity;

import org.main.Utils.MyLogger;
import org.main.Utils.Temporary;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SemiProductsRepositoryImpl implements SemiProductsRepository{
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;
    Logger logger = MyLogger.getInstance().getLogger();
    @Override
    public SemiProducts getSemiProductsById(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<SemiProducts> typedQuery = entityManager.createQuery("SELECT sp FROM SemiProducts sp WHERE sp.idSemiProduct=:id", SemiProducts.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList().get(0);
    }

    @Override
    public SemiProducts getSemiProductsTag(String tag) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<SemiProducts> typedQuery = entityManager.createQuery("SELECT sp FROM SemiProducts sp WHERE sp.tag=:tag", SemiProducts.class);
        typedQuery.setParameter("tag", tag);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList().get(0);
    }

    @Override
    public List<SemiProducts> getSemiProducts() {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<SemiProducts> typedQuery = entityManager.createQuery("SELECT sp FROM SemiProducts sp", SemiProducts.class);

        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }

    @Override
    public List<SemiProducts> getSemiProductsByWorkID(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<SemiProducts> typedQuery = entityManager.createQuery("SELECT sp FROM SemiProducts sp " +
                " INNER JOIN sp.workSemiProducts  wsp" +
                "  INNER JOIN wsp.works w WHERE w.idWork=:id", SemiProducts.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().isEmpty() ? null : typedQuery.getResultList();
    }

    @Override
    public boolean saveSemiProduct(SemiProducts semiProducts) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (semiProducts.getIdSemiProduct() == 0) {
                entityManager.persist(semiProducts);
            } else {
                entityManager.merge(semiProducts);
            }
            entityTransaction.commit();
            logger.log(Level.INFO, "SAVE:" + semiProducts.toString() + " By: ");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR  By: " + Temporary.getWorkers().toString(), e);
            return false;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public boolean changeSemiProduct(SemiProducts semiProducts) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(semiProducts);
            entityTransaction.commit();
            logger.log(Level.INFO, "CHANGE:" + semiProducts.toString() + " By: " + Temporary.getWorkers().toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR  By: " + Temporary.getWorkers().toString(), e);
            return false;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }

    @Override
    public boolean delateSemiProduct(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            SemiProducts semiProduct= getSemiProductsById(id);
            entityManager.remove(semiProduct);
            entityTransaction.commit();
            logger.log(Level.INFO, "REMOVE :" + semiProduct.toString() + " By: " + Temporary.getWorkers().toString());
            return true;
        } catch (Exception e) {
            entityTransaction.rollback();
            logger.log(Level.WARNING, "ERROR  By: " + Temporary.getWorkers().toString(), e);
            return false;
        }finally {
            entityManager.getEntityManagerFactory().getCache().evictAll();
        }
    }
}
