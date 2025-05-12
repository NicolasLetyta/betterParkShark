package com.switchfully.apps.betterparkshark.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DivisionRepositoryTest {

    @Autowired
    private DivisionRepository divisionRepository;

}
