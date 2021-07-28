package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.model.Oxigeno;
import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.model.Sintoma;
import pe.akiramenai.project.unasam.spring.repository.IReporteDAO;
import pe.akiramenai.project.unasam.spring.service.IReporteService;


@Service
public class ReporteServiceImpl implements IReporteService{

	@Autowired
	private IReporteDAO dReporte;
	
	@Override
	@Transactional
	public boolean insertar(Reporte Reporte)
	{
		Reporte objReporte=dReporte.save(Reporte);
		if(objReporte==null)
			return false;
		else
			return true;
			}
	
	@Override
	@Transactional
	public boolean modificar(Reporte Reporte)
	{
		boolean flag=false;
		try {
			dReporte.save(Reporte);
			flag=true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return flag;			
	}
	
	@Override
	@Transactional
	public void eliminar(Long idReporte) {
		dReporte.deleteById(idReporte);
	}
	
	@Override
	public Optional<Reporte>buscarId(Long idReporte){
		return dReporte.findById(idReporte);
	}
	
	@Override
	public Optional<Reporte>listarId(Long idReporte){
		return dReporte.findById(idReporte);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Reporte>listar(){
		return dReporte.findAll();
	}

	
	
	@Override
	public void modificarReporteOxigenoPorUserName(Oxigeno oxigeno, Long id) {
		dReporte.modificarReporteOxigenobyUserName(oxigeno, id);
	}
	
	@Override
	public void modificarReporteSintomaPorUserName(Sintoma sintoma, Long id) {
		dReporte.modificarReporteSintomabyUserName(sintoma, id);
	}
	
	@Override
	public List<Reporte> buscarporPacienteUserName(String username){
		return dReporte.buscarbyPacienteUserName(username);
	}
	@Override
	public List<Reporte> buscarporPacienteDNI(String dni){
		return dReporte.buscarbyPacienteDNI(dni);
	}
	
	@Override
	public List<Reporte> buscarporPacienteUserNameOrdenado(String username){
		return dReporte.buscarbyPacienteUserNameOrdenado(username);
	}
	@Override
	public List<Reporte> buscarporPacienteDNIOrdenado(String dni){
		return dReporte.buscarbyPacienteDNIOrdenado(dni);
	}
	
}
