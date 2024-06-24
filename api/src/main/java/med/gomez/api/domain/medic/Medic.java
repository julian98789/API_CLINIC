package med.gomez.api.domain.medic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.gomez.api.domain.address.Address;

@Table(name = "medicos") // Define la tabla en la base de datos que corresponde a esta entidad
@Entity(name = "Medico") // Define el nombre de la entidad en JPA
@Getter // Genera automáticamente getters para todos los campos utilizando Lombok
@NoArgsConstructor // Genera automáticamente un constructor sin argumentos utilizando Lombok
@AllArgsConstructor // Genera automáticamente un constructor con todos los argumentos utilizando Lombok
@EqualsAndHashCode(of = "id") // Genera automáticamente métodos equals() y hashCode() basados en el campo "id" utilizando Lombok
public class Medic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el campo "id" es una clave primaria generada automáticamente
    private Long id;

    private String name;
    private String email;
    private String document;
    private String phone;
    private Boolean active;

    @Enumerated(EnumType.STRING) // Almacena el enum como una cadena en la base de datos
    private Specialty specialty;

    @Embedded // Indica que el objeto Address está embebido en esta entidad
    private Address address;

    // Constructor que inicializa los campos de la clase a partir de un objeto MedicalRegistrationData
    public Medic(MedicalRegistrationData medicalRegistrationData) {
        this.active = true;
        this.name = medicalRegistrationData.name();
        this.email = medicalRegistrationData.email();
        this.document = medicalRegistrationData.document();
        this.phone = medicalRegistrationData.phone();
        this.specialty = medicalRegistrationData.specialty();
        this.address = new Address(medicalRegistrationData.addres());
    }

    // Método para actualizar los datos del médico a partir de un objeto UpdateMedicalData
    public void updateData(UpdateMedicalData updateMedicalData) {
        if (updateMedicalData.name() != null) {
            this.name = updateMedicalData.name();
        }
        if (updateMedicalData.document() != null) {
            this.document = updateMedicalData.document();
        }
        if (updateMedicalData.addres() != null) {
            this.address = address.updateData(updateMedicalData.addres());
        }
    }

    // Método para desactivar un médico
    public void deactivateMedic() {
        this.active = false;
    }
}

