package pe.akiramenai.project.unasam.spring.service;

import java.util.List;
import java.util.Optional;

import pe.akiramenai.project.unasam.spring.model.Oxigeno;
import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.model.Sintoma;

public interface IReporteService {

	public boolean insertar(Reporte Reporte);
	public boolean modificar(Reporte Reporte);
	public void eliminar(Long Reporte);
	public Optional<Reporte> buscarId(Long idReporte);
	public Optional<Reporte> listarId(Long idReporte);
	public List<Reporte> listar();
	public void modificarReporteOxigenoPorUserName(Oxigeno oxigeno, Long id);
	public void modificarReporteSintomaPorUserName(Sintoma sintoma, Long id);
	public List<Reporte> buscarporPacienteUserName(String username);
	public List<Reporte> buscarporPacienteDNI(String dni);
	public List<Reporte> buscarporPacienteUserNameOrdenado(String username);
	public List<Reporte> buscarporPacienteDNIOrdenado(String dni);
}
