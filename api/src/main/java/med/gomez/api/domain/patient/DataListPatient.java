package med.gomez.api.domain.patient;

public record DataListPatient(
        Long id,
        String name,
        String document,
        String email
) {
    public DataListPatient(Patient patient){
        this(patient.getId(), patient.getName(), patient.getDocument(), patient.getEmail());
    }

}
