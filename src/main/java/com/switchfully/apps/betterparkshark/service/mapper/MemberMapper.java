package com.switchfully.apps.betterparkshark.service.mapper;

import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.repository.MemberProjection;
import com.switchfully.apps.betterparkshark.webapi.dto.AddressDtoOutput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutputLight;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutput;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public Member inputToMember(MemberDtoInput memberDtoInput, Long address, long membershipLevel) {
        LocalDate registrationDate = LocalDate.now();
        return new Member(memberDtoInput.getFirstName(), memberDtoInput.getLastName(), memberDtoInput.getPhone(),
                memberDtoInput.getEmail(), memberDtoInput.getPassword(), memberDtoInput.getLicensePlate(),
                registrationDate,address, membershipLevel);
    }

    public MemberDtoOutput memberToOutput(Member member, AddressDtoOutput address, String membershipLevel) {
        return new MemberDtoOutput(member.getId(),
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                member.getLicensePlate(),
                member.getRegistrationDate(),
                address,
                membershipLevel);
    }

    public MemberDtoOutputLight projectedMemberToOutputLight(MemberProjection memberProjection) {
        return new MemberDtoOutputLight(memberProjection.getId(),
                memberProjection.getName(),
                memberProjection.getLicensePlate(),
                memberProjection.getPhone(),
                memberProjection.getEmail(),
                memberProjection.getRegistrationDate());
    }

    public List<MemberDtoOutputLight> projectedMembersToOutputLight(List<MemberProjection> memberList) {
        return memberList.stream()
                .map(this::projectedMemberToOutputLight)
                .collect(Collectors.toList());
    }
}
