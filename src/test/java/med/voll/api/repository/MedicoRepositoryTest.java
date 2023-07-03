package med.voll.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.model.Consulta;
import med.voll.api.model.Medico;
import med.voll.api.model.Paciente;
import med.voll.api.record.DadosCadastroMedico;
import med.voll.api.record.DadosCadastroPaciente;
import med.voll.api.record.DadosEndereco;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager entityManager;

	@Test
	@DisplayName("Deveria devolver null quando unico medico cadastrado não esta disponivel na data")
	void testEscolherMedicoAleatorioLivreNaDataCenario1() {
		var proximaSegundaas10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0);
		
		var medico = cadastrarMedico("Medico", "Medico@voll.med", "123213", EspecialidadeEnum.CARDIOLOGIA);
		var paciente = cadastrarPaciente("Paciente", "paciente@voll.med", "00000000");
		cadastrarConsulta(medico, paciente, proximaSegundaas10);
		
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(EspecialidadeEnum.CARDIOLOGIA, proximaSegundaas10);
		assertThat(medicoLivre).isNull();
	}
	
	@Test
	@DisplayName("Deveria devolver medico quando ele estiver disponivel na data")
	void testEscolherMedicoAleatorioLivreNaDataCenario2() {
		var proximaSegundaas10 = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10,0);
		
		var medico = cadastrarMedico("Medico", "Medico@voll.med", "123213", EspecialidadeEnum.CARDIOLOGIA);
		
		var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(EspecialidadeEnum.CARDIOLOGIA, proximaSegundaas10);
		assertThat(medicoLivre).isEqualTo(medico);
	}
	
	private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
		entityManager.persist(new Consulta(null, medico, paciente, data, null));
	}

	private Medico cadastrarMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
	    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
	    entityManager.persist(medico);
	    return medico;
	}

	private Paciente cadastrarPaciente(String nome, String email, String cpf) {
	    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
	    entityManager.persist(paciente);
	    return paciente;
	}

	private DadosCadastroMedico dadosMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
	    return new DadosCadastroMedico(
	            nome,
	            email,
	            "61999999999",
	            crm,
	            especialidade,
	            dadosEndereco()
	    );
	}

	private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
	    return new DadosCadastroPaciente(
	            nome,
	            email,
	            "61999999999",
	            cpf,
	            dadosEndereco()
	    );
	}

	private DadosEndereco dadosEndereco() {
	    return new DadosEndereco(
	            "rua xpto",
	            "bairro",
	            "00000000",
	            "Brasilia",
	            "DF",
	            null,
	            null
	    );
	}

}
