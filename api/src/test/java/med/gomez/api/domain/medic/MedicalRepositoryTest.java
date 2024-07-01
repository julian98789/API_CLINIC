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

@DataJpaTest //Se utiliza para probar la capa de persistencia con JPA.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Configura el uso de la base de datos para las pruebas y no remplasa la base de datos real
@ActiveProfiles("test")
class MedicalRepositoryTest {

    @Autowired
    private MedicalRepository medicalRepository; // Repositorio de medicos

    @Autowired
    private TestEntityManager testEntityManager; // Para manejar entidades en las pruebas

    @Test
    @DisplayName("Deberia retornar null cuando el medico se encuentre en consuta con otro paciente")
    void selectMedicalSpecialtyOnDateScenery1() {
        // Dado: un médico y paciente registrados con una consulta el próximo lunes a las 10:00
        var nextMonday10H = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medic = registerMedic("julian", "julian@mail.com", "1234", Specialty.CARDIOLOGY);
        var patient = registerPatient("maria", "maria@gmail.com", "1234");
        registerConsultation(medic, patient, nextMonday10H);

        // Cuando: se consulta un médico disponible de cardiología en esa fecha y hora
        var FreeMedic = medicalRepository.selectMedicalSpecialtyOnDate(Specialty.CARDIOLOGY, nextMonday10H);

        // Entonces: debería retornar null ya que el médico está ocupado
        assertThat(FreeMedic).isNull();
    }

    @Test
    @DisplayName("Deberia retornar un medico cuando realice la consulta en la base de datos en ese horario")
    void selectMedicalSpecialtyOnDateScenery2() {
        // Dado: un médico registrado sin consultas el próximo lunes a las 10:00
        var nextMonday10H = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medic = registerMedic("julian", "julian@mail.com", "1234", Specialty.CARDIOLOGY);

        // Cuando: se consulta un médico disponible de cardiología en esa fecha y hora
        var FreeMedic = medicalRepository.selectMedicalSpecialtyOnDate(Specialty.CARDIOLOGY, nextMonday10H);

        // Entonces: debería retornar el médico ya que está disponible
        assertThat(FreeMedic).isEqualTo(medic);
    }

    // Método para registrar una consulta en la base de datos
    private void registerConsultation(Medic medic, Patient patient, LocalDateTime fecha) {
        testEntityManager.persist(new Query(null, medic, patient, fecha, null));
    }

    // Método para registrar un médico en la base de datos
    private Medic registerMedic(String name, String email, String document, Specialty specialty) {
        var medic = new Medic(dataMedic(name, email, document, specialty));
        testEntityManager.persist(medic);
        return medic;
    }

    // Método para registrar un paciente en la base de datos
    private Patient registerPatient(String name, String email, String document) {
        var patient = new Patient(dataPatient(name, email, document));
        testEntityManager.persist(patient);
        return patient;
    }

    // Datos para registrar un médico
    private MedicalRegistrationData dataMedic(String name, String email, String document, Specialty specialty) {
        return new MedicalRegistrationData(
                name,
                email,
                "6199",
                document,
                specialty,
                addressData()
        );
    }

    // Datos para registrar un paciente
    private PatientRegistrationData dataPatient(String name, String email, String document) {
        return new PatientRegistrationData(
                name,
                email,
                "61999999999",
                document,
                addressData()
        );
    }

    // Datos de dirección
    private AddressData addressData() {
        return new AddressData(
                "loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }
}
