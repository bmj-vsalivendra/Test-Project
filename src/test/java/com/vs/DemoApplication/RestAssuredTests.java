package com.vs.DemoApplication;

import com.vs.DemoApplication.model.Address;
import com.vs.DemoApplication.model.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAssuredTests {

    @Value("${base.url}")
    private String baseUrl;

    @LocalServerPort
    private int port;

    // Rest Builder or BDD style
    @Test
    public void testGetEmployeeByID() {
        given()
                .baseUri(baseUrl + port)
                .basePath("/employee/1")
                .get().then().assertThat().body("name", Matchers.equalTo("raj"));
    }

    @Test
    public void testGETEmployees() {
        //arrange
        var employee = Employee.builder()
                .id(1)
                .email("raj@gmail.com")
                .phone(073433433)
                .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                .name("raj")
                .build();
        //act
        var response = given()
                .baseUri(baseUrl+ port)
                .basePath("/employee/1")
                .get();
        var responseEntity = response.body().as(Employee.class);

        //assert
        assertThat(responseEntity).isEqualTo(employee);

    }

    @Test
    public void testPOSTEmployee() {
        //arrange
        var employee = Employee.builder()
                .id(4)
                .email("raj4@gmail.com")
                .phone(073433433)
                .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                .name("raj4")
                .build();
        //act
        var response = given()
                .baseUri(baseUrl+ port)
                .basePath("/employee")
                .contentType("application/json")
                .body(employee)
                .post();
        var responseEntity = response.body().as(Employee[].class);
        var responseEmployee = Arrays.stream(responseEntity).filter(x-> x.getId()==4).findFirst().get();

        //assert
        System.out.println("This is response "+ responseEmployee);
        assertThat(responseEmployee).isEqualTo(employee);

    }


    @Test
    public void testPUTEmployee() {
        //arrange
        var employee = Employee.builder()
                .id(3)
                .email("raj4@gmail.com")
                .phone(073433433)
                .address(Address.builder().city("auckland").country("NZ").street("12 street").build())
                .name("raj3")
                .build();
        //act
        var response =
                given()
                .baseUri(baseUrl+ port)
                .basePath("/employee/3")
                .contentType("application/json")
                .body(employee)
                .put();
        var responseEntity = response.body().as(Employee.class);
       // var responseEmployee = Arrays.stream(responseEntity).filter(x-> x.getId()==3).findFirst().get();

        //assert
        System.out.println("This is response "+ responseEntity);
        assertThat(responseEntity).isEqualTo(employee);
    }





}
