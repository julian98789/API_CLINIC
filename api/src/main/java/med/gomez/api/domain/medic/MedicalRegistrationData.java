package med.gomez.api.domain.medic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.gomez.api.domain.address.AddressData;

public record MedicalRegistrationData(

    @NotBlank
    String name,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Pattern(regexp = "\\d{4,6}")//el margen de tamaño
    String document,

    @NotBlank
    String phone,

    @NotNull
    Specialty specialty,

    @NotNull
    @Valid
    AddressData addres
) {

}