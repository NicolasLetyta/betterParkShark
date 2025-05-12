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

    private final long defaultMembershipLevelId = 1L;

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
        Long addressId = address == null ? null : address.getId();

        Member member = memberMapper.inputToMember(memberDtoInput, addressId,membershipLevel.getId());
        memberRepository.save(member);

        return memberMapper.memberToOutput(member,address,membershipLevel.getName());
    }

    public MemberDtoOutput findMemberById(Long id){
        validateArgument(id,"Member not found in repository", i->!memberRepository.existsById(i),InvalidInputException::new);
        Member member = memberRepository.findById(id).get();
        System.out.println("Found member: " + member);
        Address address = addressRepository.findById(member.getAddressId()).orElse(null);
        System.out.println("Found address: " + address);
        //The way the data flows makes it impossible to retreieve a member from the databse whos MembershipLevelId is null
        //I can safely .get() from the optional without ever retrieving a null value
        MembershipLevel membershipLevel = membershipLevelRepository.findById(id).get();
        System.out.println("Found membershipLevel: " + membershipLevel);

        return memberMapper.memberToOutput(member,address,membershipLevel.getName());
    }

    public List<MemberDtoOutputLight> findAllMembers() {
        return memberMapper.projectedMembersToOutputLight(memberRepository.findAllMembersProjected());
    }

    public MemberDtoOutput updateMemberShipLevel(long memberId, long membershipLevelId) {
        validateArgument(memberId,"Member not found in repository", i->!memberRepository.existsById(i),InvalidInputException::new);
        validateArgument(membershipLevelId,"Membership level not found in repository", i->!membershipLevelRepository.existsById(i),InvalidInputException::new);

        Member member = memberRepository.findById(memberId).get();
        Address address = addressRepository.findById(member.getAddressId()).orElse(null);

        member.setMembershipLevelId(membershipLevelId);
        memberRepository.save(member);
        MembershipLevel membershipLevel = membershipLevelRepository.findById(member.getMembershipLevelId()).get();
        return memberMapper.memberToOutput(member,address,membershipLevel.getName());
    }

    private MembershipLevel setMemberShipLevelToBronzeWhenNull(MemberDtoInput memberDtoInput) {
        if(memberDtoInput.getMemberShipLevel() == null)
        {
            return membershipLevelRepository.findById(defaultMembershipLevelId).get();
        }else {
            long membershipLevelId = memberDtoInput.getMemberShipLevel();
            return membershipLevelRepository.findById(membershipLevelId).get();
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
        validateArgument(memberDtoInput.getLicensePlate(),"License plate already exists in repository", memberRepository::existsByLicensePlate,InvalidInputException::new);
        if(memberDtoInput.getMemberShipLevel() != null){
            validateArgument(memberDtoInput.getMemberShipLevel(),"Membership level id not found in repository",
                    id->!membershipLevelRepository.existsById(id),InvalidInputException::new);
        }
        return memberDtoInput;
    }

}
