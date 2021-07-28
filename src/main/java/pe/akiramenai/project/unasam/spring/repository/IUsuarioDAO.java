package pe.akiramenai.project.unasam.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.akiramenai.project.unasam.spring.model.Usuario;

@Repository
public interface IUsuarioDAO extends JpaRepository<Usuario, Long>{
	
	@Query("from Usuario c where c.email like %:email%")
	List<Usuario>buscarEmailUsuario(@Param("email")String email);
	
	@Query("from Usuario c where c.id like %:id%")
	List<Usuario>buscarIdUsuario(@Param("id")Long id);

	//@Query("from Usuario c where c.username like %:username%")
	public Usuario findByUsername(String username);
	
	@Query("from Usuario c where c.username like %:username%")
	Optional<Usuario> findByCodigo(String username);
	
	//Buscar a usuario especifico
	@Query("from Usuario c where c.dni like %:dni%")
	List<Usuario>buscarUsuario(@Param("dni")String dni);
	
	@Query("from Usuario c where c.apellido like %:apellido%")
	List<Usuario> buscarByApellidos(@Param("apellido")String apellido);
	
	@Query("from Usuario c where c.username like %:username%")
	List<Usuario> buscarByCodigo(@Param("username")String username);
	
	@Query("SELECT t FROM Usuario t where t.dni = :dni AND t.autoridad = :autoridad")
	List<Usuario> buscarEstudianteByDNI(@Param("dni") String dni, @Param("autoridad") String autoridad);
	
	@Query("SELECT t FROM Usuario t where t.apellido like %:apellido% AND t.autoridad = :autoridad")
	List<Usuario> buscarEstudianteByApellidos(@Param("apellido") String apellido, @Param("autoridad") String autoridad);
	
	@Query("SELECT t FROM Usuario t where t.username like %:username% AND t.autoridad = :autoridad")
	List<Usuario> buscarEstudianteByCodigo(@Param("username") String username, @Param("autoridad") String autoridad);
	
	//PARA OBTENER AL USUARIO DE LA SESION
	@Query("from Usuario c where c.username like %:username%")
	Usuario buscarPorUserName(String username);
	

}
