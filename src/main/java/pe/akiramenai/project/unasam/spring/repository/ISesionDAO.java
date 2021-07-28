package pe.akiramenai.project.unasam.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.akiramenai.project.unasam.spring.model.Sesion;

@Repository
public interface ISesionDAO extends JpaRepository<Sesion, Integer>{
	
	@Query("from Sesion c where c.emailSesion like %:emailSesion%")
	List<Sesion> findByEmail(@Param("emailSesion")String emailSesion);

}
