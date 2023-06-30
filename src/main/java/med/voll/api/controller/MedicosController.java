package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.model.Medicos;
import med.voll.api.record.DadosCadastroMedico;
import med.voll.api.service.MedicoService;


@RestController
@RequestMapping("/medicos")
public class MedicosController {
	
	@Autowired
	private MedicoService medicoService;
	
	@PostMapping
	public void cadastrar(@RequestBody DadosCadastroMedico dados) {
		medicoService.salvarMedico(new Medicos(dados));
	}
}
