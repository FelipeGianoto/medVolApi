package med.voll.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import med.voll.api.model.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	@Query("""
            select p.ativo
            from Paciente p
            where
            p.id = :id
            """)
	Boolean findAtivoById(Long id);

}
