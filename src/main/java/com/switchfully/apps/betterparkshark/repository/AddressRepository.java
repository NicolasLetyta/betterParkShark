package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findById(long id);
}
