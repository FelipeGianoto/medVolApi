package med.voll.api.record;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.model.Medico;

public record DadosListagemMedico(
		
		Long id,
		String nome,
		String email,
		String crm,
		EspecialidadeEnum especialidade
		
		){
	
		public DadosListagemMedico(Medico medicos) {
			this(medicos.getId(),medicos.getNome(), medicos.getEmail(), medicos.getCrm(),medicos.getEspecialidade());
		}
}
