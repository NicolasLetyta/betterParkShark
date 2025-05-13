package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
public class AllocationRepositoryTest {

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

    @BeforeAll
    void setUp() {
        memberRepository.deleteAll();
        addressRepository.deleteAll();
        divisionRepository.deleteAll();
        employeeRepository.deleteAll();
        membershipLevelRepository.deleteAll();
        parkingLotRepository.deleteAll();
        allocationRepository.deleteAll();
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
    void givenCorrectAllocation_whenSave_thenReturnAllocationFromDatabase() {
        Allocation allocation = new Allocation(
                member1.getId(),"plate",parkingLot1.getId()
        );

        Allocation savedAllocation = allocationRepository.save(allocation);

        assertThat(savedAllocation).isNotNull();
        assertThat(savedAllocation.getId()).isNotNull();
        assertThat(savedAllocation.getParkingId()).isEqualTo(allocation.getParkingId());
        assertThat(savedAllocation.getMemberId()).isEqualTo(allocation.getMemberId());
        assertThat(savedAllocation.getLicensePlate()).isEqualTo(allocation.getLicensePlate());
    }

    @Test
    void givenNullAtNonNullable_whenSave_thenThrowsException() {
        Allocation nullMemberId = new Allocation(
                null,"plate",1L
        );
        Allocation nullPlate = new Allocation(
                1L,null,1L
        );
        Allocation nullParkingId = new Allocation(
                1L,"plate",null
        );

        assertThrows(Exception.class, () -> allocationRepository.save(nullMemberId));
        assertThrows(Exception.class, () -> allocationRepository.save(nullPlate));
        assertThrows(Exception.class, () -> allocationRepository.save(nullParkingId));
    }

    @Test
    void givenAllocationExistInDatabase_whenFindAll_thenReturnListOfAllocations() {
        allocationRepository.deleteAll();
        Allocation savedAllocation1 = allocationRepository.save(new Allocation(1L,"plate",parkingLot1.getId()));
        Allocation savedAllocation2 = allocationRepository.save(new Allocation(2L,"plate2",parkingLot2.getId()));


        List<Allocation> allocations = allocationRepository.findAll();

        assertThat(allocations).isNotNull();
        assertThat(allocations.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void givenMemberId_whenFindByMemberId_thenReturnAllocationOfThatMember() {
        allocationRepository.deleteAll();
        Allocation savedAllocation1 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot1.getId()));
        Allocation savedAllocation2 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot2.getId()));
        System.out.println(savedAllocation1);
        System.out.println(allocationRepository.findAll());
        List<Allocation> allocations = allocationRepository.findByMemberId(member1.getId());
        System.out.println(allocations);
        assertThat(allocations).isNotNull();
        assertThat(allocations.size()).isEqualTo(2);
    }

    @Test
    void givenMemberId_whenFindByMemberIdAndEndTimeIsNull_thenReturnActiveAllocation(){
        allocationRepository.deleteAll();
        Allocation savedAllocation1 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot1.getId()));
        Allocation savedAllocation2 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot2.getId()));
        savedAllocation1.setEndTime(LocalDateTime.now().withNano(0));

        allocationRepository.save(savedAllocation1);

        List<Allocation> allocationsActive = allocationRepository.findByMemberIdAndEndTimeIsNull(member1.getId());

        assertThat(allocationsActive).isNotNull();
        assertThat(allocationsActive.size()).isEqualTo(1);
    }

    @Test
    void givenMemberId_whenFindByMemberIdAndEndTimeIsNotNull_thenReturnActiveAllocation(){
        allocationRepository.deleteAll();
        Allocation savedAllocation1 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot1.getId()));
        Allocation savedAllocation2 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot2.getId()));
        savedAllocation1.setEndTime(LocalDateTime.now().withNano(0));

        allocationRepository.save(savedAllocation1);

        List<Allocation> allocationsStopped = allocationRepository.findByMemberIdAndEndTimeIsNotNull(member1.getId());

        assertThat(allocationsStopped).isNotNull();
        assertThat(allocationsStopped.size()).isEqualTo(1);
    }

    @Test
    void givenParkingId_whenFindByParkingId_thenReturnAllocationOfThatParking() {
        allocationRepository.deleteAll();
        Allocation savedAllocation1 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot1.getId()));
        Allocation savedAllocation2 = allocationRepository.save(new Allocation(member2.getId(),"plate",parkingLot1.getId()));

        List<Allocation> allocations = allocationRepository.findByParkingId(parkingLot1.getId());

        assertThat(allocations).isNotNull();
        assertThat(allocations.size()).isEqualTo(2);
    }

    @Test
    void givenParkingId_whenFindByParkingIdAndEndTimeNull_thenReturnAllocationOfThatParking() {
        allocationRepository.deleteAll();
        Allocation savedAllocation1 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot1.getId()));
        Allocation savedAllocation2 = allocationRepository.save(new Allocation(member2.getId(),"plate",parkingLot1.getId()));
        savedAllocation1.setEndTime(LocalDateTime.now().withNano(0));

        allocationRepository.save(savedAllocation1);

        List<Allocation> allocationsActive = allocationRepository.findByParkingIdAndEndTimeIsNull(parkingLot1.getId());

        assertThat(allocationsActive).isNotNull();
        assertThat(allocationsActive.size()).isEqualTo(1);
    }

    @Test
    void givenParkingId_whenFindByParkingIdAndEndTimeNotNull_thenReturnAllocationOfThatParking() {
        allocationRepository.deleteAll();
        Allocation savedAllocation1 = allocationRepository.save(new Allocation(member1.getId(),"plate",parkingLot1.getId()));
        Allocation savedAllocation2 = allocationRepository.save(new Allocation(member2.getId(),"plate",parkingLot1.getId()));
        savedAllocation1.setEndTime(LocalDateTime.now().withNano(0));

        allocationRepository.save(savedAllocation1);

        List<Allocation> allocationsStopped = allocationRepository.findByParkingIdAndEndTimeIsNotNull(parkingLot1.getId());

        assertThat(allocationsStopped).isNotNull();
        assertThat(allocationsStopped.size()).isEqualTo(1);
    }
}
