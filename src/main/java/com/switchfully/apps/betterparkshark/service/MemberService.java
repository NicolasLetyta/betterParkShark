package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.domain.Address;
import com.switchfully.apps.betterparkshark.domain.Member;
import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import com.switchfully.apps.betterparkshark.exception.InvalidInputException;
import com.switchfully.apps.betterparkshark.repository.AddressRepository;
import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.repository.MembershipLevelRepository;
import com.switchfully.apps.betterparkshark.service.mapper.AddressMapper;
import com.switchfully.apps.betterparkshark.service.mapper.MemberMapper;
import com.switchfully.apps.betterparkshark.webapi.dto.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.switchfully.apps.betterparkshark.utility.Validation.validateArgument;
import static com.switchfully.apps.betterparkshark.utility.Validation.validateNonBlank;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MembershipLevelRepository membershipLevelRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    private final long defaultMemberShipLevelId = 1L;

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

    public MemberDtoOutput registerAsMember(MemberDtoInput memberDtoInput) {
        validateMemberDtoInput(memberDtoInput);

        MembershipLevel membershipLevel = setMemberShipLevelToBronzeWhenNull(memberDtoInput);
        Address address = getAddressFromInput(memberDtoInput.getAddressDtoInput());
        Long addressId = getIdFromAddress(address);

        Member member = memberMapper.inputToMember(memberDtoInput, addressId,membershipLevel.getId());
        memberRepository.save(member);

        AddressDtoOutput addressDto = getDtoFromAddress(address);
        return memberMapper.memberToOutput(member,addressDto,membershipLevel.getName());
    }

    public MemberDtoOutput findMemberById(Long id){
        validateArgument(id,"Member not found in repository", i->!memberRepository.existsById(i),InvalidInputException::new);
        Member member = memberRepository.findById(id).get();

        Address address = addressRepository.findById(member.getAddress()).orElseGet(()->null);
        AddressDtoOutput addressDto = getDtoFromAddress(address);
        MembershipLevel membershipLevel = membershipLevelRepository.findById(member.getMembershipLevel()).get();
        return memberMapper.memberToOutput(member,addressDto,membershipLevel.getName());
    }

    public List<MemberDtoOutputLight> findAllMembers() {
        return memberMapper.projectedMembersToOutputLight(memberRepository.findAllMembersProjected());
    }

    public MemberDtoOutput updateMemberShipLevel(long memberId, long memberShipLevelId) {
        validateArgument(memberId,"Member not found in repository", i->!memberRepository.existsById(i),InvalidInputException::new);
        validateArgument(memberShipLevelId,"Membership level not found in repository", i->!membershipLevelRepository.existsById(i),InvalidInputException::new);
        Member member = memberRepository.findById(memberId).get();
        MembershipLevel membershipLevel = membershipLevelRepository.findById(memberShipLevelId).get();

        member.setMembershipLevel(memberShipLevelId);
        memberRepository.save(member);
        return memberMapper.memberToOutput(member,null,membershipLevel.getName());
    }

    private MembershipLevel setMemberShipLevelToBronzeWhenNull(MemberDtoInput memberDtoInput) {
        if(memberDtoInput.getMemberShipLevel() == null)
        {
            return membershipLevelRepository.findById(defaultMemberShipLevelId).get();
        }else {
            long memberShipLevelId = memberDtoInput.getMemberShipLevel();
            return membershipLevelRepository.findById(memberShipLevelId).get();
        }
    }

    private Address getAddressFromInput(AddressDtoInput addressDtoInput) {
        if(addressDtoInput == null){
            return null;
        }else {
            Address address = addressMapper.inputToAddress(validateAddressDtoInput(addressDtoInput));
            return addressRepository.save(address);
        }
    }

    private Long getIdFromAddress(Address address) {
        if(address == null){
            return null;
        } else {
            return address.getId();
        }
    }

    private AddressDtoOutput getDtoFromAddress(Address address) {
        if(address == null){
            return null;
        } else {
            return addressMapper.addressToOutput(address);
        }
    }

    private AddressDtoInput validateAddressDtoInput(AddressDtoInput addressDtoInput) {
        validateNonBlank(addressDtoInput.getStreet(),"Street cannot be null or blank",InvalidInputException::new);
        validateNonBlank(addressDtoInput.getCity(),"City cannot be null or blank",InvalidInputException::new);
        validateNonBlank(addressDtoInput.getCountry(),"Country cannot be null or blank",InvalidInputException::new);
        validateNonBlank(addressDtoInput.getPostalCode(),"PostalCode cannot be null or blank",InvalidInputException::new);
        return addressDtoInput;
    }

    private MemberDtoInput validateMemberDtoInput(MemberDtoInput memberDtoInput) {
        validateNonBlank(memberDtoInput.getFirstName(),"First name cannot be null or blank",InvalidInputException::new);
        validateNonBlank(memberDtoInput.getLastName(),"Last name cannot be null or blank",InvalidInputException::new);
        validateNonBlank(memberDtoInput.getEmail(),"Email cannot be null or blank",InvalidInputException::new);
        validateNonBlank(memberDtoInput.getPassword(),"Password name cannot be null or blank",InvalidInputException::new);
        validateNonBlank(memberDtoInput.getLicensePlate(),"License plate cannot be null or blank",InvalidInputException::new);

        validateArgument(memberDtoInput.getEmail(),"Email already exists in repository", memberRepository::existsByEmail,InvalidInputException::new);
        validateArgument(memberDtoInput.getEmail(),"License plate already exists in repository", memberRepository::existsByLicensePlate,InvalidInputException::new);
        if(memberDtoInput.getMemberShipLevel() != null){
            validateArgument(memberDtoInput.getMemberShipLevel(),"Membership level id not found in repository",
                    id->!membershipLevelRepository.existsById(id),InvalidInputException::new);
        }
        return memberDtoInput;
    }

}
