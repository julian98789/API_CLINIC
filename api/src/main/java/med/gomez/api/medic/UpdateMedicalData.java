package med.gomez.api.medic;

import jakarta.validation.constraints.NotNull;
import med.gomez.api.address.AddressData;

public record UpdateMedicalData(
        @NotNull
        Long id,
        String name,
        String document,
        AddressData addres
) {
}
