package pe.akiramenai.project.unasam.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.akiramenai.project.unasam.spring.model.Oxigeno;

@Repository
public interface IOxigenoDAO extends JpaRepository<Oxigeno, Long>{
	
	@Query("from Oxigeno c where c.idOxigeno like %:idOxigeno%")
	List<Oxigeno>buscarIdOxigeno(@Param("idOxigeno")Long idOxigeno);
	
	@Query("from Oxigeno c where c.paciente.dni like %:dni%")
	List<Oxigeno> buscarbyPacienteDNI(@Param("dni")String dni);
	
	@Query("from Oxigeno c where c.paciente.username like %:username%")
	List<Oxigeno> buscarbyPacienteUserName(@Param("username")String username);
	
	@Query("from Oxigeno c where c.paciente.username like %:username% order by id_oxigeno")
	List<Oxigeno> listarbyPacienteUserNameOrdenado(@Param("username")String username);
}
