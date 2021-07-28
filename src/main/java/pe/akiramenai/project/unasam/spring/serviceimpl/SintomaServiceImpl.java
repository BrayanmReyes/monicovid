package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pe.akiramenai.project.unasam.spring.model.Sintoma;
import pe.akiramenai.project.unasam.spring.repository.ISintomaDAO;
import pe.akiramenai.project.unasam.spring.service.ISintomaService;


@Service
public class SintomaServiceImpl implements ISintomaService{

	@Autowired
	private ISintomaDAO dSintoma;
	
	@Override
	@Transactional
	public boolean insertar(Sintoma Sintoma)
	{
		Sintoma objSintoma=dSintoma.save(Sintoma);
		if(objSintoma==null)
			return false;
		else
			return true;
			}
	
	@Override
	@Transactional
	public boolean modificar(Sintoma Sintoma)
	{
		boolean flag=false;
		try {
			dSintoma.save(Sintoma);
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
	public void eliminar(Long idSintoma) {
		dSintoma.deleteById(idSintoma);
	}
	
	@Override
	public Optional<Sintoma>buscarId(Long idSintoma){
		return dSintoma.findById(idSintoma);
	}
	
	@Override
	public Optional<Sintoma>listarId(Long idSintoma){
		return dSintoma.findById(idSintoma);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Sintoma>listar(){
		return dSintoma.findAll();
	}

	@Override
	public List<Sintoma> listarbyPacienteDNI(String dni) {
		// TODO Auto-generated method stub
		return dSintoma.buscarbyPacienteDNI(dni);
	}

	@Override
	public List<Sintoma> listarSintomasbyUsername(String username) {
		// TODO Auto-generated method stub
		return dSintoma.buscarbyPacienteUserName(username);
	}
	

}
