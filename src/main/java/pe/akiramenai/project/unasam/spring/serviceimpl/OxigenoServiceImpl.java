package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pe.akiramenai.project.unasam.spring.model.Oxigeno;
import pe.akiramenai.project.unasam.spring.model.Temperatura;
import pe.akiramenai.project.unasam.spring.repository.IOxigenoDAO;
import pe.akiramenai.project.unasam.spring.service.IOxigenoService;
import pe.akiramenai.project.unasam.spring.util.mathResources;

@Service
public class OxigenoServiceImpl implements IOxigenoService{

	@Autowired
	private IOxigenoDAO dOxigeno;
	
	@Override
	@Transactional
	public boolean insertar(Oxigeno Oxigeno)
	{
		Oxigeno objOxigeno=dOxigeno.save(Oxigeno);
		if(objOxigeno==null)
			return false;
		else
			return true;
			}
	
	@Override
	@Transactional
	public boolean modificar(Oxigeno Oxigeno)
	{
		boolean flag=false;
		try {
			dOxigeno.save(Oxigeno);
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
	public void eliminar(Long idOxigeno) {
		dOxigeno.deleteById(idOxigeno);
	}
	
	@Override
	public Optional<Oxigeno>buscarId(Long idOxigeno){
		return dOxigeno.findById(idOxigeno);
	}
	
	@Override
	public Optional<Oxigeno>listarId(Long idOxigeno){
		return dOxigeno.findById(idOxigeno);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Oxigeno>listar(){
		return dOxigeno.findAll();
	}

	@Override
	public List<Oxigeno> listarbyPacienteDNI(String dni) {
		// TODO Auto-generated method stub
		return dOxigeno.buscarbyPacienteDNI(dni);
	}

	@Override
	public List<Oxigeno> listarOxigenacionbyUsername(String username) {
		// TODO Auto-generated method stub
		return dOxigeno.buscarbyPacienteUserName(username);
	}
	
	@Override
	public boolean isComplicacionOxigeno(String username) {
		// TODO Auto-generated method stub
		boolean flag = false;
		List<Oxigeno> listOxigeno = listarOxigenacionbyUsername(username);
		List<Double> listOxigenacion = new ArrayList<Double>();
		listOxigeno.forEach((oxigeno)->{
			listOxigenacion.add(oxigeno.getValor());
		});
		
		mathResources calculation = new mathResources();
		
		double meanOxigeno = calculation.mean(listOxigenacion);
		double sdOxigeno = calculation.sd(listOxigenacion);
		Oxigeno oxigenoActual = listOxigeno.get(listOxigeno.size()-1);
		if(oxigenoActual.getValor()<92.0)
			flag = true;
		
		if(listOxigenacion.size()>3) {
			if (oxigenoActual.getValor()<meanOxigeno-sdOxigeno)
				flag=true;
		}
		
		
		return flag;
	}
	
	@Override
	public List<Oxigeno> listarOxigenacionbyUsernameOrdenado(String username){
		List<Oxigeno> listaTemp = dOxigeno.listarbyPacienteUserNameOrdenado(username);
		List<Oxigeno> listaDefinitiva= new ArrayList<Oxigeno>(); 
		if(listaTemp.size()>10) {
			for(int i=0; i<10;i++) {
				listaDefinitiva.add(listaTemp.get(listaTemp.size()-10+i)); 
			}
			
		}
		else
			listaDefinitiva = listaTemp;
		
		return listaDefinitiva;
	}
}
