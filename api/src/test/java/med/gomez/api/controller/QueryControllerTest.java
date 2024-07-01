package med.gomez.api.controller;

import med.gomez.api.domain.medic.Specialty;
import med.gomez.api.domain.query.DataDetailQueries;
import med.gomez.api.domain.query.DataScheduleQuery;
import med.gomez.api.domain.query.QueryAgendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class QueryControllerTest {

    @Autowired
    private MockMvc mockMvc; //Se utiliza para simular solicitudes HTTP en los tests sin necesidad de un servidor real.

    @Autowired
    private JacksonTester<DataScheduleQuery> dataScheduleQueryJacksonTester; // Para serializar y deserializar JSON

    @Autowired
    private JacksonTester<DataDetailQueries> dataDetailQueriesJacksonTester; // Para serializar y deserializar JSON

    @MockBean
    private QueryAgendaService queryAgendaService; // Mock del servicio que maneja las consultas

    @WithMockUser //Simula un usuario autenticado para los tests de seguridad.
    @Test
    @DisplayName("Deberia retornar un estado http 400 cuando los datos ingresados sean invalidos")
    void scheduleScenery1() throws Exception {
        // Dado / cuando: se realiza una solicitud POST sin contenido
        var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();

        // Entonces: se espera un estado HTTP 400 (Bad Request)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @WithMockUser
    @Test
    @DisplayName("Deberia retornar un estado http 200 cuando los datos ingresados sean validos")
    void scheduleScenery2() throws Exception {

        // Dado: configuración de datos válidos y un mock del servicio
        var fecha = LocalDateTime.now().plusHours(1); // Fecha en el futuro
        var specialty = Specialty.CARDIOLOGY; // Especialidad
        var data = new DataDetailQueries(null, 5L, 5L, fecha); // Datos de detalle de la consulta

        // Cuando: se simula la respuesta del servicio al agendar
        when(queryAgendaService.schedule(any())).thenReturn(data);

        // Se realiza una solicitud POST con datos válidos
        var response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dataScheduleQueryJacksonTester.write(new DataScheduleQuery(2L, 5L, fecha, specialty)).getJson()))
                .andReturn().getResponse();

        // Entonces: se espera un estado HTTP 200 (OK)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Se verifica que el contenido de la respuesta sea igual al JSON esperado
        var expectedJson = dataDetailQueriesJacksonTester.write(data).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}
