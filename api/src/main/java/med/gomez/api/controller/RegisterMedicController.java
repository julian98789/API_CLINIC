package med.gomez.api.controller;

import jakarta.validation.Valid;
import med.gomez.api.medic.Medic;
import med.gomez.api.medic.MedicalRegistrationData;
import med.gomez.api.medic.MedicalRepository;
import med.gomez.api.medic.dataListmedical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/medico")
public class RegisterMedicController {

    @Autowired
    private MedicalRepository medicalRepository;

    @PostMapping
    public void registerMedic(@RequestBody @Valid MedicalRegistrationData medicalRegistrationData){

        medicalRepository.save(new Medic(medicalRegistrationData));
    }

    @GetMapping
    public Page<dataListmedical> medicalList(@PageableDefault(size = 2) Pageable pagination) {
        return medicalRepository.findAll(pagination).map(dataListmedical::new);

    }
}
