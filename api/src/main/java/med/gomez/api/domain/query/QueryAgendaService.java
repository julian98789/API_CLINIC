package med.gomez.api.domain.query;

import med.gomez.api.domain.medic.Medic;
import med.gomez.api.domain.medic.MedicalRepository;
import med.gomez.api.domain.patient.PatientRepository;
import med.gomez.api.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryAgendaService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicalRepository medicalRepository;

    @Autowired
    private QueryRepository queryRepository;

    public void schedule (DataScheduleQuery data){

        if (patientRepository.findById(data.idPatient()).isPresent()){
            throw new IntegrityValidation("El id del pacinte no fue encontrado");
        }

        if (data.idMedic()!=null && medicalRepository.existsById(data.idMedic())){
            throw new IntegrityValidation("El id del medico no fue encontrado");
        }

        var patient = patientRepository.findById(data.idPatient()).get();

        var medic = selectMedic( data);

        var quiery = new Query(null,medic,patient,data.fecha());

        queryRepository.save(quiery);

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
}
