package pe.akiramenai.project.unasam.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.model.Oxigeno;
import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.model.Sintoma;

@Repository
public interface IReporteDAO extends JpaRepository<Reporte, Long>{
	
	@Query("from Reporte c where c.id like %:id%")
	List<Reporte>buscarIdReporte(@Param("id")Long id);
	
	@Transactional
	@Modifying
	@Query("update Reporte r set r.oxigeno = :oxigeno where r.id = :id")
	void modificarReporteOxigenobyUserName(@Param("oxigeno")Oxigeno oxigeno, @Param("id")Long id);
	
	@Transactional
	@Modifying
	@Query("update Reporte r set r.sintoma = :sintoma where r.id = :id")
	void modificarReporteSintomabyUserName(@Param("sintoma")Sintoma sintoma, @Param("id")Long id);
	
	
	@Query("from Reporte c where c.temperatura.paciente.username like %:username% order by id")
	List<Reporte> buscarbyPacienteUserName(@Param("username")String username);
	
	@Query("from Reporte c where c.temperatura.paciente.dni like %:dni% order by id")
	List<Reporte> buscarbyPacienteDNI(@Param("dni")String dni);
	
	@Query("from Reporte c where c.temperatura.paciente.username like %:username% order by id desc")
	List<Reporte> buscarbyPacienteUserNameOrdenado(@Param("username")String username);
	
	@Query("from Reporte c where c.temperatura.paciente.dni like %:dni% order by id desc")
	List<Reporte> buscarbyPacienteDNIOrdenado(@Param("dni")String dni);
}
