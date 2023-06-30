package med.voll.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.model.Medicos;
import med.voll.api.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;
	
	
	public Medicos salvarMedico(Medicos medicos) {
		return medicoRepository.save(medicos);
	}
}
