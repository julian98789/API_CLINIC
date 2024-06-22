package med.gomez.api.medic;


public record dataListmedical(
        Long id,
        String name,
        String document,
        String email,
        String specialty
) {
    public dataListmedical(Medic medic) {
        this(medic.getID(), medic.getName(), medic.getDocument(), medic.getEmail(), medic.getSpecialty().name());
    }
}
