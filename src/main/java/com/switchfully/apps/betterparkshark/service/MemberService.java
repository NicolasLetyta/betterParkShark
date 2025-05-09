package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.MemberProjection;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.repository.MembershipLevelRepository;
import com.switchfully.apps.betterparkshark.service.mapper.AddressMapper;
import com.switchfully.apps.betterparkshark.service.mapper.MemberMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoInput;
import com.switchfully.apps.betterparkshark.webapi.dto.MemberDtoOutput;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MembershipLevelRepository membershipLevelRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    public MemberService (MemberRepository memberRepository,
                          MemberMapper memberMapper,
                          MembershipLevelRepository membershipLevelRepository,
                          AddressMapper addressMapper,
                          AddressRepository addressRepository) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
        this.membershipLevelRepository = membershipLevelRepository;
        this.addressMapper = addressMapper;
        this.addressRepository = addressRepository;
    }

    public Member findById(long id) {
        return memberRepository.findById(id);
    }

    public List<MemberDtoOutput> findAllMembers() {
        return memberRepository.findAllMembersProjected().stream()
                .map(memberMapper::memberToOutput)
                .collect(Collectors.toList());
    }

    public MemberDtoOutput createMember(MemberDtoInput memberDtoInput) {
        MembershipLevel membershipLevel = setMemberShipLevelToBronzeWhenNull(memberDtoInput);

        Address address = addressMapper.inputToAddress(memberDtoInput.getAddressDtoInput());
        addressRepository.save(address);

        Member member = memberMapper.inputToMember(memberDtoInput, address,membershipLevel);
        memberRepository.save(member);

        return memberMapper.memberToOutput(member);
    }

    public MemberDtoOutput updateMemberSipLevel(long memberId, long memberShipLevelId) {
        Member member = memberRepository.findById(memberId);
        member.setMembershipLevel(memberShipLevelId);
        memberRepository.save(member);
        return memberMapper.memberToOutput(member);
    }

    private MembershipLevel setMemberShipLevelToBronzeWhenNull(MemberDtoInput memberDtoInput) {
        if(memberDtoInput.getMemberShipLevel() == null)
        {
            return membershipLevelRepository.findById(1);
        }else {
            return membershipLevelRepository.findById(memberDtoInput.getMemberShipLevel());
        }
    }

}
