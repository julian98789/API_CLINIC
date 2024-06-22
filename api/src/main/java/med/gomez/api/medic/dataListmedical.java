package med.gomez.api.medic;


public record dataListmedical(
        String name,
        String document,
        String email,
        String specialty
) {
    public dataListmedical(Medic medic) {
        this(medic.getName(), medic.getDocument(), medic.getEmail(), medic.getSpecialty().name());
    }
}
