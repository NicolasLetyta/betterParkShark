package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.MembershipLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void persistNewMembershipLevelTest() {
        MembershipLevel newMemberShipLevel = new MembershipLevel("newLevel",0,0,4);
        MembershipLevel result = membershipLevelRepository.save(newMemberShipLevel);

        assertThat(result).isEqualTo(newMemberShipLevel);
    }

    @Test
    void givenExistingMemberShipLevelId_whenFindById_thenReturnCorrectMemberShipLevel() {
        long idBronze = bronze.getId();
        long idSilver = silver.getId();
        long idGold = gold.getId();

        MembershipLevel resultBronze = membershipLevelRepository.findById(idBronze).get();
        MembershipLevel resultSilver = membershipLevelRepository.findById(idSilver).get();
        MembershipLevel resultGold = membershipLevelRepository.findById(idGold).get();

        assertThat(resultBronze).isEqualTo(bronze);
        assertThat(resultSilver).isEqualTo(silver);
        assertThat(resultGold).isEqualTo(gold);
    }
}
