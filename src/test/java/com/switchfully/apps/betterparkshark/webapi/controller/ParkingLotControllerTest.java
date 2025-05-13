package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.repository.*;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoInput;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.Base64;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ParkingLotControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DivisionRepository divisionRepository;
    @Autowired
    private MembershipLevelRepository membershipLevelRepository;

    private Employee employee;
    private Employee admin;
    private Address address;
    private AddressDtoInput addressDto;
    private Member member1;
    private Member member2;
    private Division division;
    private MembershipLevel bronze;

    @BeforeAll
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        memberRepository.deleteAll();
        addressRepository.deleteAll();
        divisionRepository.deleteAll();
        employeeRepository.deleteAll();
        membershipLevelRepository.deleteAll();
        parkingLotRepository.deleteAll();
        address = new Address("street","number","2000","city","country");
        addressRepository.save(address);
        bronze = new MembershipLevel("bronze",0,0,4);
        membershipLevelRepository.save(bronze);
        member1 = new Member("name","name","phone","member1@gmail.com","pass",
                "plate", LocalDate.now(),1L,1L);
        member2 = new Member("name","name","phone","member2@gmail.com","pass",
                "plate2", LocalDate.now(),1L,1L);
        memberRepository.save(member1);
        memberRepository.save(member2);
        employee = new Employee("name","name","mobile","phone","nm@gmail.com","pass", EmployeeCategory.CONTACT_PERSON,1L);
        admin = new Employee("name","name","phone","mobile","admin@gmail.com","pass", EmployeeCategory.ADMIN,1L);
        employeeRepository.save(employee);
        employeeRepository.save(admin);
        division = new Division("Division 1", "Original Name 1", 1L, 1L);
        divisionRepository.save(division);
    }

    @Test
    void testCreateNewParkingLot() {
        addressDto = new AddressDtoInput("street","number","2000","city","country");
        ParkingLotDtoInput parkingLotDtoInput = new ParkingLotDtoInput("ParkingLot","GROUND_BUILDING", 250, 2.5, addressDto,1L,1L );
        String authToken = basicAuth("admin@gmail.com","pass");

        given()
                .header("Authorization", authToken)
                .contentType("application/json")
                .body(parkingLotDtoInput)
                .when()
                .post("/parking_lots")
                .then()
                .statusCode(201)
                .body("name",equalTo("ParkingLot"))
                .body("category",equalTo(LotCategory.GROUND_BUILDING.name()))
                .body("employeePhone", equalTo("phone"))
                .body("employeeFirstName", equalTo("name"))
                .body("addressParkingLot.street", equalTo("street"))
                .body("addressParkingLot.streetNumber", equalTo("number"));

    }

    @Test
    void testGetAllParkingLots() {

        parkingLotRepository.deleteAll();
        ParkingLot parkingLot1 = new ParkingLot("test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L);
        ParkingLot parkingLot2 = new ParkingLot("test2", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L);
        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);
        String authToken = basicAuth("admin@gmail.com","pass");

        given()
                .header("Authorization", authToken)
                .when()
                .get("/parking_lots")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("[0].id", equalTo(parkingLot1.getId().intValue()))
                .body("[0]", not(hasKey("category"))); // check if only small dto is called
    }

    @Test
    void testGetParkingLotById() {
        parkingLotRepository.deleteAll();
        ParkingLot parkingLot1 = new ParkingLot("test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L);
        parkingLotRepository.save(parkingLot1);
        String authToken = basicAuth("admin@gmail.com","pass");

        given()
                .header("Authorization", authToken)
                .when()
                .get("/parking_lots/"+parkingLot1.getId().intValue())
                .then()
                .statusCode(200)
                .body("id", equalTo(parkingLot1.getId().intValue()))
                .body("name", equalTo("test"))
                .body("category", equalTo(LotCategory.GROUND_BUILDING.name()))
                .body("employeePhone", equalTo("phone"))
                .body("employeeFirstName", equalTo("name"))
                .body("addressParkingLot.street", equalTo("street"))
                .body("addressParkingLot.streetNumber", equalTo("number"));
    }

    private String basicAuth(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }

}
