package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.model.Temperatura;
import pe.akiramenai.project.unasam.spring.repository.ITemperaturaDAO;
import pe.akiramenai.project.unasam.spring.service.ITemperaturaService;
import pe.akiramenai.project.unasam.spring.util.mathResources;


@Service
public class TemperaturaServiceImpl implements ITemperaturaService{

	@Autowired
	private ITemperaturaDAO dTemperatura;
	
	@Override
	@Transactional
	public boolean insertar(Temperatura Temperatura)
	{
		Temperatura objTemperatura=dTemperatura.save(Temperatura);
		if(objTemperatura==null)
			return false;
		else
			return true;
			}
	
	@Override
	@Transactional
	public boolean modificar(Temperatura Temperatura)
	{
		boolean flag=false;
		try {
			dTemperatura.save(Temperatura);
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
	public void eliminar(Long idTemperatura) {
		dTemperatura.deleteById(idTemperatura);
	}
	
	@Override
	public Optional<Temperatura>buscarId(Long idTemperatura){
		return dTemperatura.findById(idTemperatura);
	}
	
	@Override
	public Optional<Temperatura>listarId(Long idTemperatura){
		return dTemperatura.findById(idTemperatura);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Temperatura>listar(){
		return dTemperatura.findAll();
	}

	@Override
	public List<Temperatura> listarbyPacienteDNI(String dni) {
		// TODO Auto-generated method stub
		return dTemperatura.buscarbyPacienteDNI(dni);
	}

	@Override
	public List<Temperatura> listarAllTemperatura() {
		// TODO Auto-generated method stub
		return dTemperatura.listarAllTemperatura();
	}

	//PARA EL CHART
	@Override
	public List<Temperatura> TemperaturabyUserID(Long id) {
		// TODO Auto-generated method stub
		return dTemperatura.buscarbyPacienteID(id);
	}

	@Override
	public List<Temperatura> listarTemperaturabyUsername(String username) {
		// TODO Auto-generated method stub
		return dTemperatura.buscarbyPacienteUserName(username);
	}
	
	@Override
	public boolean isComplicacionTemperatura(String username) {
		// TODO Auto-generated method stub
		boolean flag = false;
		List<Temperatura> listTemperatura = listarTemperaturabyUsername(username);
		List<Double> listTemperaturaValor = new ArrayList<Double>();
		listTemperatura.forEach((temperatura)->{
			listTemperaturaValor.add(temperatura.getValor());
		});
		
		mathResources calculation = new mathResources();
		
		double meanTemperatura = calculation.mean(listTemperaturaValor);
		double sdTemperatura = calculation.sd(listTemperaturaValor);
		Temperatura temperaturaActual = listTemperatura.get(listTemperatura.size()-1);
		if(temperaturaActual.getValor()>38.5)
			flag = true;
		
		if(listTemperaturaValor.size()>3) {
			if (temperaturaActual.getValor()>meanTemperatura+sdTemperatura)
				flag=true;
		}
		return flag;
	}
	
	@Override
	public Date obtenerUltimoRegistro(String username) {
		// TODO Auto-generated method stub
		
		
		List<Temperatura> listTemperatura = listarTemperaturabyUsername(username);
		
		if(listTemperatura.size()>=1){
			Temperatura temperaturaActual = listTemperatura.get(listTemperatura.size()-1);
			return temperaturaActual.getFechaRegistro();
		}
		else
			return null;
	}
	
	@Override
	public List<Temperatura> listarTemperaturabyUsernameOrdenado(String username){
		List<Temperatura> listaTemp = dTemperatura.listarByPacienteUserNameOrdenado(username);
		List<Temperatura> listaDefinitiva= new ArrayList<Temperatura>(); 
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
