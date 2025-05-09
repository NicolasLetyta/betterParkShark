package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Allocation;

import java.util.List;

public interface AllocationRepositoryCustom {
    List<Allocation> findFilteredAllocations(int limit, String statusFilter, String orderDirection);

}
