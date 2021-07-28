package pe.akiramenai.project.unasam.spring.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.akiramenai.project.unasam.spring.model.Role;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.repository.IUsuarioDAO;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;


@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuarioDAO usuarioDao;

	@Autowired
	private IUsuarioService uService;

	private String index;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username=username.toUpperCase();
		Usuario usuario = usuarioDao.findByUsername(username);
		uService.setUsuario(usuario);
		
		index=obtenerIndex(usuario);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnable(), true, true, true,
				authorities);
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	private String obtenerIndex(Usuario usuario) {
		
		String direccion="redirect:/login";
		if(usuario==null)
			direccion= "redirect:/monicovid/error404";
		else{
			if(uService.getUsuario().getAutoridad().contains("ADMIN"))
				direccion= "redirect:/usuario/lista";
			else
				if(uService.getUsuario().getAutoridad().contains("ESTUDIANTE"))
					direccion= "redirect:/estudiante/perfil";
				else
					if(uService.getUsuario().getAutoridad().contains("ASESOR"))
						direccion= "redirect:/asesor/perfil";
					else
						if(uService.getUsuario().getAutoridad().contains("JUEZ"))
							direccion= "redirect:/juez/perfil";

			}
		return direccion;
	}
	
	
}
