package pe.akiramenai.project.unasam.spring.service;

import java.util.List;
import java.util.Optional;

import pe.akiramenai.project.unasam.spring.model.Contacto;

public interface IContactoService {

	public boolean insertar(Contacto Contacto);
	public boolean modificar(Contacto Contacto);
	public void eliminar(Long Contacto);
	public Optional<Contacto> buscarId(Long idContacto);
	public Optional<Contacto> listarId(Long idContacto);
	public List<Contacto> listar();
	public List<Contacto> listarbyPacienteDNI(String dni);
	//Para obtener segun el username de la sesion
	public List<Contacto> listarContactosbyUsername(String username);
	
}
