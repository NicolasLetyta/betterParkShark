package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MemberMapper {

    public Member inputToMember(MemberDtoInput memberDtoInput , Address address, MembershipLevel membershipLevel) {
        return new Member(memberDtoInput.getFirstName(), memberDtoInput.getLastName(), memberDtoInput.getPhone(),
                memberDtoInput.getEmail(), memberDtoInput.getPassword(), memberDtoInput.getLicensePlate(),
                LocalDateTime.now(),address, membershipLevel);
    }
}
