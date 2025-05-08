package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.repository.MemberProjection;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutput;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MemberMapper {

    public Member inputToMember(MemberDtoInput memberDtoInput , Address address, MembershipLevel membershipLevel) {
        return new Member(memberDtoInput.getFirstName(), memberDtoInput.getLastName(), memberDtoInput.getPhone(),
                memberDtoInput.getEmail(), memberDtoInput.getPassword(), memberDtoInput.getLicensePlate(),
                LocalDateTime.now(),address, membershipLevel);
    }

    public MemberDtoOutput memberToOutput(Member member) {
        return new MemberDtoOutput(member.getId(), member.getName(), member.getLicensePlate(), member.getPhone(),
                member.getEmail(), member.getRegistrationDate());
    }

    public MemberDtoOutput memberToOutput(MemberProjection member) {
        return new MemberDtoOutput(member.getId(), member.getName(), member.getLicensePlate(), member.getPhone(),
                member.getEmail(), member.getRegistrationDate());
    }
}
