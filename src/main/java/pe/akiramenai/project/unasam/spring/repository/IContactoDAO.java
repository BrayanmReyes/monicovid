package pe.akiramenai.project.unasam.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.akiramenai.project.unasam.spring.model.Contacto;

@Repository
public interface IContactoDAO extends JpaRepository<Contacto, Long>{
	
	@Query("from Contacto c where c.idContacto like %:idContacto%")
	List<Contacto>buscarIdContacto(@Param("idContacto")Long idContacto);
	
	@Query("from Contacto c where c.paciente.dni like %:dni%")
	List<Contacto> buscarbyPacienteDNI(@Param("dni")String dni);
	
	@Query("from Contacto c where c.paciente.username like %:username%")
	List<Contacto> buscarbyPacienteUserName(@Param("username")String username);
}
