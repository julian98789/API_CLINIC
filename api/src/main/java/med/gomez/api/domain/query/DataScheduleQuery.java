package med.gomez.api.domain.query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.gomez.api.domain.medic.Specialty;

import java.time.LocalDateTime;

public record DataScheduleQuery(
        @NotNull
        Long idPatient,
        Long idMedic,

        @NotNull
        @Future
        LocalDateTime fecha,

        Specialty specialty
) {
}
