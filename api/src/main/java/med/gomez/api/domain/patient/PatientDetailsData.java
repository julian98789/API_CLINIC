package med.gomez.api.domain.patient;

import med.gomez.api.domain.address.Address;

public record PatientDetailsData(
         Long id,
         String name,
         String email,
         String document,
         String phone,
         Address address
) {
    public PatientDetailsData (Patient patient){
        this(patient.getId(), patient.getName(), patient.getEmail(),
                patient.getDocument(), patient.getPhone(), patient.getAddress());
    }
}
