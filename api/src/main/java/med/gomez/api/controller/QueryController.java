package med.gomez.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.gomez.api.domain.query.DataCancellationConsultation;
import med.gomez.api.domain.query.DataDetailQueries;
import med.gomez.api.domain.query.DataScheduleQuery;
import med.gomez.api.domain.query.QueryAgendaService;
import med.gomez.api.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Consultas", description = "Controlador para la gestión de consultas médicas")
public class QueryController {

    @Autowired
    private QueryAgendaService service;

    @PostMapping
    @Transactional
    @Operation(summary = "Agendar una consulta", description = "Permite agendar una nueva consulta médica.")
    public ResponseEntity schedule (@RequestBody @Valid DataScheduleQuery data) throws IntegrityValidation {

        var response = service.schedule(data);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Cancelar una consulta", description = "Permite cancelar una consulta médica existente.")
    public ResponseEntity cancel (@RequestBody @Valid DataCancellationConsultation data) throws IntegrityValidation{
        service.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
