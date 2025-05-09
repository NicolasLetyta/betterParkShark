package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class MemberShipLevelRepositoryTest {

    @Autowired
    MembershipLevelRepository membershipLevelRepository;

    MembershipLevel bronze;
    MembershipLevel silver;
    MembershipLevel gold;

    @BeforeEach
    void setUp() {
        bronze = new MembershipLevel("bronze",0,0,4);
        silver = new MembershipLevel("silver",10,20,6);
        gold = new MembershipLevel("gold",40,30,24);
        membershipLevelRepository.save(bronze);
        membershipLevelRepository.save(silver);
        membershipLevelRepository.save(gold);
    }
}
