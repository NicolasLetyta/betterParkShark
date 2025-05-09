package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipLevelRepository extends JpaRepository<MembershipLevel, Long> {
    MembershipLevel findById(long id);
}
