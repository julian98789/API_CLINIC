package med.gomez.api.domain.query.validations;

import jakarta.validation.ValidationException;
import med.gomez.api.domain.query.DataScheduleQuery;
import med.gomez.api.domain.query.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicWithConsultation  implements  QueryValidator {

    @Autowired
    private QueryRepository queryRepository;

    public void validation(DataScheduleQuery data){

        if (data.idMedic()==null){
            return;
        }

        var medicWithConsultation = queryRepository.existsByMedicIdAndFecha(data.idMedic(),data.fecha());

        if (medicWithConsultation){
            throw new ValidationException("El medico ya tiene una consulta para esa fecha");
        }

    }

}
