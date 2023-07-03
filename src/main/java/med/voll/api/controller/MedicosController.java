package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.model.Medico;
import med.voll.api.record.DadosAtualizacaoMedico;
import med.voll.api.record.DadosCadastroMedico;
import med.voll.api.record.DadosDetalhamentoMedico;
import med.voll.api.record.DadosListagemMedico;
import med.voll.api.service.MedicoService;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicosController {
	
	@Autowired
	private MedicoService medicoService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
		Medico medico = medicoService.salvar(new Medico(dados));
		var uri = uriBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}
	
	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort  = {"nome"}) Pageable pageable) {
		Page<DadosListagemMedico> list = medicoService.listar(pageable);
		return ResponseEntity.ok(list);
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<?> editar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
		Medico medico = medicoService.atualizar(dados);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		medicoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable Long id) { 
		Medico medico = medicoService.detalhar(id);
		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
}
