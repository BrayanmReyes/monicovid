package pe.akiramenai.project.unasam.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.akiramenai.project.unasam.spring.model.Sintoma;

@Repository
public interface ISintomaDAO extends JpaRepository<Sintoma, Long>{
	
	@Query("from Sintoma c where c.idSintoma like %:idSintoma%")
	List<Sintoma>buscarIdSintoma(@Param("idSintoma")Long idSintoma);
	
	@Query("from Sintoma c where c.paciente.dni like %:dni%")
	List<Sintoma> buscarbyPacienteDNI(@Param("dni")String dni);
	
	@Query("from Sintoma c where c.paciente.username like %:username%")
	List<Sintoma> buscarbyPacienteUserName(@Param("username")String username);
}
