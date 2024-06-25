package med.gomez.api.domain.patient;

import jakarta.validation.constraints.NotNull;
import med.gomez.api.domain.address.AddressData;


public record UpdatedPatientData(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressData address
) {
}
