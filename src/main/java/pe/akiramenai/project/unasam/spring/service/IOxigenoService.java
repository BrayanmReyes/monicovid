package pe.akiramenai.project.unasam.spring.service;

import java.util.List;
import java.util.Optional;

import pe.akiramenai.project.unasam.spring.model.Oxigeno;

public interface IOxigenoService {

	public boolean insertar(Oxigeno Oxigeno);
	public boolean modificar(Oxigeno Oxigeno);
	public void eliminar(Long Oxigeno);
	public Optional<Oxigeno> buscarId(Long idOxigeno);
	public Optional<Oxigeno> listarId(Long idOxigeno);
	public List<Oxigeno> listar();
	public List<Oxigeno> listarbyPacienteDNI(String dni);
	//Para obtener segun el username de la sesion
	public List<Oxigeno> listarOxigenacionbyUsername(String username);
	public boolean isComplicacionOxigeno(String username);
	
	public List<Oxigeno> listarOxigenacionbyUsernameOrdenado(String username);
}
