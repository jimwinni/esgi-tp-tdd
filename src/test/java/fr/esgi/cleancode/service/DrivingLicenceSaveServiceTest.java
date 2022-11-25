package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceSaveServiceTest {

    private InMemoryDatabase database = InMemoryDatabase.getInstance();

    @ParameterizedTest
    @ValueSource(strings = {"199782154684654", "555482154684654"})
    void should_validate(String validSSNumber) {
        final var id = UUID.randomUUID();

        assertThat(validSSNumber)
                .isNotNull()
                .containsOnlyDigits()
                .hasSizeGreaterThanOrEqualTo(15)
                .hasSizeLessThanOrEqualTo(15);

        val drivingLicence = DrivingLicence.builder().driverSocialSecurityNumber(validSSNumber).build();

        Assertions.assertThatNoException().isThrownBy(() -> database.save(id, drivingLicence));

        assertThat(drivingLicence.getDriverSocialSecurityNumber())
                .isEqualTo(validSSNumber);
    }
}

