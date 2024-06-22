package med.gomez.api.medic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.gomez.api.addres.Addres;



@Table (name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "ID")
public class Medic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String name;
    private  String email;
    private String document;
    private String phone;
    private Boolean active;
    @Enumerated(EnumType.STRING)
    private Specialty specialty;
    @Embedded
    private Addres addres;

    public Medic(MedicalRegistrationData medicalRegistrationData) {
        this.active = true;
        this.name = medicalRegistrationData.name();
        this.email = medicalRegistrationData.email();
        this.document = medicalRegistrationData.document();
        this.phone = medicalRegistrationData.phone();
        this.specialty = medicalRegistrationData.specialty();
        this.addres = new Addres(medicalRegistrationData.addres());
    }


    public void updateData(updateMedicalData updateMedicalData) {
        if (updateMedicalData.name() != null){
            this.name = updateMedicalData.name();
        }
        if (updateMedicalData.document() != null){
            this.document = updateMedicalData.document();
        }
        if (updateMedicalData.addres() != null){
            this.addres = addres.updateData(updateMedicalData.addres());
        }
    }

    public void deactivateMedic() {
        this.active = false;
    }
}
