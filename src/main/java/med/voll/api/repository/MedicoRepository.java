package med.voll.api.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import med.voll.api.enums.EspecialidadeEnum;
import med.voll.api.model.Medico;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Page<Medico> findAllByAtivoTrue(Pageable pageable);

	@Query("""
            select m from Medico m
            where
            m.ativo = 1
            and
            m.especialidade = :especialidade
            and
            m.id not in(
                    select c.medico.id from Consulta c
                    where
                    c.data = :data
            )
            order by rand()
            limit 1
            """)
	Medico escolherMedicoAleatorioLivreNaData(EspecialidadeEnum especialidade, LocalDateTime data);
	
}
