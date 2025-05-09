package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Division;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DivisionRepository extends JpaRepository<Division, Long> {

    Optional<Division> findById(long id);
    List<Division> findAll();
}