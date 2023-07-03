package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.record.DadosAgendamentoConsulta;
import med.voll.api.service.AgendaConsultasService;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
	
	@Autowired
	private AgendaConsultasService agendaService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
		var dtoConsulta = agendaService.agendar(dados);
		return ResponseEntity.ok(dtoConsulta);
	}
}
