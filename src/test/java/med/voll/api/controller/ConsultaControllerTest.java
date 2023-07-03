package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

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

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.record.DadosAgendamentoConsulta;
import med.voll.api.record.DadosDetalhamentoConsulta;
import med.voll.api.service.AgendaConsultasService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;
	
	@MockBean
	private AgendaConsultasService agenda;
	
	@Test
	@DisplayName("Deveria devolver codigo http 400 quando informações estiverem invalidas")
	@WithMockUser
	void agendarCenario1() throws Exception {
		
		var response = mockMvc.perform(post("/consultas")).andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@DisplayName("Deveria devolver codigo http 200 quando informações estiverem validas")
	@WithMockUser
	void agendarCenario2() throws Exception {
		var data = LocalDateTime.now().plusHours(1);
		var especialidade = EspecialidadeEnum.CARDIOLOGIA;
		var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 2l, data); 
		when(agenda.agendar(any())).thenReturn(dadosDetalhamento);
		
		var response = mockMvc.perform(
				post("/consultas")
					.contentType(MediaType.APPLICATION_JSON)
					.content(dadosAgendamentoConsultaJson.write(new DadosAgendamentoConsulta(2l, 2l, data, especialidade)).getJson())
				).andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		var jsonEsperado = dadosDetalhamentoConsultaJson.write(
				dadosDetalhamento
				).getJson();
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
	}

}
