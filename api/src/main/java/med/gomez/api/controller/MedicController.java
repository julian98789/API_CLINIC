package med.gomez.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.gomez.api.domain.address.AddressData;
import med.gomez.api.domain.medic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping ("/medico")
public class MedicController {

    @Autowired
    private MedicalRepository medicalRepository;

    @PostMapping
    public ResponseEntity<DataResponseMedical> registerMedic(@RequestBody @Valid MedicalRegistrationData medicalRegistrationData,
                                                             UriComponentsBuilder uriComponentsBuilder){

        Medic medic = medicalRepository.save(new Medic(medicalRegistrationData));
        DataResponseMedical response = new DataResponseMedical(
                medic.getId(),
                medic.getName(),
                medic.getEmail(),
                medic.getPhone(),
                medic.getSpecialty().toString(),
                new AddressData(
                        medic.getAddress().getStreet(),
                        medic.getAddress().getCity(),
                        medic.getAddress().getDistrict(),
                        medic.getAddress().getNumber(),
                        medic.getAddress().getComplement())
        );
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medic.getId()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    //retorna todos los datos de los medicos
    @GetMapping
    public ResponseEntity<Page<DataListMedical> >medicalList(@PageableDefault(size = 2) Pageable pagination) {
        return ResponseEntity.ok(medicalRepository.findByActiveTrue(pagination).map(DataListMedical::new));

    }

    @PutMapping
    @Transactional // cierra la transaccion a lo mas el metodo termine
    public ResponseEntity updateMedical(@RequestBody @Valid UpdateMedicalData updateMedicalData) {
        Medic medic = medicalRepository.getReferenceById(updateMedicalData.id());
        medic.updateData(updateMedicalData);
        DataResponseMedical response = new DataResponseMedical(
                medic.getId(),
                medic.getName(),
                medic.getEmail(),
                medic.getPhone(),
                medic.getSpecialty().toString(),
                new AddressData(
                        medic.getAddress().getStreet(),
                        medic.getAddress().getCity(),
                        medic.getAddress().getDistrict(),
                        medic.getAddress().getNumber(),
                        medic.getAddress().getComplement())
        );

        return ResponseEntity.ok(response);
    }

    // delete logico (no elimina de la base de datos)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteMedic(@PathVariable Long id){
        Medic medic = medicalRepository.getReferenceById(id);
       medic.deactivateMedic();
       return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponseMedical> returnsMedicalData(@PathVariable Long id){
        Medic medic = medicalRepository.getReferenceById(id);
        var medicalData = new DataResponseMedical(
                medic.getId(),
                medic.getName(),
                medic.getEmail(),
                medic.getPhone(),
                medic.getSpecialty().toString(),
                new AddressData(
                        medic.getAddress().getStreet(),
                        medic.getAddress().getCity(),
                        medic.getAddress().getDistrict(),
                        medic.getAddress().getNumber(),
                        medic.getAddress().getComplement())
        );
        return ResponseEntity.ok(medicalData);

    }
    //este codigo elimina el registro de la basa de datos
    /*
    public void deleteMedic(@PathVariable Long id){
        Medic medic = medicalRepository.getReferenceById(id);
        medicalRepository.delete(medic);

    }

     */
}
