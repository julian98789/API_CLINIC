package med.gomez.api.domain.query;


import java.time.LocalDateTime;

public record DataDetailQueries(
        Long id,
        Long idPatient,
        Long idMedic,
        LocalDateTime fecha
) {
    public DataDetailQueries(Query query){
        this(query.getId(),query.getPatient().getId(),query.getMedic().getId(),query.getFecha());
    }
}
