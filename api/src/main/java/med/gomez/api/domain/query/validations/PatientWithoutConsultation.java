package med.gomez.api.domain.query.validations;

import jakarta.validation.ValidationException;
import med.gomez.api.domain.query.DataScheduleQuery;
import med.gomez.api.domain.query.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientWithoutConsultation  implements  QueryValidator{

    @Autowired
    private QueryRepository queryRepository;

    public void validation (DataScheduleQuery data){
        var firstSchedule = data.fecha().withHour(7);
        var lastSchedule = data.fecha().withHour(18);

        var patientWithConsultation = queryRepository.existsByPatientIdAndFechaBetween(data.idPatient(),firstSchedule,lastSchedule);

        if (patientWithConsultation){
            throw new ValidationException("El paciente ya tiene una consulta para esa fecha ");
        }

    }
}
