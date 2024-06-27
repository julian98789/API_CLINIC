package med.gomez.api.domain.query.validations;

import jakarta.validation.ValidationException;
import med.gomez.api.domain.query.DataScheduleQuery;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceSchedule implements  QueryValidator {
    public void validation (DataScheduleQuery data){
        var hour = LocalDateTime.now();
        var consultationTime= data.fecha();

        var differenceOf30Minutes = Duration.between(hour,consultationTime).toMinutes()<30;

        if (differenceOf30Minutes){
            throw new ValidationException("La consultas deben programarse con al menos 30 minutos de anticipacion");
        }
    }

}
