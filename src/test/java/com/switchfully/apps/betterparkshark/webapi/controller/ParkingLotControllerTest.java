package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.repository.*;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.ParkingLotDtoInput;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;

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
    private Address address;
    private AddressDtoInput addressDto;
    private Member member1;
    private Member member2;
    private Division division;
    private MembershipLevel bronze;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        address = new Address("street","number","2000","city","country");
        addressRepository.save(address);
        bronze = new MembershipLevel("bronze",0,0,4);
        membershipLevelRepository.save(bronze);
        member1 = new Member("name","name","phone","mn@gmail.com","pass",
                "plate", LocalDate.now(),1L,1L);
        member2 = new Member("name","name","phone","member2@gmail.com","pass",
                "plate2", LocalDate.now(),1L,1L);
        memberRepository.save(member1);
        memberRepository.save(member2);
        employee = new Employee("name","name","mobile","phone","nm@gmail.com","pass", EmployeeCategory.CONTACT_PERSON,1L);
        employeeRepository.save(employee);
        division = new Division("Division 1", "Original Name 1", 1L, 1L);
        divisionRepository.save(division);
    }

    @Test
    void testCreateNewParkingLot() {
        addressDto = new AddressDtoInput("street","number","2000","city","country");
        ParkingLotDtoInput parkingLotDtoInput = new ParkingLotDtoInput("ParkingLot","GROUND_BUILDING", 250, 2.5, addressDto,1L,1L );

        given()
                .contentType("application/json")
                .body(parkingLotDtoInput)
                .when()
                .post("/parking_lots")
                .then()
                .statusCode(201)
                .body("id",equalTo(1))
                .body("name",equalTo("ParkingLot"))
                .body("category",equalTo(LotCategory.GROUND_BUILDING))
                .body("employeePhone", equalTo("phone"))
                .body("employeeFirstName", equalTo("name"))
                .body("parkingLotAddress.street", equalTo("street"))
                .body("parkingLotAddress.number", equalTo("number"));

    }

    @Test
    void testGetAllParkingLots() {

        parkingLotRepository.deleteAll();
        parkingLotRepository.save(new ParkingLot("test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L));
        parkingLotRepository.save(new ParkingLot("test2", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L));

        when()
                .get("/parking_lots")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("[0].id", equalTo(1))
                .body("[0]", not(hasKey("category"))); // check if only small dto is called
    }

    @Test
    void testGetParkingLotById() {
        parkingLotRepository.deleteAll();
        parkingLotRepository.save(new ParkingLot("test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L));

        when()
                .get("/parking_lots/1")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("id", equalTo(1))
                .body("name", equalTo("test"))
                .body("category", equalTo(LotCategory.GROUND_BUILDING))
                .body("employeePhone", equalTo("phone"))
                .body("employeeFirstName", equalTo("name"))
                .body("parkingLotAddress.street", equalTo("street"))
                .body("parkingLotAddress.number", equalTo("number"));
    }


}
