package com.vs.DemoApplication;

import com.vs.DemoApplication.controller.EmployeeController;
import com.vs.DemoApplication.model.Address;
import com.vs.DemoApplication.model.Employee;
import com.vs.DemoApplication.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
public class MockMvcWithSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetEmployeeBYID() throws Exception {
        //Arrange
        var employee = Employee.builder()
                .id(2)
                .email("mocksetup@gmail.com")
                .phone(12345)
                .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                .name("Mock")
                .build();
        //Act
        when(employeeService.getEmployeeByID(1)).thenReturn(employee);

        //Assertion
        var result = this.mockMvc.perform(get("/employee/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("Mock"))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("mocksetup@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(2))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetEmployeeList() throws Exception {
        //Arrange
        List<Employee> employeeList = new ArrayList<>(List.of(
                Employee.builder()
                        .id(1)
                        .email("mocksetup@gmail.com")
                        .phone(12345)
                        .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                        .name("Mock")
                        .build(),
                Employee.builder()
                        .id(2)
                        .email("raj2@gmail.com")
                        .phone(73433433)
                        .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                        .name("raj2")
                        .build(),
                Employee.builder()
                        .id(3)
                        .email("raj3@gmail.com")
                        .phone(3433433)
                        .address(Address.builder().city("raj city").country("raj country").street("street").build())
                        .name("raj3")
                        .build()));
        //Act
        when(employeeService.getAllEmployees()).thenReturn(employeeList);
        //Assertion
        var result = this.mockMvc.perform(get("/employee"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Mock"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phone").value("12345"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address.city").value("auckland"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address.country").value("NZ"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("mocksetup@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
