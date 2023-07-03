package med.voll.api.record;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.model.Endereco;
import med.voll.api.model.Medico;

public record DadosDetalhamentoMedico(
		
		Long id, 
		String nome, 
		String crm, 
		String telefone,
		EspecialidadeEnum especialidade, 
		Endereco endereco
		){
	
	public DadosDetalhamentoMedico(Medico medico) {
		this(medico.getId(), medico.getNome(), medico.getCrm(), medico.getTelefone(),medico.getEspecialidade(), medico.getEndereco());
	}
	
}
