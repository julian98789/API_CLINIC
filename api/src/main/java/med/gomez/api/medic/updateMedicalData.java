package med.gomez.api.medic;

import jakarta.validation.constraints.NotNull;
import med.gomez.api.addres.Addres;
import med.gomez.api.addres.AddressData;

public record updateMedicalData(
        @NotNull
        Long id,
        String name,
        String document,
        AddressData addres
) {
}
