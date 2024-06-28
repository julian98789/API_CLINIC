package med.gomez.api.domain.query;

import med.gomez.api.domain.medic.Medic;
import med.gomez.api.domain.medic.MedicalRepository;
import med.gomez.api.domain.patient.PatientRepository;
import med.gomez.api.domain.query.validations.QueryValidator;
import med.gomez.api.domain.query.validations.cancellationValidation.ValidatedCancellationOfQueries;
import med.gomez.api.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryAgendaService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalRepository medicalRepository;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    List<QueryValidator>validators;

    @Autowired
    List<ValidatedCancellationOfQueries> validatedCancellationOfQueries;

    public DataDetailQueries schedule (DataScheduleQuery data){

        if (!patientRepository.findById(data.idPatient()).isPresent()){
            throw new IntegrityValidation("El id del pacinte no fue encontrado");
        }

        if (data.idMedic()!=null && !medicalRepository.existsById(data.idMedic())){
            throw new IntegrityValidation("El id del medico no fue encontrado");
        }
        validators.forEach(v->v.validation(data));

        var patient = patientRepository.findById(data.idPatient()).get();

        var medic = selectMedic( data);

        if (medic==null){
            throw new IntegrityValidation("No existen medicos disponibles para este horario ni especialidad");
        }

        var quiery = new Query(medic,patient,data.fecha());

        queryRepository.save(quiery);

        return new DataDetailQueries(quiery);

    }

    private Medic selectMedic(DataScheduleQuery data) {

        if (data.idMedic()!=null){
            return medicalRepository.getReferenceById(data.idMedic());
        }
        if (data.specialty()==null){
            throw new IntegrityValidation("debe seleccionarse una specialidad para el medico");
        }
        return medicalRepository.selectMedicalSpecialtyOnDate(data.specialty(),data.fecha());
    }

    public void cancel(DataCancellationConsultation data){
        if (!queryRepository.existsById(data.idQuery())){
            throw new IntegrityValidation("Id de la consulta  no existe ");
        }
        validatedCancellationOfQueries.forEach(v->v.validation(data));

        var consultation = queryRepository.getReferenceById(data.idQuery());
        consultation.cancel(data.reason());
    }
}
