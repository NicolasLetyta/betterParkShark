package com.switchfully.apps.betterparkshark.repository;



import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private MembershipLevelRepository membershipLevelRepository;

    private Address address;
    private MembershipLevel bronzeMembership;
    private MembershipLevel silverMembership;
    private MembershipLevel goldMembership;

    @BeforeEach
    void setUp() {
        address = new Address("street","number","2000","city","country");
        addressRepository.save(address);
        bronzeMembership = new MembershipLevel("bronze",0,0,4);
        silverMembership = new MembershipLevel("silver",10,20,6);
        goldMembership = new MembershipLevel("gold",40,30,24);
        membershipLevelRepository.save(bronzeMembership);
        membershipLevelRepository.save(silverMembership);
        membershipLevelRepository.save(goldMembership);
    }

    @Test
    void createMember() {
        Member member = new Member("name","name","phone","email","pass",
                "plate",LocalDateTime.now(),1,1);
        long id = member.getId();
        Member result = memberRepository.save(member);

        assertThat(result.getId()).isEqualTo(1);
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
