package med.voll.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.model.ValidacaoExceptnion;
import med.voll.api.record.DadosAgendamentoConsulta;
import med.voll.api.record.DadosDetalhamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;
import med.voll.api.validacaoConsulta.ValidadorAgendamentoDeConsulta;

@Service
public class AgendaConsultasService {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private List<ValidadorAgendamentoDeConsulta> validadores;
	
	public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoExceptnion("Id do paciente informado nao existe!");
		}
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoExceptnion("Id do medico informado nao existe!");
		}
		
		validadores.forEach(v -> {
			v.validar(dados);
		});
		
		var medico = escolherMedico(dados);
		var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		var consulta = new Consulta(null, medico, paciente, dados.data(), null);
		consultaRepository.save(consulta);
		
		return new DadosDetalhamentoConsulta(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if(dados.especialidade() == null) {
			throw new ValidationException("Especialidade e obrigatoria quando medico nao for escolhido");
		}
		
		return null;
	}

}
