package med.voll.api.validacaoConsulta;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.voll.api.model.ValidacaoExceptnion;
import med.voll.api.record.DadosAgendamentoConsulta;

@Component
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = dataConsulta.getHour() < 7;
        var depoisDoEncerramentoDaClinica = dataConsulta.getHour() > 18;
        if (domingo || antesDaAberturaDaClinica || depoisDoEncerramentoDaClinica) {
            throw new ValidacaoExceptnion("Consulta fora do horário de funcionamento da clínica");
        }

    }

}
