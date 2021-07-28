package pe.akiramenai.project.unasam.spring.service;

import java.util.List;
import java.util.Optional;

import pe.akiramenai.project.unasam.spring.model.Role;
import pe.akiramenai.project.unasam.spring.model.Usuario;

public interface IRoleService {
		public boolean insertar(Role Role);
		public int  insertarRolexUsuario(Usuario usuario);
		public boolean modificar(Role Role);
		public void eliminar(Long id);
		public Optional<Role> buscarId(Long id);
		public Optional<Role> listarId(Long id);
		public List<Role> listar();
		public List<Role> listarTiposRole();
}
