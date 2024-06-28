package med.gomez.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
public class QueryController {

    @Autowired
    private QueryAgendaService service;

    @PostMapping
    @Transactional
    public ResponseEntity schedule (@RequestBody @Valid DataScheduleQuery data) throws IntegrityValidation {

        var response = service.schedule(data);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity cancel (@RequestBody @Valid DataCancellationConsultation data) throws IntegrityValidation{
        service.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
