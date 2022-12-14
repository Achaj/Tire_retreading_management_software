package org.example.Entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentsRepositoryImpl implements DepartmentsRepository{
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
}
