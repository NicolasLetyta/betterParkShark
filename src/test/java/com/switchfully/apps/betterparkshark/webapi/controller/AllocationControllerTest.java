package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.repository.*;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class AllocationControllerTest {

    @LocalServerPort
    private int port;


    @Autowired
    private AllocationRepository allocationRepository;
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
    private Member member1;
    private Member member2;
    private ParkingLot parkingLot1;
    private ParkingLot parkingLot2;
    private Division division;
    private MembershipLevel bronze;


    @BeforeAll
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
//        memberRepository.deleteAll();
//        addressRepository.deleteAll();
//        divisionRepository.deleteAll();
//        employeeRepository.deleteAll();
//        membershipLevelRepository.deleteAll();
//        parkingLotRepository.deleteAll();
//        allocationRepository.deleteAll();
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
        employee = new Employee("name","name","phone","mobile","contact@gmail.com","pass", EmployeeCategory.CONTACT_PERSON,1L);
        admin = new Employee("name","name","phone","mobile","admin@gmail.com","pass", EmployeeCategory.CONTACT_PERSON,1L);
        employeeRepository.save(employee);
        employeeRepository.save(admin);
        division = new Division("Division 1", "Original Name 1", 1L, 1L);
        divisionRepository.save(division);
        parkingLot1 = new ParkingLot("test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L);
        parkingLot2 = new ParkingLot("test2", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L);
        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);
    }

    @Test
    void testCreateNewAllocation() {
        AllocationDtoInput allocation = new AllocationDtoInput(
                1L,"plate",1L
        );
        String authToken = basicAuth("member1@gmail.com","pass");


        given()
                .header("Authorization", authToken)
                .contentType("application/json")
                .body(allocation)
                .when()
                .post("/allocations")
                .then()
                .statusCode(201)
                .body("allocationId", equalTo(1))
                .body("memberId", equalTo(1))
                .body("parkingId", equalTo(1))
                .body("licensePlate", equalTo("plate"))
                .body("endTime", equalTo(null));
    }

    @Test
    void testGetAllAllocations() {
        allocationRepository.deleteAll();
        allocationRepository.save(new Allocation(1L,"plate",1L));
        allocationRepository.save(new Allocation(2L,"plate2",2L));
        String authToken = basicAuth("admin@gmail.com","pass");


        given()
                .header("Authorization", authToken)
                .when()
                .get("/allocations")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));


    }

    @Test
    void testGetAllocationByMemberId(){
        allocationRepository.deleteAll();
        Allocation savedAllocation = allocationRepository.save(new Allocation(1L,"plate",1L));
        Long memberId = savedAllocation.getMemberId();
        String authToken = basicAuth("admin@gmail.com","pass");

        given()
                .header("Authorization", authToken)
                .when()
                .get("/allocations/member/" + memberId)
                .then()
                .statusCode(200)
                .body("[0].allocationId", equalTo(savedAllocation.getId().intValue()))
                .body("[0].memberId", equalTo(memberId.intValue()))
                .body("[0].parkingId", equalTo(savedAllocation.getParkingId().intValue()))
                .body("[0].licensePlate", equalTo("plate"));
    }

    @Test
    void testGetAllocationByParkingId(){
        allocationRepository.deleteAll();
        Allocation savedAllocation = allocationRepository.save(new Allocation(1L,"plate",1L));
        Long parkingLotId = savedAllocation.getParkingId();
        String authToken = basicAuth("admin@gmail.com","pass");

        given()
                .header("Authorization", authToken)
                .when()
                .get("/allocations/parkinglot/" + parkingLotId)
                .then()
                .statusCode(200)
                .body("[0].allocationId", equalTo(savedAllocation.getId().intValue()))
                .body("[0].memberId", equalTo(savedAllocation.getMemberId().intValue()))
                .body("[0].parkingId", equalTo(parkingLotId.intValue()))
                .body("[0].licensePlate", equalTo("plate"));
    }

    @Test
    void testStopAllocation(){
        allocationRepository.deleteAll();
        Allocation savedAllocation = allocationRepository.save(new Allocation(1L,"plate",1L));
        Long allocationId = savedAllocation.getId();
        String authToken = basicAuth("member1@gmail.com","pass");
        System.out.println(allocationId);
        System.out.println(savedAllocation);
        given()
                .header("Authorization", authToken)
                .when()
                .post("/allocations/" +allocationId)
                .then()
                .statusCode(200)
                .body("endTime", notNullValue());
    }


    private String basicAuth(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }


}
