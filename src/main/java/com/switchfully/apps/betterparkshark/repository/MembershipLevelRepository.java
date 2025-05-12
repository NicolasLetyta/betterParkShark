package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipLevelRepository extends JpaRepository<MembershipLevel, Long> {
    @Query("SELECT m.name as name FROM MembershipLevel m WHERE m.id =:id")
    String findNameById(@Param("id")long id);
}
