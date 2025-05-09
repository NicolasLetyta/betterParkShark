package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.repository.MembershipLevelRepository;
import com.switchfully.apps.betterparkshark.service.mapper.AddressMapper;
import com.switchfully.apps.betterparkshark.service.mapper.MemberMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@Rollback
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberServiceTest {

    @Autowired
    private MembershipLevelRepository membershipLevelRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AddressMapper addressMapper;

    @PersistenceContext
    private EntityManager entityManager;


    private Address address;
    private MembershipLevel bronze;
    private MembershipLevel silver;
    private MembershipLevel gold;
    private Member member1;
    private Member member2;
    private Member member3;
    private Member member4;
    private Member member5;
    long lastMemberId;
    long lastAddressId;

    @BeforeAll
    void setupOnce() {
        bronze = membershipLevelRepository.save(new MembershipLevel("bronze",0,0,4));
        silver = membershipLevelRepository.save(new MembershipLevel("silver",10,20,6));
        gold = membershipLevelRepository.save(new MembershipLevel("gold",40,30,24));
    }

    @BeforeEach
    void setUp() {
        address = new Address("street","number","2000","city","country");
        addressRepository.save(address);
        member1 = new Member("name1","name1","phone1","email1","password1","plate1",LocalDate.now(),1L,1L);
        member2 = new Member("name2","name2","phone2","email2","password2","plate2",LocalDate.now(),0L,2L);
        member3 = new Member("name3","name3","phone3","email3","password3","plate3",LocalDate.now(),1L,3L);
        member4 = new Member("name4","name4","phone4","email4","password4","plate4",LocalDate.now(),0L,1L);
        member5 = new Member("name5","name5","phone5","email5","password5","plate5",LocalDate.now(),1L,2L);
        member1 = memberRepository.save(member1);
        member2 = memberRepository.save(member2);
        member3 = memberRepository.save(member3);
        member4 = memberRepository.save(member4);
        member5 = memberRepository.save(member5);

        lastMemberId = ((Number) entityManager.createNativeQuery("SELECT nextval('member_seq')").getSingleResult()).longValue();
        lastAddressId= ((Number) entityManager.createNativeQuery("SELECT nextval('address_seq')").getSingleResult()).longValue();
    }

    @Test
    void givenMemberDtoInputWithAddressDtoInputAndMemberShipLevelId_whenRegisterAsMember_thenReturnMemberDtoOutput() {
        AddressDtoInput addressDtoInput = new AddressDtoInput("street","number","2000",
                "city","country");
        AddressDtoOutput addressDtoOutput = new AddressDtoOutput(lastAddressId+1,"street","number",
                "2000","city","country");

        MemberDtoInput memberDtoInput = new MemberDtoInput("nicolas","bluj","phone",
                "email","password","plate",addressDtoInput,bronze.getId());
        long newId = lastMemberId + 1;
        MemberDtoOutput expectedResult = new MemberDtoOutput(newId,"nicolas bluj","phone",
                "email","plate",LocalDate.now(),addressDtoOutput,"bronze");

        MemberDtoOutput result = memberService.registerAsMember(memberDtoInput);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void givenMemberDtoInputWithAddressDtoInputIsNullAndMemberShipLevelId_whenRegisterAsMember_thenReturnMemberDtoOutput() {
        MemberDtoInput memberDtoInput = new MemberDtoInput("nicolas","bluj","phone",
                "email","password","plate", null, bronze.getId());
        long newId = lastMemberId + 1;
        MemberDtoOutput expectedResult = new MemberDtoOutput(newId,"nicolas bluj","phone",
                "email","plate",LocalDate.now(),null,"bronze");

        MemberDtoOutput result = memberService.registerAsMember(memberDtoInput);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void givenMemberDtoInputWithAddressDtoInputIsNullAndMemberShipLevelIsIsNull_whenRegisterAsMember_thenReturnMemberDtoOutputWithBronzeLevel() {
        MemberDtoInput memberDtoInput = new MemberDtoInput("nicolas","bluj","phone",
                "email","password","plate", null,null);
        long newId = lastMemberId + 1;
        MemberDtoOutput expectedResult = new MemberDtoOutput(newId,"nicolas bluj","phone",
                "email","plate",LocalDate.now(),null,"bronze");

        MemberDtoOutput result = memberService.registerAsMember(memberDtoInput);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void givenMemberIdWithAddressExists_whenFindById_thenReturnMemberDto() {
        long memberId = member3.getId();
        AddressDtoOutput addressDtoOutput = addressMapper.addressToOutput(address);
        MemberDtoOutput expectedResult = new MemberDtoOutput(memberId,
                member3.getName(),
                member3.getPhone(),
                member3.getEmail(),
                member3.getLicensePlate(),
                member3.getRegistrationDate(),
                addressDtoOutput,
                "gold");

        MemberDtoOutput result = memberService.findMemberById(memberId);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void givenMemberIdWithoutAddressExists_whenFindById_thenReturnMemberDto() {
        long memberId = member2.getId();
        MemberDtoOutput expectedResult = new MemberDtoOutput(memberId,
                member2.getName(),
                member2.getPhone(),
                member2.getEmail(),
                member2.getLicensePlate(),
                member2.getRegistrationDate(),
                null,
                "silver");

        MemberDtoOutput result = memberService.findMemberById(memberId);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void whenFindAllMembers_thenReturnListOfMemberDtoOutputLight() {
        List<MemberDtoOutputLight> result = memberService.findAllMembers();

        assertThat(result).hasSize(5);
        assertThat(result.get(0).getEmail()).isEqualTo(member1.getEmail());
        assertThat(result.get(1).getEmail()).isEqualTo(member2.getEmail());
        assertThat(result.get(2).getEmail()).isEqualTo(member3.getEmail());
        assertThat(result.get(3).getEmail()).isEqualTo(member4.getEmail());
        assertThat(result.get(4).getEmail()).isEqualTo(member5.getEmail());
    }

    @Test
    void givenValidMemberShipLevelId_whenUpdateMemberShipLevel_thenReturnUpdatedMemberDto() {
        long newMemberShipLevelId = gold.getId();
        long memberId = member4.getId();
        AddressDtoOutput addressDto = addressMapper.addressToOutput(address);
        MemberDtoOutput expectedResult = new MemberDtoOutput(memberId, member4.getName(), member4.getPhone(),
                member4.getEmail(), member4.getLicensePlate(), member4.getRegistrationDate(), addressDto, gold.getName());

        MemberDtoOutput result = memberService.updateMemberShipLevel(memberId, newMemberShipLevelId);

        assertThat(result).isEqualTo(expectedResult);
    }

}
