package med.gomez.api.domain.query.validations;

import jakarta.validation.ValidationException;
import med.gomez.api.domain.patient.PatientRepository;
import med.gomez.api.domain.query.DataScheduleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatient  implements  QueryValidator{
    @Autowired
    private PatientRepository patientRepository;

   public void validation (DataScheduleQuery data) {

       if (data.idPatient() == null) {
           return;
       }

       var activePatient = patientRepository.findActiveById(data.idPatient());

       if (!activePatient) {
           throw new ValidationException("No se pueden agendar citas con pacientes inactivos en el sistema");
       }
   }
}
