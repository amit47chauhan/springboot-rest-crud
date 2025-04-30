package com.learn.crud.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.learn.crud.dao.EmployeeDAO;
import com.learn.crud.entity.Employee;
import com.learn.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.apache.tomcat.jni.SSLConf.apply;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private EmployeeService employeeService;
    private ObjectMapper objectMapper;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
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

    //Patch method to patch some details of an employee
    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> patchPayload){
        Employee employee = employeeService.findById(employeeId);

        // throw exception if null
        if(employee == null){
            throw  new RuntimeException("Employee not found -" + employeeId);
        }

        // throw exception if request body contains "id" key
        if(patchPayload.containsKey("id")){
            throw  new RuntimeException("Employee id not allowed in Request Body - " + employeeId);
        }

        Employee patchedEmployee = apply(patchPayload, employee);
        Employee dbEmployee = employeeService.save(patchedEmployee);

        return dbEmployee;
    }

    private Employee apply(Map<String, Object> patchPayload, Employee employee) {
        // Convert employee object to a JSON object node
        ObjectNode employeeNode = objectMapper.convertValue(employee, ObjectNode.class);

        //convert the patchPayload map to JSON object node
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);

        // merge the patch updates into the employee node
        employeeNode.setAll(patchNode);

        return objectMapper.convertValue(employeeNode, Employee.class);
    }


}
