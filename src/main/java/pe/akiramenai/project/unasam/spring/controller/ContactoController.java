package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.akiramenai.project.unasam.spring.model.Contacto;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.service.IContactoService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;


@Controller
@RequestMapping("/contacto")
public class ContactoController {


	@Autowired
	private IContactoService aService;
	
	@Autowired
	private IUsuarioService eService;//paciente
		
	@RequestMapping("/listar")
	public String listar(Map<String, Object>model)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		model.put("listaContactos", aService.listarContactosbyUsername(userName));
		model.put("listaUsuarios", eService.listar());
		return "monicovidPacienteListContactos";
	}
	

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object>model, @ModelAttribute Contacto Proceso)
	{
		aService.listarId(Proceso.getIdContacto());
		return "monicovidPacienteListContactos";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model)
	{	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		Usuario usuario= eService.buscarPorUserName(userName);
		Contacto contacto=new Contacto();
		
		contacto.setPaciente(usuario);
				
		model.addAttribute("contacto", contacto);
		model.addAttribute("listaUsuarios", eService.listar());
		return "monicovidPacienteContacto";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Contacto objContacto, BindingResult binRes, Model model)
	throws ParseException
	{
		if(binRes.hasErrors()) 
		{
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteContacto";
		}
		else {
				boolean flag=aService.insertar(objContacto);
				if(flag) {
					return "redirect:/contacto/listar";
				}
					
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/contacto/irRegistrar";

				}
							
			}			
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Contacto objContacto, BindingResult binRes, Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors())
			return "redirect://contacto/listar";
		else {
				boolean flag=aService.modificar(objContacto);
				if(flag) {
					objRedir.addFlashAttribute("mensaje", "Se modificó correctamente");
					return "redirect:/contacto/listar";
					}
				else {
					objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
					return "redirect:/contacto/irRegistrar";

				}
							
			}			
	}
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Contacto>obj=aService.buscarId(id);
		if(obj==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://contacto/listar";
			
		}
		else
		{
			if(obj.isPresent())
				obj.ifPresent(o-> model.addAttribute("contacto",o));
			model.addAttribute("listaContactos", aService.listar());
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteContacto";
		}
	}	
	
	@RequestMapping("/alertar/{id}")
	public String alertar(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Contacto>obj=aService.buscarId(id);
		if(obj==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://contacto/listar";
			
		}
		else
		{
			if(obj.isPresent())
				obj.ifPresent(o-> model.addAttribute("contacto",o));
			//model.addAttribute("listaContactos", aService.listar());
			//model.addAttribute("listaUsuarios", eService.listar());
			return "EnviarSMS";
		}
	}	
	
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String,Object>model, @RequestParam(value="id")Long id){
		try {
			if(id!=null&&id>0) {
				aService.eliminar(id);
				model.put("listaContactos", aService.listar());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "Sucedio un error");
			model.put("listaContactos", aService.listar());
		}
		return "monicovidPacienteListContactos";
	}
	
}

