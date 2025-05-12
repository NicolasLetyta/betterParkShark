package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.repository.*;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

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
    private Address address;
    private Member member1;
    private Member member2;
    private ParkingLot parkingLot1;
    private ParkingLot parkingLot2;
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
        employee = new Employee("name","name","phone","mobile","nm@gmail.com","pass", EmployeeCategory.CONTACT_PERSON,1L);
        employeeRepository.save(employee);
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

        given()
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

        when()
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

        when()
        .get("/allocations/member/" + memberId)
                .then()
                .statusCode(200)
                .body("[0].allocationId", equalTo(1))
                .body("[0].memberId", equalTo(1))
                .body("[0].parkingId", equalTo(1))
                .body("[0].licensePlate", equalTo("plate"));
    }

    @Test
    void testGetAllocationByParkingId(){
        allocationRepository.deleteAll();
        Allocation savedAllocation = allocationRepository.save(new Allocation(1L,"plate",1L));
        Long parkingLotId = savedAllocation.getParkingId();

        when()
                .get("/allocations/parkinglot/" + parkingLotId)
                .then()
                .statusCode(200)
                .body("[0].allocationId", equalTo(1))
                .body("[0].memberId", equalTo(1))
                .body("[0].parkingId", equalTo(1))
                .body("[0].licensePlate", equalTo("plate"));
    }

    @Test
    void testStopAllocation(){
        allocationRepository.deleteAll();
        Allocation savedAllocation = allocationRepository.save(new Allocation(1L,"plate",1L));
        Long allocationId = savedAllocation.getId();

        when()
                .post("/allocations/" +allocationId)
                .then()
                .statusCode(201)
                .body("endTime", notNullValue());
    }

}
