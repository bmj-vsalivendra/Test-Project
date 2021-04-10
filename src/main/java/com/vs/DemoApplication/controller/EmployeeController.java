package com.vs.DemoApplication.controller;

import com.vs.DemoApplication.model.Address;
import com.vs.DemoApplication.model.Employee;
import com.vs.DemoApplication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/employee")
    public List<Employee> getEmployee() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployeeByID(@PathVariable int id) {
        return employeeService.getEmployeeByID(id);

    }
    @PostMapping("/employee")
    public List<Employee> postEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@RequestBody Employee employee, @PathVariable int id) {
        return employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/employee/{id}")
    public List<Employee> updateEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }


}
