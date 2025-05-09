package com.switchfully.apps.betterparkshark.service;



import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.repository.MembershipLevelRepository;
import com.switchfully.apps.betterparkshark.service.mapper.AddressMapper;
import com.switchfully.apps.betterparkshark.service.mapper.MemberMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    void createMember() {
        //AddressDtoInput addressDtoInput = new AddressDtoInput("street","")
        //MemberDtoInput memberDtoInput = new MemberDtoInput()
        Member member = new Member("name","name","phone","email","pass",
                "plate",LocalDateTime.now(),1,1);
        long id = member.getId();
        Member result = memberRepository.save(member);
        Address test = addressRepository.findById(1);
        System.out.println(test);

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
