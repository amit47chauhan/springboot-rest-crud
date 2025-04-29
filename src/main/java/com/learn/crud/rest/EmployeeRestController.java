package com.learn.crud.rest;

import com.learn.crud.dao.EmployeeDAO;
import com.learn.crud.entity.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeDAO employeeDAO;

    // quick and dirty : inject employee dao (using constructor injection),
    // will refactor later

    public EmployeeRestController(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    // expose "/employees" and return a list of

    @GetMapping("/employees")
    public List<Employee> findAllEmployees() {
        return employeeDAO.findAll();
    }

}
