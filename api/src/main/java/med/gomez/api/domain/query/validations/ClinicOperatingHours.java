package med.gomez.api.domain.query.validations;

import jakarta.validation.ValidationException;
import med.gomez.api.domain.query.DataScheduleQuery;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class ClinicOperatingHours  implements  QueryValidator {

    public void validation (DataScheduleQuery data){
        var sunday = DayOfWeek.SUNDAY.equals(data.fecha().getDayOfWeek());

        var beforeOpening = data.fecha().getHour()<7;
        var afterClosing = data.fecha().getHour()>19;
        if (sunday || beforeOpening || afterClosing){
            throw new ValidationException("El horario de atencion de la clinica es de lunes a sabados de 7:00am - 7:00pm ");
        }
    }
}
