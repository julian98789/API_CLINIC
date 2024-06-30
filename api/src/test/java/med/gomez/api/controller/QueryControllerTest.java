package med.gomez.api.controller;

import med.gomez.api.domain.medic.Specialty;
import med.gomez.api.domain.query.DataDetailQueries;
import med.gomez.api.domain.query.DataScheduleQuery;
import med.gomez.api.domain.query.QueryAgendaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DataScheduleQuery> dataScheduleQueryJacksonTester;

    @Autowired
    private JacksonTester<DataDetailQueries> dataDetailQueriesJacksonTester;

    @MockBean
    private QueryAgendaService queryAgendaService;

    @WithMockUser
    @Test
    @DisplayName("Deberia retornar un estado http 400 cuando los datos ingresados sean invalidos")
    void scheduleScenery1() throws Exception {
        //dado / cuando
        var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();

        //entonces
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @WithMockUser
    @Test
    @DisplayName("Deberia retornar un estado http 200 cuando los datos ingresados sean validos")
    void scheduleScenery2() throws Exception {

        //dado
        var fecha = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;
        var data = new DataDetailQueries(null,5l,5l,fecha);

        // cuando
        when(queryAgendaService.schedule(any())).thenReturn(data);

        var response = mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dataScheduleQueryJacksonTester.write(new DataScheduleQuery(2l,5l,fecha, specialty)).getJson()))
                .andReturn().getResponse();

        //entonces
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJeson = dataDetailQueriesJacksonTester.write(data).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJeson);

    }
}