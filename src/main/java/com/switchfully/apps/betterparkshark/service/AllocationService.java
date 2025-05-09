package com.switchfully.apps.betterparkshark.service;

import com.switchfully.apps.betterparkshark.repository.MemberRepository;
import com.switchfully.apps.betterparkshark.repository.ParkingLotRepository;
import com.switchfully.apps.betterparkshark.webapi.dto.AllocationDtoInput;
import org.springframework.stereotype.Service;

@Service
public class AllocationService {

    private ParkingLotRepository parkingLotRepository;
    private MemberRepository memberRepository;


    public AllocationDtoInput createNewAllocation(AllocationDtoInput allocationDtoInput) {
        // check id member, id
        return null;
    }

}
