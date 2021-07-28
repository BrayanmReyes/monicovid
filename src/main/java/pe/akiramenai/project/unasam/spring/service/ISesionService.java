package pe.akiramenai.project.unasam.spring.service;

import java.util.List;
import java.util.Optional;

import pe.akiramenai.project.unasam.spring.model.Sesion;

public interface ISesionService {

	public boolean insertar(Sesion Sesion);
	public boolean modificar(Sesion Sesion);
	public void eliminar(int idSesion);
	public Optional<Sesion> buscarId(int idSesion);
	public Optional<Sesion> listarId(int idSesion);
	public List<Sesion> listar();
	public List<Sesion> buscarSesion(String emailSesion);
	public boolean sendMail(String emailSesion);
	public boolean sendMail(String emailSesion,String asunto,String mensaje);
	public boolean esUsuario(String dni,String password);
	public int elegirUsuarioRecuperar(String email);
	public int elegirUsuario(String dni);
	
}
