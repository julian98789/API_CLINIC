package med.gomez.api.domain.medic;

import jakarta.validation.constraints.NotNull;
import med.gomez.api.domain.address.AddressData;

public record UpdateMedicalData(
        @NotNull
        Long id,
        String name,
        String document,
        AddressData addres
) {
}
