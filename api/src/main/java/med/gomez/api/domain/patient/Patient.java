package med.gomez.api.domain.patient;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.gomez.api.domain.address.Address;


@Table(name = "pacientes")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Patient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String document;
    private String phone;
    private Boolean active;

    @Embedded
    private Address address;


    public Patient(PatientRegistrationData data) {
        this.active = true;
        this.name = data.name();
        this.email = data.email();
        this.document = data.document();
        this.phone = data.phone();
        this.address = new Address(data.address());
    }

    public void updateInformation(UpdatedPatientData data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.phone() != null) {
            this.phone = data.phone();
        }
        if (data.address() != null) {
            this.address.updateAddress(data.address());
        }

    }

    public void remove() {
        this.active = false;
    }
}
