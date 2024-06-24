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

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/medico") // Define la ruta base para este controlador
public class MedicController {

    @Autowired // Inyecta automáticamente una instancia de MedicalRepository
    private MedicalRepository medicalRepository;

    // Endpoint para registrar un médico
    @PostMapping // Define que este método maneja las solicitudes POST a /medico
    public ResponseEntity<DataResponseMedical> registerMedic(
            @RequestBody @Valid MedicalRegistrationData medicalRegistrationData,
            UriComponentsBuilder uriComponentsBuilder) {

        // Guarda el nuevo médico en la base de datos
        Medic medic = medicalRepository.save(new Medic(medicalRegistrationData));

        // Construye la respuesta con los datos del médico registrado
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

        // Construye la URI para la respuesta de creación
        URI url = uriComponentsBuilder.path("/medico/{id}").buildAndExpand(medic.getId()).toUri();

        // Retorna una respuesta HTTP 201 Created con la URL del recurso creado y los datos del médico
        return ResponseEntity.created(url).body(response);
    }

    // Endpoint para obtener la lista de médicos activos paginada
    @GetMapping // Define que este método maneja las solicitudes GET a /medico
    public ResponseEntity<Page<DataListMedical>> medicalList(
            @PageableDefault(size = 2) Pageable pagination) {

        // Obtiene la lista de médicos activos paginada desde el repositorio
        return ResponseEntity.ok(medicalRepository.findByActiveTrue(pagination).map(DataListMedical::new));
    }

    // Endpoint para actualizar los datos de un médico
    @PutMapping // Define que este método maneja las solicitudes PUT a /medico
    @Transactional // Gestiona la transacción de manera automática por el contenedor de Spring
    public ResponseEntity<DataResponseMedical> updateMedical(
            @RequestBody @Valid UpdateMedicalData updateMedicalData) {

        // Obtiene una referencia al médico desde el repositorio
        Medic medic = medicalRepository.getReferenceById(updateMedicalData.id());

        // Actualiza los datos del médico
        medic.updateData(updateMedicalData);

        // Construye la respuesta con los datos actualizados del médico
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

        // Retorna una respuesta HTTP 200 OK con los datos actualizados del médico
        return ResponseEntity.ok(response);
    }

    // Endpoint para desactivar lógicamente un médico (soft delete)
    @DeleteMapping("/{id}") // Define que este método maneja las solicitudes DELETE a /medico/{id}
    @Transactional // Gestiona la transacción de manera automática por el contenedor de Spring
    public ResponseEntity<Void> deleteMedic(@PathVariable Long id) {

        // Obtiene una referencia al médico desde el repositorio
        Medic medic = medicalRepository.getReferenceById(id);

        // Desactiva lógicamente al médico
        medic.deactivateMedic();

        // Retorna una respuesta HTTP 204 No Content para indicar que el médico fue desactivado correctamente
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener los datos de un médico por su ID
    @GetMapping("/{id}") // Define que este método maneja las solicitudes GET a /medico/{id}
    public ResponseEntity<DataResponseMedical> getMedicalData(@PathVariable Long id) {

        // Obtiene una referencia al médico desde el repositorio
        Medic medic = medicalRepository.getReferenceById(id);

        // Construye la respuesta con los datos del médico
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

        // Retorna una respuesta HTTP 200 OK con los datos del médico solicitado
        return ResponseEntity.ok(response);
    }
}
