package com.switchfully.apps.betterparkshark.repository;

import com.switchfully.apps.betterparkshark.domain.Division;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class DivisionRepositoryTest {

    @Autowired
    private DivisionRepository divisionRepository;

    private Division division;
    private Division subDivision;

    @BeforeEach
    void setUp() {
        division = new Division("Test Division", "Test Original Name", 1L, null);
    }

    // Tests that the division (with null parent) is saved correctly in the database
    @Test
    void givenCorrectDivision_whenSave_thenReturnDivisionFromDatabase() {
        Division savedDivision = divisionRepository.save(division);

        assertThat(savedDivision).isNotNull();
        assertThat(savedDivision.getId()).isNotNull();
        assertThat(savedDivision.getName()).isEqualTo(division.getName());
        assertThat(savedDivision.getOriginalName()).isEqualTo(division.getOriginalName());
        assertThat(savedDivision.getDirectorId()).isEqualTo(division.getDirectorId());
    }

    // Tests that the subdivision (with parent) is saved correctly in the database
    @Test
    void givenCorrectSubDivision_whenSave_thenReturnSubDivisionFromDatabase() {
        subDivision = new Division("Test Subdivision", "Test Original Name", 1L, 1L);
        Division savedSubDivision = divisionRepository.save(subDivision);

        assertThat(savedSubDivision).isNotNull();
        assertThat(savedSubDivision.getId()).isNotNull();
        assertThat(savedSubDivision.getName()).isEqualTo(subDivision.getName());
        assertThat(savedSubDivision.getOriginalName()).isEqualTo(subDivision.getOriginalName());
        assertThat(savedSubDivision.getDirectorId()).isEqualTo(subDivision.getDirectorId());
        assertThat(savedSubDivision.getParentId()).isEqualTo(subDivision.getParentId());
    }

    // Tests that an exception is thrown when trying to save a division with null values
    // for non-nullable fields or a null division.
    @Test
    void givenNullAtNonNullable_whenSave_thenThrowException() {
        Division nullValueName = new Division(null, "Test Original Name", 1L, null);
        Division nullValueOriginalName = new Division("Test Division", null, 1L, null);
        Division nullValueDirectorId = new Division("Test Division", "Test Original Name", null, null);

        assertThrows(Exception.class, () -> divisionRepository.save(nullValueName));
        assertThrows(Exception.class, () -> divisionRepository.save(nullValueOriginalName));
        assertThrows(Exception.class, () -> divisionRepository.save(nullValueDirectorId));

    }

    // Tests that we can retrieve the division by its ID after saving it
    @Test
    void givenDivisionExistsInDatabase_whenFindById_thenReturnDivisionFromDatabase() {
        Division savedDivision = divisionRepository.save(division);

        Division foundDivision = divisionRepository.findById(savedDivision.getId()).orElse(null);

        assertThat(foundDivision).isNotNull();
        assertThat(foundDivision.getId()).isEqualTo(savedDivision.getId());
        assertThat(foundDivision.getName()).isEqualTo(savedDivision.getName());
        assertThat(foundDivision.getOriginalName()).isEqualTo(savedDivision.getOriginalName());
        assertThat(foundDivision.getDirectorId()).isEqualTo(savedDivision.getDirectorId());
    }

    // Tests that findById returns null when trying to find a division that does not exist in the database
    @Test
    void givenDivisionDoesNotExistsInDatabase_whenFindById_thenReturnNull() {
        Division foundDivision = divisionRepository.findById(999L).orElse(null);

        assertThat(foundDivision).isNull();
    }

    // Tests that we can retrieve all divisions from the database
    @Test
    void givenDivisionsExistInDatabase_whenFindAll_thenReturnAllDivisions() {
        Division savedDivision1 = divisionRepository.save(division);
        Division savedDivision2 = divisionRepository.save(new Division("Test Division 2", "Test Original Name 2", 2L, null));

        List<Division> allDivisions = divisionRepository.findAll();

        assertThat(allDivisions).isNotNull();
        assertThat(allDivisions.size()).isGreaterThanOrEqualTo(2);
    }
}
