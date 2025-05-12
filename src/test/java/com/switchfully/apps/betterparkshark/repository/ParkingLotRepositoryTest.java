package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.*;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class ParkingLotRepositoryTest {

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
    void givenCorrectParkingLot_whenSave_thenReturnParkingLotFromDatabase() {
        ParkingLot parkingLot = new ParkingLot("test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L);
        ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);

        assertThat(savedParkingLot).isNotNull();
        assertThat(savedParkingLot.getId()).isNotNull();
        assertThat(savedParkingLot.getName()).isEqualTo("test");
        assertThat(savedParkingLot.getSpaceAvailable()).isEqualTo(250);
        assertThat(savedParkingLot.getDivisionId()).isEqualTo(1);
    }

    @Test
    void givenNullAtNonNullable_whenSave_thenThrowsException() {
        ParkingLot nullName = new ParkingLot(null, LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L);
        ParkingLot nullCategory = new ParkingLot("Test", null,250,2.5,1L,1L,1L);
        ParkingLot nullContactPersonId = new ParkingLot("Test", LotCategory.GROUND_BUILDING,250,2.5,null,1L,1L);
        ParkingLot nullAddressId = new ParkingLot("Test", LotCategory.GROUND_BUILDING,250,2.5,1L,null,1L);
        ParkingLot nullDivisionId = new ParkingLot("Test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,null);

        assertThrows(Exception.class, () -> parkingLotRepository.save(nullName));
        assertThrows(Exception.class, () -> parkingLotRepository.save(nullCategory));
        assertThrows(Exception.class, () -> parkingLotRepository.save(nullContactPersonId));
        assertThrows(Exception.class, () -> parkingLotRepository.save(nullAddressId));
        assertThrows(Exception.class, () -> parkingLotRepository.save(nullDivisionId));
    }

    @Test
    void givenParkingLotsExistInDatabase_whenFindAll_thenReturnListOfParkingLotsNotTooMuchDetails() {
        parkingLotRepository.deleteAll();
        ParkingLot savedParkingLot1 = parkingLotRepository.save(new ParkingLot("test", LotCategory.GROUND_BUILDING,250,2.5,1L,1L,1L));
        ParkingLot savedParkingLot2 = parkingLotRepository.save(new ParkingLot("test2", LotCategory.GROUND_BUILDING,250,2.5,1L,2L,1L));

        List<ParkingLot> parkingLots = parkingLotRepository.findAll();

        assertThat(parkingLots).isNotNull();
        assertThat(parkingLots.size()).isEqualTo(2);
        //add check number of fields present? 
    }

}
