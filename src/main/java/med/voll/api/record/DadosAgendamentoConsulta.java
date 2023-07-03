package med.voll.api.record;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.enums.EspecialidadeEnum;

public record DadosAgendamentoConsulta(
		
		Long idMedico,
		
		@NotNull
		Long idPaciente,
		
		@NotNull
		@Future
		LocalDateTime data,
		
		EspecialidadeEnum especialidade
		
		) {}
