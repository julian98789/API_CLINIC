package med.gomez.api.domain.medic;

import med.gomez.api.domain.address.AddressData;
import med.gomez.api.domain.patient.Patient;
import med.gomez.api.domain.patient.PatientRegistrationData;
import med.gomez.api.domain.query.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicalRepositoryTest {

    @Autowired
    private MedicalRepository medicalRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Deberia retornar null cuando el medico se encuentre en consuta con otro paciente")
    void selectMedicalSpecialtyOnDateScenery1() {
        //dado
        var nextMonday10H = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medic=registerMedic("julian","julian@mail.com","1234",Specialty.CARDIOLOGY);;
        var patient=registerPatient("maria","maria@gmail.com","1234");
        registerConsultation(medic,patient,nextMonday10H);

        //caundo
        var FreeMedic = medicalRepository.selectMedicalSpecialtyOnDate(Specialty.CARDIOLOGY,nextMonday10H );

        //entonces
        assertThat(FreeMedic).isNull();
    }

    @Test
    @DisplayName("deberia retornar un medico cuando realice la consulta en la base de datos  en ese horario")
    void selectMedicalSpecialtyOnDateScenery2() {

        //dado
        var nextMonday10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medic=registerMedic("julian","julian@mail.com","1234",Specialty.CARDIOLOGY);

        //caundo
        var FreeMedic = medicalRepository.selectMedicalSpecialtyOnDate(Specialty.CARDIOLOGY,nextMonday10H);

        //entonces
        assertThat(FreeMedic).isEqualTo(medic);
    }

    private void registerConsultation(Medic medic, Patient patient, LocalDateTime fecha) {
        testEntityManager.persist(new Query(null, medic, patient, fecha, null));
    }

    private Medic registerMedic(String name, String email, String document, Specialty specialty) {
        var medic = new Medic(dataMedic(name, email, document, specialty));
        testEntityManager.persist(medic);
        return medic;
    }

    private Patient registerPatient(String name, String email, String document) {
        var patient = new Patient(dataPatient(name, email, document));
        testEntityManager.persist(patient);
        return patient;
    }

    private  MedicalRegistrationData dataMedic(String name, String email, String document, Specialty specialty) {
        return new MedicalRegistrationData(
                name,
                email,
                "6199",
                document,
                specialty,
                addressData()
        );
    }

    private PatientRegistrationData dataPatient(String name, String email, String document) {
        return new PatientRegistrationData(
                name,
                email,
                "61999999999",
                document,
                addressData()

        );
    }

    private AddressData addressData() {
        return new AddressData(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }

}

