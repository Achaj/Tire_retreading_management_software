package org.main.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentsRepositoryImpl implements DepartmentsRepository {
    EntityManager entityManager = ConnectionToDB.entityManager;
    EntityTransaction entityTransaction = ConnectionToDB.entityTransaction;

    @Override
    public List<Departments> getDepartments() {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Departments> departmentsTypedQuery = entityManager.createQuery("SELECT d FROM Departments d", Departments.class);
        return departmentsTypedQuery.getResultList();
    }

    @Override
    public boolean saveDepartment(Departments departments) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            if (departments.getIdDepartment() == 0) {
                entityManager.persist(departments);
            } else {
                 entityManager.merge(departments);
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
    public Departments getDepartment(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Departments> productTypedQuery = entityManager.createQuery("FROM Departments d WHERE d.idDepartment=:id", Departments.class);
        productTypedQuery.setParameter("id", id);
        return productTypedQuery.getResultList().get(0);
    }

    @Override
    public Departments getDepartmentByName(String name) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        TypedQuery<Departments> productTypedQuery = entityManager.createQuery("FROM Departments d WHERE d.name LIKE:name", Departments.class);
        productTypedQuery.setParameter("name", name);
        if (productTypedQuery.getResultList().size() > 0) {
            return productTypedQuery.getResultList().get(0);
        } else {
            return null;
        }

    }

    @Override
    public List<Departments> getDepartmentByCity(String city) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        TypedQuery<Departments> productTypedQuery = entityManager.createQuery("FROM Departments d WHERE d.city=:name", Departments.class);
        productTypedQuery.setParameter("name", city);
        return productTypedQuery.getResultList();
    }

    @Override
    public boolean changeDepartment(Departments departments) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        try {
            entityManager.merge(departments);
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
    public boolean delateDepartment(int id) {
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }

        try {
            Departments departments = getDepartment(id);
            entityManager.remove(departments);
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
