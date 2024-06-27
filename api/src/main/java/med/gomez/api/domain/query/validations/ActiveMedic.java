package med.gomez.api.domain.query.validations;

import jakarta.validation.ValidationException;
import med.gomez.api.domain.medic.MedicalRepository;
import med.gomez.api.domain.query.DataScheduleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveMedic  implements  QueryValidator{
    @Autowired
    private MedicalRepository medicalRepository;

    public void validation (DataScheduleQuery data) {

        if (data.idMedic() == null) {
            return;
        }

        var mctiveMedic = medicalRepository.findActiveById(data.idMedic());

        if (!mctiveMedic) {
            throw new ValidationException("No se pueden agendar citas con medicos inactivos en el sistema");
        }
    }
}
