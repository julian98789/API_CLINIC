package med.gomez.api.domain.query.validations.cancellationValidation;

import jakarta.validation.ValidationException;
import med.gomez.api.domain.query.DataCancellationConsultation;
import med.gomez.api.domain.query.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CancellationSchedule implements ValidatedCancellationOfQueries {

    @Autowired
    private QueryRepository queryRepository;

    @Override
    public void validation (DataCancellationConsultation data){
        var consultation = queryRepository.getReferenceById(data.idQuery());
        var now = LocalDateTime.now();
        var hourDifference = Duration.between(now,consultation.getFecha()).toHours();

        if (hourDifference < 24){
            throw new ValidationException("La consulta solamente puede ser cancelada con minimo un dia de antelacion  ");
        }
    }
}
