package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipLevelRepository extends JpaRepository<MembershipLevel, Long> {
    Optional<MembershipLevel> findById(long id);

    boolean existsById(long id);
}
