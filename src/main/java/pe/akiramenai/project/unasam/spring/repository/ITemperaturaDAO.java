package pe.akiramenai.project.unasam.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.akiramenai.project.unasam.spring.model.Temperatura;

@Repository
public interface ITemperaturaDAO extends JpaRepository<Temperatura, Long>{
	
	@Query("from Temperatura c where c.idTemperatura like %:idTemperatura%")
	List<Temperatura>buscarIdTemperatura(@Param("idTemperatura")Long idTemperatura);
	
	@Query("select c.valor from Temperatura c")
	List<Temperatura>listarAllTemperatura();
	
	@Query("from Temperatura c where c.paciente.dni like %:dni%")
	List<Temperatura> buscarbyPacienteDNI(@Param("dni")String dni);
	
	//PARA EL CHART
	@Query("from Temperatura c where c.paciente.id like %:id%")
	List<Temperatura> buscarbyPacienteID(@Param("id")Long id);
	
	@Query("from Temperatura c where c.paciente.username like %:username%")
	List<Temperatura> buscarbyPacienteUserName(@Param("username")String username);
	
	@Query("from Temperatura c where c.paciente.username like %:username% order by id_temperatura")
	List<Temperatura> listarByPacienteUserNameOrdenado(@Param("username")String username);
}
