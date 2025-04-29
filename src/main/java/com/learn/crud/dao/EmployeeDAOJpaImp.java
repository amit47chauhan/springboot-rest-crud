package com.learn.crud.dao;

import com.learn.crud.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOJpaImp implements EmployeeDAO{

    //fields for entityManager
    private EntityManager entityManager;

    //Constructor Injection
    @Autowired
    public EmployeeDAOJpaImp(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        // query
        TypedQuery<Employee> query = entityManager.createQuery(
                "from Employee", Employee.class
        );

        // executing query and result list
        List<Employee> employees = query.getResultList();

        // returning the result
        return employees;
    }
}
