package pe.akiramenai.project.unasam.spring.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import pe.akiramenai.project.unasam.spring.model.Temperatura;

public interface ITemperaturaService {

	public boolean insertar(Temperatura Temperatura);
	public boolean modificar(Temperatura Temperatura);
	public void eliminar(Long Temperatura);
	public Optional<Temperatura> buscarId(Long idTemperatura);
	public Optional<Temperatura> listarId(Long idTemperatura);
	public List<Temperatura> listar();
	public List<Temperatura> listarbyPacienteDNI(String dni);
	public List<Temperatura> listarAllTemperatura();
	//para el chart
	public List<Temperatura> TemperaturabyUserID(Long id);
	//Para obtener segun el username de la sesion
	public List<Temperatura> listarTemperaturabyUsername(String username);
	public boolean isComplicacionTemperatura(String username);
	public Date obtenerUltimoRegistro(String username);
	
	public List<Temperatura> listarTemperaturabyUsernameOrdenado(String username);
}
