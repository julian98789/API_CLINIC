package med.gomez.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import med.gomez.api.domain.address.AddressData;

public record PatientRegistrationData(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 0, max = 15)
        String phone,

        //@Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
        @NotBlank
        String document,

        @NotNull
        @Valid
        AddressData address
) {
}
