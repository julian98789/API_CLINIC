package med.gomez.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.gomez.api.medic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/medico")
public class MedicController {

    @Autowired
    private MedicalRepository medicalRepository;

    @PostMapping
    public void registerMedic(@RequestBody @Valid MedicalRegistrationData medicalRegistrationData){

        medicalRepository.save(new Medic(medicalRegistrationData));
    }

    @GetMapping
    public Page<dataListmedical> medicalList(@PageableDefault(size = 2) Pageable pagination) {
        //return medicalRepository.findAll(pagination).map(dataListmedical::new);
        return medicalRepository.findByActiveTrue(pagination).map(dataListmedical::new);

    }

    @PutMapping
    @Transactional // cierra la transaccion a lo mas el metodo termine
    public void updateMedical(@RequestBody @Valid updateMedicalData updateMedicalData){
        Medic medic = medicalRepository.getReferenceById(updateMedicalData.id());
        medic.updateData(updateMedicalData);

    }

    // delete logico (no elimina de la base de datos)
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteMedic(@PathVariable Long id){
        Medic medic = medicalRepository.getReferenceById(id);
       medic.deactivateMedic();

    }
    //este codigo elimina el registro de la basa de datos
    /*
    public void deleteMedic(@PathVariable Long id){
        Medic medic = medicalRepository.getReferenceById(id);
        medicalRepository.delete(medic);

    }

     */
}
