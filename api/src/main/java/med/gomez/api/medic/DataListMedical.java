package med.gomez.api.medic;


public record DataListMedical(
        Long id,
        String name,
        String document,
        String email,
        String specialty
) {
    public DataListMedical(Medic medic) {
        this(medic.getId(), medic.getName(), medic.getDocument(), medic.getEmail(), medic.getSpecialty().name());
    }
}
