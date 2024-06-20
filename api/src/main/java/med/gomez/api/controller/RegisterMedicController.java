package med.gomez.api.controller;

import jakarta.validation.Valid;
import med.gomez.api.medic.Medic;
import med.gomez.api.medic.MedicalRegistrationData;
import med.gomez.api.medic.MedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/medico")
public class RegisterMedicController {

    @Autowired
    private MedicalRepository medicalRepository;

    @PostMapping
    public void registerMedic(@RequestBody @Valid MedicalRegistrationData medicalRegistrationData){

        medicalRepository.save(new Medic(medicalRegistrationData));
    }
}
