package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.mail.MailSend;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.model.Sesion;
import pe.akiramenai.project.unasam.spring.repository.ISesionDAO;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;
import pe.akiramenai.project.unasam.spring.service.ISesionService;


@Service
public class SesionServiceImpl implements ISesionService{

	@Autowired
	private ISesionDAO dSesion;
	@Autowired
	private IUsuarioService sUsuario;
	
	
	@Override
	@Transactional
	public boolean insertar(Sesion Sesion)
	{
		Sesion objSesion=dSesion.save(Sesion);
		if(objSesion==null)
			return false;
		else
			return true;
			}
	
	@Override
	@Transactional
	public boolean modificar(Sesion Sesion)
	{
		boolean flag=false;
		try {
			dSesion.save(Sesion);
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
	public void eliminar(int idSesion) {
		dSesion.deleteById(idSesion);
	}
	
	@Override
	public Optional<Sesion>buscarId(int idSesion){
		return dSesion.findById(idSesion);
	}
	
	@Override
	public Optional<Sesion>listarId(int idSesion){
		return dSesion.findById(idSesion);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Sesion>listar(){
		return dSesion.findAll();
	}
	
	@Override
	@Transactional
	public List<Sesion>buscarSesion(String emailSesion){
		return dSesion.findByEmail(emailSesion);
	}
	
	@Override
	@Transactional
	public boolean sendMail(String emailSesion) {
		boolean flag=false;
		String password = crearPassword(8);
		List<Usuario> user =sUsuario.buscarEmailUsuario(emailSesion);
		
		user.get(0).setPassword(password);
		sUsuario.modificarNuevaPassword(user.get(0));
		flag=MailSend.enviarMail(emailSesion,"Nueva Contraseña", "Hola "+ user.get(0).getNombre() +", tu nueva contraseña es " + password);
		return flag;
	}

	@Override
	@Transactional
	public boolean esUsuario(String dni,String password) {
		boolean flag=false;
		int usuario=elegirUsuario(dni);
		switch(usuario) {
		case 1:List<Usuario>listaE= sUsuario.buscarUsuario(dni);
				if(listaE.get(0).getPassword().equals(password))
					flag=true;break;
		default: flag=false;break;
		}
		return flag;
	}

	
	@Override
	@Transactional	
	public int elegirUsuario(String dni) {
		int usuario=0;
			if(sUsuario.buscarUsuario(dni).isEmpty())
					usuario=0;
			else
				usuario=1;
		
		return usuario;
	}
	
	@Override
	@Transactional
	public int elegirUsuarioRecuperar(String email) {
		int usuario=0;
				if(sUsuario.buscarEmailUsuario(email).isEmpty())
					usuario=0;
				else
					usuario=1;
		
		return usuario;
	}
	
	
	@Override
	@Transactional
	public boolean sendMail(String email, String asunto, String mensaje) {
		boolean flag=false;
		
		flag=MailSend.enviarMail(email,asunto, mensaje);
		
		return flag;
	}
	
	private String crearPassword(int longitud) {
		String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
		String CHAR_UPPER = CHAR_LOWER.toUpperCase();
		String NUMBER = "0123456789";
		String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
		SecureRandom random = new SecureRandom();
		
		 StringBuilder sb = new StringBuilder(longitud);
	        for (int i = 0; i < longitud; i++) {

				// 0-62 (exclusive), random returns 0-61
	            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
	            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

	            // debug
	            System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);

	            sb.append(rndChar);

	        }

	        return sb.toString();

	}
}
