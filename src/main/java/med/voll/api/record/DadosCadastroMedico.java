package med.voll.api.record;

import med.voll.api.enums.EspecialidadeEnum;

public record DadosCadastroMedico(String nome, String email, String crm, EspecialidadeEnum especialidade, DadosEndereco endereco) {
}
