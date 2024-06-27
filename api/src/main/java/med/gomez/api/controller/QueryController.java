package med.gomez.api.controller;

import jakarta.validation.Valid;
import med.gomez.api.domain.query.DataDetailQueries;
import med.gomez.api.domain.query.DataScheduleQuery;
import med.gomez.api.domain.query.QueryAgendaService;
import med.gomez.api.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class QueryController {

    @Autowired
    private QueryAgendaService service;

    @PostMapping
    @Transactional
    public ResponseEntity schedule (@RequestBody @Valid DataScheduleQuery data) throws IntegrityValidation {

        var response = service.schedule(data);

        return ResponseEntity.ok(response);
    }
}
