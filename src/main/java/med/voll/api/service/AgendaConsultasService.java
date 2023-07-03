package med.voll.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.model.ValidacaoExceptnion;
import med.voll.api.record.DadosAgendamentoConsulta;
import med.voll.api.repository.ConsultaRepository;
import med.voll.api.repository.MedicoRepository;
import med.voll.api.repository.PacienteRepository;

@Service
public class AgendaConsultasService {
	
	@Autowired
	private ConsultaRepository consultaRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private PacienteRepository pacienteRepository;
	
	public void agendar(DadosAgendamentoConsulta dados) {
		
		if(!pacienteRepository.existsById(dados.idPaciente())) {
			throw new ValidacaoExceptnion("Id do paciente informado nao existe!");
		}
		if(dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
			throw new ValidacaoExceptnion("Id do medico informado nao existe!");
		}
		
		var medico = escolherMedico(dados);
		var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
		var consulta = new Consulta(null, medico, paciente, dados.data());
		consultaRepository.save(consulta);
	}

	private Medico escolherMedico(DadosAgendamentoConsulta dados) {
		if(dados.idMedico() != null) {
			return medicoRepository.getReferenceById(dados.idMedico());
		}
		
		if(dados.especialidade() == null) {
			throw new ValidationException("Especialidade e obrigatoria quando medico nao for escolhido");
		}
		
		return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
	}

}
