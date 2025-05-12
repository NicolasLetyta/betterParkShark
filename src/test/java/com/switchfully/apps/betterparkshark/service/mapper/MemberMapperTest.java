package com.switchfully.apps.betterparkshark.service.mapper;


import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberMapperTest {

    private AddressMapper addressMapper;
    private MemberMapper memberMapper = new MemberMapper(addressMapper);

    Address address;
    MembershipLevel bronze;
    MembershipLevel silver;
    MembershipLevel gold;
    @BeforeEach
    public void setUp() {
        address = new Address("street1", "number", "postalcode", "city", "country");
        bronze = new MembershipLevel("bronze",0,0,4);
        silver = new MembershipLevel("silver",10,20,6);
        gold = new MembershipLevel("gold",40,30,24);
    }

    @Test
    void givenValidFullMemberDtoInput_whenMapToMember_thenReturnMember() {
        AddressDtoInput addressDtoInput = new AddressDtoInput("street", "number", "postalcode", "city", "country");
        MemberDtoInput input = new MemberDtoInput("name","name","phone","email","password","plate",addressDtoInput,1L);

        Member expectedResult = new Member("name","name","phone","email","password","plate", LocalDate.now(),1L,1L);

        Member result = memberMapper.inputToMember(input,1L,1);

        assertThat(result).isEqualTo(expectedResult);
        assertThat(result.getName()).isEqualTo(expectedResult.getName());
        assertThat(result.getPhone()).isEqualTo(expectedResult.getPhone());
        assertThat(result.getEmail()).isEqualTo(expectedResult.getEmail());
        assertThat(result.getPassword()).isEqualTo(expectedResult.getPassword());
        assertThat(result.getLicensePlate()).isEqualTo(expectedResult.getLicensePlate());
        assertThat(result.getRegistrationDate()).isEqualTo(expectedResult.getRegistrationDate());
        assertThat(result.getAddressId()).isEqualTo(expectedResult.getAddressId());
        assertThat(result.getMembershipLevelId()).isEqualTo(expectedResult.getMembershipLevelId());

    }

    @Test
    void givenMemberDtoInputWithAddressDtoInputNull_whenMapToMember_thenReturnMemberWithoutAddressId() {
        AddressDtoInput addressDtoInput = null;
        MemberDtoInput input = new MemberDtoInput("name","name","phone","email","password","plate",addressDtoInput,1L);
        Member expectedResult = new Member("name","name","phone","email","password","plate", LocalDate.now(), 0L,1L);

        Member result = memberMapper.inputToMember(input,0L,1);

        assertThat(result).isEqualTo(expectedResult);
        assertThat(result.getName()).isEqualTo(expectedResult.getName());
        assertThat(result.getPhone()).isEqualTo(expectedResult.getPhone());
        assertThat(result.getEmail()).isEqualTo(expectedResult.getEmail());
        assertThat(result.getPassword()).isEqualTo(expectedResult.getPassword());
        assertThat(result.getLicensePlate()).isEqualTo(expectedResult.getLicensePlate());
        assertThat(result.getRegistrationDate()).isEqualTo(expectedResult.getRegistrationDate());
        assertThat(result.getAddressId()).isEqualTo(expectedResult.getAddressId());
        assertThat(result.getMembershipLevelId()).isEqualTo(expectedResult.getMembershipLevelId());
    }



}
