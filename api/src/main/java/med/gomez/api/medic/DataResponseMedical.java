package med.gomez.api.medic;

import med.gomez.api.address.AddressData;

public record DataResponseMedical(
        Long id,
        String name,
        String email,
        String phone,
        String document,
        AddressData address
) {
}
