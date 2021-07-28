package pe.akiramenai.project.unasam.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.model.Role;

@Repository
public interface IRoleDAO extends JpaRepository<Role, Long>{
	
	@Transactional
	@Modifying
	@Query(
	  value = 
	    "insert into authorities (id, authority, user_id) values (default, :authority, :user_id)",
	  nativeQuery = true)
	int insertRole(@Param("authority") String authority, @Param("user_id") Long user_id);

	@Transactional
	@Modifying
	@Query(
	  value = 
	    "insert into authorities (id, authority, user_id) values (default, :authority, :user_id)",
	  nativeQuery = true)
	void insertarRole(@Param("authority") String authority, @Param("user_id") Long user_id);

}
