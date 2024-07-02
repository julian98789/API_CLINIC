package med.gomez.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.gomez.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Pacientes", description = "Controlador para la gestión de pacientes")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Registrar un nuevo paciente", description = "Registra un nuevo paciente en la base de datos y devuelve la información del paciente registrado.")
    public ResponseEntity registrar(@RequestBody @Valid PatientRegistrationData data, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(patient.getId()).toUri();
        return ResponseEntity.created(uri).body(new PatientDetailsData(patient));
    }

    @GetMapping
    @Operation(summary = "Listar pacientes activos", description = "Devuelve una lista paginada de todos los pacientes que están activos.")
    public ResponseEntity<Page<DataListPatient>> listar(@PageableDefault(size = 3, sort = {"name"}) Pageable pagination) {
        var page = repository.findAllByActiveTrue(pagination).map(DataListPatient::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualizar datos de un paciente", description = "Actualiza la información de un paciente existente en la base de datos.")
    public ResponseEntity actualizar(@RequestBody @Valid UpdatedPatientData data) {
        var patient = repository.getReferenceById(data.id());
        patient.updateInformation(data);

        return ResponseEntity.ok(new PatientDetailsData(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un paciente", description = "Elimina lógicamente un paciente por su ID.")
    public ResponseEntity eliminar(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.remove();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener datos de un paciente", description = "Devuelve los datos de un paciente por su ID.")
    public ResponseEntity detallar(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new PatientDetailsData(patient));
    }


}