package med.gomez.api.domain.query;


import java.time.LocalDateTime;

public record DataDetailQueries(
        Long id,
        Long idPatient,
        Long idMedic,
        LocalDateTime fecha
) {
}
