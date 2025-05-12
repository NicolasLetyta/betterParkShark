package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByLicensePlate(String licensePlate);

    @Query("SELECT m.id as id, m.firstName as firstName, m.lastName as lastName, m.licensePlate as licensePlate, m.phone as phone, m.email as email, m.registrationDate as registrationDate FROM Member m")
    List<MemberProjection> findAllMembersProjected();

    Member findByEmail(String email);
}
