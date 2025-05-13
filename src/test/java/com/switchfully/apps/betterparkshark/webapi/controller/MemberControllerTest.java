package com.switchfully.apps.betterparkshark.webapi.controller;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.EmployeeRepository;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.repository.MembershipLevelRepository;
import com.switchfully.apps.betterparkshark.service.AuthenticationService;
import com.switchfully.apps.betterparkshark.service.MemberService;
import com.switchfully.apps.betterparkshark.service.mapper.AddressMapper;
import com.switchfully.apps.betterparkshark.service.mapper.MemberMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutputLight;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Rollback
public class MemberControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MembershipLevelRepository membershipLevelRepository;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AddressMapper addressMapper;

    @PersistenceContext
    private EntityManager entityManager;

    MembershipLevel bronze;
    MembershipLevel silver;
    MembershipLevel gold;

    Member memberBronze;
    Member memberSilver;
    Member memberGold;

    Address addressMember;

    Employee admin;
    Employee contactPerson;

    Long lastMemberId;



    @BeforeAll
    void setupAll() {
        bronze = membershipLevelRepository.save(new MembershipLevel("bronze",0,0,4));
        silver = membershipLevelRepository.save(new MembershipLevel("silver",10,20,6));
        gold = membershipLevelRepository.save(new MembershipLevel("gold",40,30,24));

        Address address = new Address("Holleywood hills","109C","2140xC","Los Angeles","USA");
        address = addressRepository.save(address);

        admin = employeeRepository.save(new Employee("Jane","Fonda","+314928466392",
                "+314928466392","janefonda@yahoo.com","janefonda", EmployeeCategory.ADMIN, address.getId()));

        contactPerson = employeeRepository.save(new Employee("Jackson","Fonda","+314928466296",
                "+314928466028","jacksonfonda@yahoo.com","jacksonfonda", EmployeeCategory.CONTACT_PERSON, address.getId()));

    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        memberRepository.deleteAll();
        addressRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();

        addressMember = addressRepository.save(new Address("Lamoniere straat","109C","2140","Basel","Belgie"));

        memberBronze = memberRepository.save(new Member("Bert", "Heinz","+32957500123",
                "bertheinz@gmail.com","bertheinz","123XYZ",
                LocalDate.now(),addressMember.getId(),bronze.getId()));

        memberSilver = memberRepository.save(new Member("ernie", "Heinz","+32957500049",
                "ernieheinz@gmail.com","ernieheinz","456ABC",
                LocalDate.now(),addressMember.getId(),silver.getId()));

        memberGold = memberRepository.save(new Member("joe", "Heinz","+32957500826",
                "joeheinz@gmail.com","joeheinz","789DEF",
                LocalDate.now(),addressMember.getId(),gold.getId()));

        lastMemberId = ((Number) entityManager.createNativeQuery("SELECT nextval('member_seq')").getSingleResult()).longValue();
    }

    private String basicAuth(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }

    @Test
    void givenValidPayload_whenRegisterAsMember_thenReturnMemberDtoOutputStatusCreated() throws Exception {
        MemberDtoInput payload = new MemberDtoInput("validName","validSurname","+32459025038",
                "valid@valid.com","password","123XYZS",
                null,silver.getId());

        MemberDtoOutput expectedResult= new MemberDtoOutput(lastMemberId+1,"validName validSurname","+32459025038",
                "valid@valid.com", "123XYZS",LocalDate.now(),
                null,silver.getName());

        MemberDtoOutput response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/members")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDtoOutput.class);

        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void givenInvalidPayload_whenRegisterAsAdmin_thenReturnStatusBadRequest() throws Exception {
        MemberDtoInput payload = new MemberDtoInput("validName","validSurname","+32459025038",
                "invalidEmail","password","123XYZS",
                null,silver.getId());

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/members")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void givenValidAuthToken_whenFindAllMembers_thenReturnListOfMemberDtoOutputLightStatusOk() throws Exception {
        TestTransaction.flagForCommit();
        TestTransaction.end();

        String authToken = basicAuth("janefonda@yahoo.com","janefonda");
        List<MemberDtoOutputLight> expectedResult = memberService.findAllMembers();

        MemberDtoOutputLight[] response = given()
                .header("Authorization", authToken)
                .when()
                .get("/members")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(MemberDtoOutputLight[].class);

        assertThat(response).hasSize(3);
        assertThat(List.of(response)).isEqualTo(expectedResult);
    }

    @Test
    void givenInValidAuthToken_whenFindAllMembers_thenReturnStatusUnauthorized() throws Exception {
        String authToken = basicAuth("booboothefool@yahoo.com","booboothefool");
        given()
                .header("Authorization", authToken)
                .when()
                .get("/members")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void givenValidAuthToken_whenFindMemberBuId_thenReturnMemberDtoOutputStatusOk() throws Exception {
        TestTransaction.flagForCommit();
        TestTransaction.end();

        String authToken = basicAuth("janefonda@yahoo.com","janefonda");
        MemberDtoOutput expectedResult = memberService.findMemberById(memberGold.getId());

        MemberDtoOutput response = given()
                .header("Authorization", authToken)
                .when()
                .get("/members/" + memberGold.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(MemberDtoOutput.class);

        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void givenInValidAuthToken_whenFindMemberById_thenReturnStatusUnauthorized() throws Exception {
        String authToken = basicAuth("booboothefool@yahoo.com","booboothefool");

        given()
                .header("Authorization", authToken)
                .when()
                .get("/members/" + memberGold.getId())
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void givenValidAuthToken_whenupdateMembershipLevel_thenReturnMemberDtoOutputStatusOk() throws Exception {
        TestTransaction.flagForCommit();
        TestTransaction.end();

        Map<String, Long> payload = Map.of("membershipLevel", memberGold.getId());

        String authToken = basicAuth("bertheinz@gmail.com","bertheinz");
        MemberDtoOutput expectedResult = memberService.updateMemberShipLevel(memberBronze,memberGold.getId());

        MemberDtoOutput response = given()
                .header("Authorization", authToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/members/update_membership")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(MemberDtoOutput.class);

        assertThat(response).isEqualTo(expectedResult);
    }

    @Test
    void givenInValidAuthToken_whenupdateMembershipLevel_thenReturnStatusUnauthorized() throws Exception {
        String authToken = basicAuth("booboothefool@yahoo.com","booboothefool");
        Map<String, Long> payload = Map.of("membershipLevel", memberGold.getId());


        given()
                .header("Authorization", authToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .patch("/members/update_membership")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

}
