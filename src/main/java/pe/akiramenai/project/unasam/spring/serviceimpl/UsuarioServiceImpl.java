package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.repository.IUsuarioDAO;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{
	@Autowired
	private IUsuarioDAO dUsuario;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private Usuario usuario;
	
	@Override
	@Transactional
	public boolean insertar(Usuario Usuario)
	{
		if(confirmarPassword(Usuario)) {
		aMayusculas(Usuario);
		Usuario.setPassword(passwordEncoder.encode(Usuario.getPassword()));
		Usuario.setEmail(Usuario.getUsername());
		Usuario objUsuario=dUsuario.save(Usuario);
		
		
		
		if(objUsuario==null)
			return false;
		else
			return true;
		}
		else
			return false;
	}
	
	@Override
	@Transactional
	public boolean modificar(Usuario Usuario)
	{
		boolean flag=false;
		try {
			if(confirmarPassword(Usuario)) {
			aMayusculas(Usuario);
			Usuario.setPassword(passwordEncoder.encode(Usuario.getPassword()));
			dUsuario.save(Usuario);
			flag=true;}
			else 
				flag=false;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return flag;			
	}
	
	@Override
	@Transactional
	public void eliminar(Long id) {
		dUsuario.deleteById(id);
	}
	
	@Override
	public Optional<Usuario>buscarId(Long id){
		return dUsuario.findById(id);
	}
	
	@Override
	public Optional<Usuario>listarId(Long id){
		return dUsuario.findById(id);
	}
	
	@Override
	public Optional<Usuario>listarCodigo(String username){
		Optional<Usuario> objUsuario= dUsuario.findByCodigo(username);
		if(objUsuario.isPresent()) {
			objUsuario.ifPresent(o->usuario=o);
		}
		return objUsuario;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Usuario>listar(){
		return dUsuario.findAll();
	}
	
	@Override
	@Transactional
	public List<Usuario>buscarUsuario(String dni){
		return dUsuario.buscarUsuario(dni);
	}
	
	@Override
	@Transactional
	public List<Usuario>buscarEmailUsuario(String email){
		return dUsuario.buscarEmailUsuario(email.toUpperCase());
	}
	
	@Override
	@Transactional
	public String buscarEmail(Long id) {
		String email="";
		List<Usuario>lista=dUsuario.buscarIdUsuario(id);
		
		if(!lista.isEmpty())
		email=lista.get(0).getEmail();
		return email;
	}
	
	@Override
	@Transactional
	public List<Usuario> listarPacientes() {
		List<Usuario>lista=dUsuario.findAll();
		List<Usuario>listaR=new ArrayList<Usuario>();
		for(int i=0;i<lista.size();i++)
		if(lista.get(i).getRoles().toString().contains("ADMIN"))
			listaR.add(lista.get(i));
		return listaR;
	}
	
	@Override
	@Transactional
	public List<Usuario> listarAsesores(){
		List<Usuario>lista=dUsuario.findAll();
		List<Usuario>listaR=new ArrayList<Usuario>();
		for(int i=0;i<lista.size();i++)
		if(lista.get(i).getRoles().toString().indexOf("ASESOR")>-1)
			listaR.add(lista.get(i));
		
		for(int i=0;i<lista.size();i++)
			if(lista.get(i).getRoles().toString().indexOf("MIXTO")>-1)
				listaR.add(lista.get(i));
		
		return listaR;
	}
	@Override
	@Transactional
	public List<Usuario> listarJueces(){
		List<Usuario>lista=dUsuario.findAll();
		List<Usuario>listaR=new ArrayList<Usuario>();
		for(int i=0;i<lista.size();i++)
		if(lista.get(i).getRoles().toString().indexOf("JUEZ")>-1)
			listaR.add(lista.get(i));
		
		for(int i=0;i<lista.size();i++)
			if(lista.get(i).getRoles().toString().indexOf("MIXTO")>-1)
				listaR.add(lista.get(i));
		
		return listaR;
	}
	
	@Override
	@Transactional
	public List<String> listarCiclos(int anio1, int anio2){
		List<String>lista=new ArrayList<>();
		String ciclo="";
		for(int i=anio1;i<anio2;i++) 
		for(int j=0;j<3;j++) {
			ciclo=i+"0"+j;
			lista.add(ciclo);
		}
		return lista;
	}

	@Override
	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	private boolean confirmarPassword(Usuario usuario){
		return usuario.getPassword().equalsIgnoreCase(usuario.getConfirmPassword());
	}
	private void aMayusculas(Usuario usuario){
		usuario.setNombre(usuario.getNombre().toUpperCase());
		usuario.setApellido(usuario.getApellido().toUpperCase());
		usuario.setUsername(usuario.getUsername().toUpperCase());
	}
	@Override
	@Transactional
	public boolean modificarNuevaPassword(Usuario Usuario)
	{
		boolean flag=false;
		try {
			Usuario.setPassword(passwordEncoder.encode(Usuario.getPassword()));
			dUsuario.save(Usuario);
			flag=true;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		return flag;			
	}
	
	@Override
	public List<Usuario> buscarByApellidos(String apellido) {
		return dUsuario.buscarByApellidos(apellido);
	}
	//d
	@Override
	public List<Usuario> buscarByCodigo(String username){
		return dUsuario.buscarByCodigo(username);
	}
	
	@Override
	public List<Usuario> buscarEstudianteDNI(String dni){
		return dUsuario.buscarEstudianteByDNI(dni, "ROLE_ESTUDIANTE");
	}

	@Override
	public List<Usuario> buscarEstudianteApellidos(String apellido) {
		return dUsuario.buscarEstudianteByApellidos(apellido, "ROLE_ESTUDIANTE");
	}

	@Override
	public List<Usuario> buscarEstudianteCodigo(String codigo) {
		return dUsuario.buscarEstudianteByCodigo(codigo, "ROLE_ESTUDIANTE");
	}

	@Override
	public Usuario buscarPorUserName(String username) {
		// TODO Auto-generated method stub
		return dUsuario.buscarPorUserName(username);
	}

	@Override
	public String obtenerUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		return userName;
	}
}
