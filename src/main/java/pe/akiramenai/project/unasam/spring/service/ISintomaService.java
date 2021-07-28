package pe.akiramenai.project.unasam.spring.service;

import java.util.List;
import java.util.Optional;

import pe.akiramenai.project.unasam.spring.model.Sintoma;

public interface ISintomaService {

	public boolean insertar(Sintoma Sintoma);
	public boolean modificar(Sintoma Sintoma);
	public void eliminar(Long Sintoma);
	public Optional<Sintoma> buscarId(Long idSintoma);
	public Optional<Sintoma> listarId(Long idSintoma);
	public List<Sintoma> listar();
	public List<Sintoma> listarbyPacienteDNI(String dni);
	//Para obtener segun el username de la sesion
	public List<Sintoma> listarSintomasbyUsername(String username);
	
}
