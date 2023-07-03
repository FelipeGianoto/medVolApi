package med.voll.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import med.voll.api.model.Medico;
import med.voll.api.record.DadosAtualizacaoMedico;
import med.voll.api.record.DadosListagemMedico;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	
	public Medico salvar(Medico medicos) {
		 return medicoRepository.save(medicos);
	}
	
	public Medico atualizar(@Valid DadosAtualizacaoMedico dados) {
		var medico = medicoRepository.getReferenceById(dados.id());
	    medico.atualizarInformacoes(dados);
	    return medico;
	}

	public void deletar(Long id) {
		var medico = medicoRepository.getReferenceById(id);
		medico.excluir();
	}
	
	public Page<DadosListagemMedico> listar(Pageable pageable){
		return medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico :: new );
	}

	public Medico detalhar(Long id) {
		var medico = medicoRepository.getReferenceById(id);
		return medico;
	}
}
