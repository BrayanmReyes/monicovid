package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import pe.akiramenai.project.unasam.spring.model.Contacto;
import pe.akiramenai.project.unasam.spring.repository.IContactoDAO;
import pe.akiramenai.project.unasam.spring.service.IContactoService;


@Service
public class ContactoServiceImpl implements IContactoService{

	@Autowired
	private IContactoDAO dContacto;
	
	@Override
	@Transactional
	public boolean insertar(Contacto Contacto)
	{
		Contacto.setNumero("51"+Contacto.getNumero());
		Contacto objContacto=dContacto.save(Contacto);
		if(objContacto==null)
			return false;
		else
			return true;
			}
	
	@Override
	@Transactional
	public boolean modificar(Contacto Contacto)
	{
		boolean flag=false;
		try {
			Contacto.setNumero("51"+Contacto.getNumero());
			dContacto.save(Contacto);
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
	public void eliminar(Long idContacto) {
		dContacto.deleteById(idContacto);
	}
	
	@Override
	public Optional<Contacto>buscarId(Long idContacto){
		return dContacto.findById(idContacto);
	}
	
	@Override
	public Optional<Contacto>listarId(Long idContacto){
		return dContacto.findById(idContacto);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Contacto>listar(){
		return dContacto.findAll();
	}

	@Override
	public List<Contacto> listarbyPacienteDNI(String dni) {
		// TODO Auto-generated method stub
		return dContacto.buscarbyPacienteDNI(dni);
	}

	@Override
	public List<Contacto> listarContactosbyUsername(String username) {
		// TODO Auto-generated method stub
		return dContacto.buscarbyPacienteUserName(username);
	}
	

}
