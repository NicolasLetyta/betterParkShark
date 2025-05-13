package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Division;
import com.switchfully.apps.betterparkshark.domain.Employee;
import com.switchfully.apps.betterparkshark.domain.EmployeeCategory;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.DivisionRepository;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.webapi.dto.DivisionDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.SubDivisionDtoInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
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

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    void setUpPrerequisiteForCreation() {

        addressRepository.save(new Address(
                "Test Street",
                "12",
                "Test Postal Code",
                "Test City",
                "Test Country"
        ));

        employeeRepository.save(new Employee(
                1L,
                "John",
                "Doe",
                "+32123456789",
                "director@ps.com",
                "password",
                EmployeeCategory.DIRECTOR,
                1L
        ));
    }

    @Test
    void testCreateNewDivision() {

        setUpPrerequisiteForCreation();

        DivisionDtoInput inputDTO = new DivisionDtoInput(
                "Test Division",
                "Test Original Name",
                1L
        );

        given()
                .contentType(ContentType.JSON)
                .body(inputDTO)
                .when()
                .post("/divisions")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Division"))
                .body("originalName", equalTo("Test Original Name"))
                .body("directorId", equalTo(1));
    }

    @Test
    void testCreateNewSubDivision() {

        setUpPrerequisiteForCreation();

        divisionRepository.save(new Division(
                "Parent Division",
                "Parent Original Name",
                1L,
                null
        ));

        SubDivisionDtoInput inputDTO = new SubDivisionDtoInput(
                "Test Subdivision",
                "Test Original Name",
                1L,
                1L
        );

        given()
                .contentType(ContentType.JSON)
                .body(inputDTO)
                .when()
                .post("/divisions/subdivision")
                .then()
                .statusCode(201)
                .body("name", equalTo("Test Subdivision"))
                .body("originalName", equalTo("Test Original Name"))
                .body("directorId", equalTo(1));
    }

    @Test
    void testGetAllDivisions() {

        divisionRepository.deleteAll(); // because there's no guarantees about the state of the database depending on the other tests.
        divisionRepository.save(new Division("Division 1", "Original Name 1", 1L, 1L));
        divisionRepository.save(new Division("Division 2", "Original Name 2", 2L, 2L));

        when()
                .get("/divisions")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2)); // Adjust this based on your initial data
    }

    @Test
    void testGetDivisionById() {
        Division savedDivision = divisionRepository.save(new Division("Division 1", "Original Name 1", 1L, null));

        when()
                .get("/divisions/" + savedDivision.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Division 1"))
                .body("originalName", equalTo("Original Name 1"))
                .body("directorId", equalTo(1))
                .body("parentId", equalTo(null));
    }


    @Test
    void testGetDivisionByIdNotFound() {
        when()
                .get("/divisions/999")
                .then()
                .statusCode(400);
    }

}