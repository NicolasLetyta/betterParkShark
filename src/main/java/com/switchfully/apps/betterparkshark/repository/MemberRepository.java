package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m.id as id, m.name as name, m.licensePlate as lisencePlate, m.phone as phone, m.email as email, m.registrationDate as registrationDate FROM Member m")
    List<MemberProjection> findAllMembersProjected();
}
