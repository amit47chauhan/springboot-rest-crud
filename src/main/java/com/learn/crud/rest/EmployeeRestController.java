package com.learn.crud.rest;

import com.learn.crud.dao.EmployeeDAO;
import com.learn.crud.entity.Employee;
import com.learn.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    // expose "/employees" and return a list of

    @GetMapping("/employees")
    public List<Employee> findAllEmployees() {
        return employeeService.findAll();
    }

    // GET : /employees/{employeeId}
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee employee = employeeService.findById(employeeId);

        if(employee == null){
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        return employee;
    }

    // Mapping for POST/employees - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee){
         //also just in case they pass id in JSON .. set id to 0
         //This is as to force a save of new item ... instead of update
         employee.setId(0);
         Employee dbEmployee = employeeService.save(employee);

         return dbEmployee;
     }

     //update existing employee

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee){
        Employee dbEmployee = employeeService.save(employee);

        return dbEmployee;
    }



}
