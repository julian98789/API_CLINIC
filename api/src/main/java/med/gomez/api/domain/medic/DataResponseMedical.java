package med.gomez.api.domain.medic;

import med.gomez.api.domain.address.AddressData;

public record DataResponseMedical(
        Long id,
        String name,
        String email,
        String phone,
        String document,
        AddressData address
) {
}
