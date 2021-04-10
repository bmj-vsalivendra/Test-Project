package com.vs.DemoApplication.service;

import com.vs.DemoApplication.model.Address;
import com.vs.DemoApplication.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeService {

    private List<Employee> employeeList = new ArrayList<>(List.of(
            Employee.builder()
                    .id(1)
                    .email("raj@gmail.com")
                    .phone(073433433)
                    .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                    .name("raj")
                    .build(),
            Employee.builder()
                    .id(2)
                    .email("raj2@gmail.com")
                    .phone(073433433)
                    .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                    .name("raj2")
                    .build(),
            Employee.builder()
                    .id(3)
                    .email("raj3@gmail.com")
                    .phone(073433433)
                    .address(Address.builder().city("raj city").country("raj country").street("street").build())
                    .name("raj3")
                    .build()));


    public List<Employee> getAllEmployees() {
        return employeeList;
    }

    public Employee getEmployeeByID(int id) {
        return employeeList.stream().filter(s -> s.getId() == id).findFirst().get();
    }

    public List<Employee> addEmployee(Employee employee) {
        employeeList.add(employee);
        return employeeList;
    }

    public Employee updateEmployee(int id, Employee employee) {
        return employeeList.stream()
                .filter(s -> s.getId() == id)
                .peek(o -> o.setName(employee.getName()))
                .peek(o -> o.setEmail(employee.getEmail()))
                .peek(o -> o.setPhone(employee.getPhone()))
                .peek(o -> o.setAddress(employee.getAddress()))
                .findFirst().get();
    }

    public List<Employee> deleteEmployee(int id) {
        employeeList.removeIf(s -> s.getId() == id);
        return employeeList;
    }
}
