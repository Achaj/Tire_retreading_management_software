package org.main.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class SemiProductsRepositoryImpl implements SemiProductsRepository{
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;
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
    public boolean saveSemiProduct(SemiProducts semiProducts) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.persist(semiProducts);
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
    public boolean changeSemiProduct(SemiProducts semiProducts) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(semiProducts);
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
    public boolean delateSemiProduct(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            SemiProducts semiProduct= getSemiProductsById(id);
            entityManager.remove(semiProduct);
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
