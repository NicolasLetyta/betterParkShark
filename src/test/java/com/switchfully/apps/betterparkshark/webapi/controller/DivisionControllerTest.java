package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Division;
import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.EmployeeCategory;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.service.AuthenticationService;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.SubDivisionDtoInput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class DivisionControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DivisionRepository divisionRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthenticationService authenticationService;

    Employee admin;

    @BeforeAll
    void setUpAll() {
        addressRepository.deleteAll();
        employeeRepository.deleteAll();

        Address address = new Address("Holleywood hills","109C","2140xC","Los Angeles","USA");
        address = addressRepository.save(address);
        Employee admin = new Employee("Jane","Fonda","+314928466392",
                "+314928466392","janefonda@yahoo.com","janefonda",
                EmployeeCategory.ADMIN, address.getId());
        Employee director = new Employee(
                "John",
                "Doe",
                "+32123456789",
                "+32djdenjdejdfj",
                "director@ps.com",
                "password",
                EmployeeCategory.DIRECTOR,
                address.getId());
        employeeRepository.save(admin);
        employeeRepository.save(director);
        System.out.println("Admin ID: " + admin.getTypeEmployee());
        System.out.println(employeeRepository.findAll());
    }

    @BeforeEach
    void setUp() {
        divisionRepository.deleteAll();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

//    void setUpPrerequisiteForCreation() {
//
//        addressRepository.save(new Address(
//                "Test Street",
//                "12",
//                "Test Postal Code",
//                "Test City",
//                "Test Country"
//        ));
//
//        employeeRepository.save(new Employee(
//                1L,
//                "John",
//                "Doe",
//                "+32123456789",
//                "director@ps.com",
//                "password",
//                EmployeeCategory.DIRECTOR,
//                1L
//        ));
//    }

    @Test
    void testCreateNewDivision() {
        divisionRepository.deleteAll();

        //setUpPrerequisiteForCreation();

        DivisionDtoInput inputDTO = new DivisionDtoInput(
                "Test Division",
                "Test Original Name",
                2L
        );
        System.out.println(employeeRepository.findEmployeeById(1L).getEmail());
        System.out.println(employeeRepository.findAll());

        String authToken = authenticationService.encode("janefonda@yahoo.com", "janefonda");
        System.out.println(authenticationService.authenticateAdmin(authToken).getTypeEmployee());
        given()
                .header("Authorization", authToken)
                .contentType(ContentType.JSON)
                .body(inputDTO)
                .when()
                .post("/divisions")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Division"))
                .body("originalName", equalTo("Test Original Name"))
                .body("directorId", equalTo(2));
    }

    @Test
    void testCreateNewSubDivision() {

        //etUpPrerequisiteForCreation();

        divisionRepository.save(new Division(
                "Parent Division",
                "Parent Original Name",
                2L,
                null
        ));

        SubDivisionDtoInput inputDTO = new SubDivisionDtoInput(
                "Test Subdivision",
                "Test Original Name",
                2L,
                1L
        );

        String authToken = authenticationService.encode("janefonda@yahoo.com", "janefonda");
        given()
                .header("Authorization", authToken)
                .contentType(ContentType.JSON)
                .body(inputDTO)
                .when()
                .post("/divisions/subdivision")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Subdivision"))
                .body("originalName", equalTo("Test Original Name"))
                .body("directorId", equalTo(2));
    }

    @Test
    void testGetAllDivisions() {

        divisionRepository.deleteAll(); // because there's no guarantees about the state of the database depending on the other tests.
        divisionRepository.save(new Division("Division 1", "Original Name 1", 2L, 1L));
        divisionRepository.save(new Division("Division 2", "Original Name 2", 2L, 2L));

        String authToken = authenticationService.encode("janefonda@yahoo.com", "janefonda");
        given()
                .header("Authorization", authToken)
                .when()
                .get("/divisions")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2)); // Adjust this based on your initial data
    }

    @Test
    void testGetDivisionById() {
        Division savedDivision = divisionRepository.save(new Division("Division 1", "Original Name 1", 2L, null));

        String authToken = authenticationService.encode("janefonda@yahoo.com", "janefonda");
        given()
                .header("Authorization", authToken)
                .when()
                .get("/divisions/" + savedDivision.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Division 1"))
                .body("originalName", equalTo("Original Name 1"))
                .body("directorId", equalTo(2))
                .body("parentId", equalTo(null));
    }


    @Test
    void testGetDivisionByIdNotFound() {
        String authToken = authenticationService.encode("janefonda@yahoo.com", "janefonda");
        given()
                .header("Authorization", authToken)
                .when()
                .get("/divisions/999")
                .then()
                .statusCode(400);
    }

}