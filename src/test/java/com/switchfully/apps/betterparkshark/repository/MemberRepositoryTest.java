package com.switchfully.apps.betterparkshark.repository;



import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MembershipLevelRepository membershipLevelRepository;

    private Address address;
    private MembershipLevel bronze;
    private MembershipLevel silver;
    private MembershipLevel gold;
    private Member member;

    @BeforeEach
    void setUp() {
        address = new Address("street","number","2000","city","country");
        addressRepository.save(address);
        bronze = new MembershipLevel("bronze",0,0,4);
        silver = new MembershipLevel("silver",10,20,6);
        gold = new MembershipLevel("gold",40,30,24);
        membershipLevelRepository.save(bronze);
        membershipLevelRepository.save(silver);
        membershipLevelRepository.save(gold);

        member = new Member("name","name","phone","email","pass",
                "plate",LocalDate.now(),1L,1L);
    }

    @Test
    void givenCorrectDomainMember_whenSaveMember_thenReturnMemberFromDatabase() {
        Member result = memberRepository.save(member);

        assertThat(result).isEqualTo(member);
        assertThat(result.getMembershipLevel()).isEqualTo(1);
    }

    @Test
    void givenInvalidDomainMember_whenSaveMember_thenThrowsException() {
        Member invalidMember = new Member("name",null,"phone","email","pass",
                "plate", LocalDate.now(),0L,3L);

        assertThrows(Exception.class, () -> memberRepository.save(invalidMember));
    }

    @Test
    void givenMemberExistsInDatabase_whenFindById_thenReturnMemberFromDatabase() {
        memberRepository.save(member);

        Member result = memberRepository.findById(member.getId()).get();

        assertThat(result).isEqualTo(member);
    }

    @Test
    void givenMultipleMembersInDatabase_whenFindAllMembersProjected_thenReturnAllMembersProjected() {
        Member member1 = new Member("name1","name1","phone1","email1","pass1",
                "plate1",LocalDate.now(),1L,1L);
        Member member2 = new Member("name2","name2","phone2","email2","pass2",
                "plate2",LocalDate.now(),1L,2L);
        Member member3 = new Member("name3","name3","phone3","email3","pass3",
                "plate3",LocalDate.now(),1L,3L);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        List<MemberProjection> result = memberRepository.findAllMembersProjected();
        assertThat(result).hasSize(3);

        assertThat(result.get(0).getEmail()).isEqualTo(member1.getEmail());
        assertThat(result.get(0).getLicensePlate()).isEqualTo(member1.getLicensePlate());
        assertThat(result.get(0).getName()).isEqualTo(member1.getName());
        assertThat(result.get(0).getId()).isEqualTo(member1.getId());
        assertThat(result.get(0).getPhone()).isEqualTo(member1.getPhone());
        assertThat(result.get(0).getRegistrationDate()).isEqualTo(member1.getRegistrationDate());

        assertThat(result.get(1).getEmail()).isEqualTo(member2.getEmail());
        assertThat(result.get(1).getLicensePlate()).isEqualTo(member2.getLicensePlate());
        assertThat(result.get(1).getName()).isEqualTo(member2.getName());
        assertThat(result.get(1).getId()).isEqualTo(member2.getId());
        assertThat(result.get(1).getPhone()).isEqualTo(member2.getPhone());
        assertThat(result.get(1).getRegistrationDate()).isEqualTo(member2.getRegistrationDate());

        assertThat(result.get(2).getEmail()).isEqualTo(member3.getEmail());
        assertThat(result.get(2).getLicensePlate()).isEqualTo(member3.getLicensePlate());
        assertThat(result.get(2).getName()).isEqualTo(member3.getName());
        assertThat(result.get(2).getId()).isEqualTo(member3.getId());
        assertThat(result.get(2).getPhone()).isEqualTo(member3.getPhone());
        assertThat(result.get(2).getRegistrationDate()).isEqualTo(member3.getRegistrationDate());
    }

    @Test
    void whenSavingMembersWithIdenticalUniqueEnforcedFields_thenThrowsException() {
        Member member1 = new Member("name1","name1","phone1","email1","pass1",
                "plate1",LocalDate.now(),1L,1L);
        Member member2 = new Member("name1","name1","phone1","email1","pass1",
                "plate1",LocalDate.now(),1L,1L);

        memberRepository.save(member1);

        assertThrows(Exception.class, () -> {memberRepository.save(member2);
        memberRepository.flush();});
    }












//    @Mock
//    private MemberMapper memberMapper;
//    @Mock
//    private MemberRepository memberRepository;
//    @Mock
//    private AddressMapper addressMapper;
//    @Mock
//    private AddressRepository addressRepository;
//    @Mock
//    private MembershipLevelRepository membershipLevelRepository;
//    @InjectMocks
//    private MemberService memberService;
//
//    private AddressDtoInput addressDtoInput = new AddressDtoInput("street", "1a", "2000", "antwerp", "belgium");
//    private MembershipLevel bronze = new MembershipLevel();
//    private MembershipLevel silver = new MembershipLevel();
//    private MembershipLevel gold = new MembershipLevel();
//    private Address address = new Address("street", "1a", "2000", "antwerp", "belgium");



//    @Test
//    void givenValidMemberInputWithMembership_whenCreateMember_thenReturnMemberDtoOutput() {
//        //GIVEN
//        MemberDtoInput memberDtoInput = new MemberDtoInput("firstname","lastname", 0123,
//                "email", "password", "licenseplate",
//                addressDtoInput,2);
//        Member member = new Member("firstname", "lastname", 0123,
//                "email", "password", "licenseplate",
//                LocalDateTime.now(),address,silver);
//        //EXPECTED RESULT
//        MemberDtoOutput expectedResult = new MemberDtoOutput(1, "lastname", "licenseplate",
//                0123,"email", LocalDateTime.now());
//
//        //STUB
//        when(addressMapper.inputToAddress(addressDtoInput)).thenReturn(address);
//        when(membershipLevelRepository.findById(memberDtoInput.getMemberShipLevel())).thenReturn(silver);
//        when(memberMapper.inputToMember(memberDtoInput,address,silver)).thenReturn(member);
//        when(memberMapper.memberToOutput(member)).thenReturn(expectedResult);
//
//        MemberDtoOutput result = memberService.createMember(memberDtoInput);
//
//        verify(memberMapper, times(1)).inputToMember(memberDtoInput,address,silver);
//        verify(addressMapper, times(1)).inputToAddress(addressDtoInput);
//        verify(membershipLevelRepository, times(1)).findById(memberDtoInput.getMemberShipLevel());
//        verify(memberRepository, times(1)).save(member);
//        verify(addressRepository, times(1)).save(address);
//
//        assertThat(result).isEqualTo(expectedResult);
//
//    }

}
