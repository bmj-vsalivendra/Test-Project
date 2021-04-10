package com.vs.DemoApplication;

import com.vs.DemoApplication.model.Address;
import com.vs.DemoApplication.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringTestRestTemplateTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    String baseUrl;

//    @BeforeEach
//    public void initialiseValues() {
//        baseUrl = "http://localhost:" + port + "/employee";
//
//    }


    @ParameterizedTest(name = "get the first employee record")
    @CsvSource(value = {"first record, 1", "second record, 2", "third record, 3"}, nullValues = "NIL")
    @DisplayName("getASingleEmployeeByID")
    public void getEmployeeByID(final String recordKey, String recordValue) {
        //Arrange
        this.baseUrl = "http://localhost:" + port + "/employee/" + recordValue;
        var employee = Employee.builder()
                .id(1)
                .email("raj@gmail.com")
                .phone(15611675)
                .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                .name("raj")
                .build();
        //Act
        var responseEntity = this.testRestTemplate.getForObject(baseUrl, Employee.class);
        System.out.println("This is response entity id " + responseEntity.getId());
        //Assert
        //assertThat(responseEntity).isEqualTo(employee);
    }

    @Test
    public void getEmployeesList() {
        //Arrange
        final String baseUrl = "http://localhost:" + port + "/employee";
        var employee = Employee.builder()
                .id(1)
                .email("raj@gmail.com")
                .phone(15611675)
                .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                .name("raj")
                .build();
        //Act
        var responseEntity = this.testRestTemplate.getForObject(baseUrl, Employee[].class);
        //Assert
        var responseEmployee = Arrays.stream(responseEntity).filter(x -> x.getId() == 1).findFirst().get().getId();
        System.out.println("this is id  " + responseEmployee);
        assertThat(responseEmployee).isEqualTo(employee.getId());
    }
    //Post a body and test.

    @Test
    public void testPOSTEmployees() {
        //arrange
        final String baseUrl = "http://localhost:" + port + "/employee";
        var employee = Employee.builder()
                .id(5)
                .email("john@gmail.com")
                .name("john")
                .phone(15611675)
                .address(Address.builder().city("chenni").country("india").street("12 street").build())
                .build();
        //act
        var responseEntity = this.testRestTemplate.postForObject(baseUrl,employee, Employee[].class);
        //assert
        var responseEmployee = Arrays.stream(responseEntity).filter(o->o.getId()==5).findFirst().get();
        System.out.println("This is new record"+responseEmployee);
        assertThat(responseEmployee).isEqualTo(employee);

    }


}
