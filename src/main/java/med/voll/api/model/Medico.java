package med.voll.api.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.record.DadosAtualizacaoMedico;
import med.voll.api.record.DadosCadastroMedico;

@Table(name = "medico")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String crm;
	@Enumerated(EnumType.STRING)
	private EspecialidadeEnum especialidade;
	@Embedded
	private Endereco endereco;
	
	private boolean ativo;
	
	
	public Medico(DadosCadastroMedico dados) {
		this.nome = dados.nome();
		this.email = dados.email();
		this.telefone = dados.telefone();
		this.crm = dados.crm();
		this.especialidade = dados.especialidade();
		this.endereco = new Endereco(dados.endereco());
		this.ativo = true;
	}


	public void atualizarInformacoes(@Valid DadosAtualizacaoMedico dados) {
		if(dados.nome() != null) {
			this.nome = dados.nome();
		}
		if(dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		if(dados.endereco() != null) {
			this.endereco.atualizarInformacoes(dados.endereco());
		}
		
	}


	public void excluir() {
		this.ativo = false;
	}
}
