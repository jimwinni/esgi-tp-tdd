package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DrivingLicencePenaltyServiceTest {

    private InMemoryDatabase database = InMemoryDatabase.getInstance();

    @Test
    void should_reduce_points() {
        final var id = UUID.randomUUID();
        final var points = 5;
        final var drivingLicence = DrivingLicence.builder().id(id).build();

        assertThat(drivingLicence.getAvailablePoints() - points)
                .isNotNull()
                .isGreaterThanOrEqualTo(0)
                .isLessThanOrEqualTo(12);

        Assertions.assertThatNoException().isThrownBy(() -> database.save(id, drivingLicence));

        final var updatedDrivingLicence = DrivingLicence.builder().id(id).availablePoints(drivingLicence.getAvailablePoints() - points).build();

        Assertions.assertThatNoException().isThrownBy(() -> database.save(id, updatedDrivingLicence));

        assertThat(updatedDrivingLicence.getAvailablePoints())
                .isEqualTo(drivingLicence.getAvailablePoints() - points);
    }
}