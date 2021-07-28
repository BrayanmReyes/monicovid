package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.akiramenai.project.unasam.spring.model.Sesion;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.service.ISesionService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;


@Controller
@RequestMapping("/sesion")
public class SesionController {

	@Autowired
	private ISesionService sService;

	@Autowired
	private IUsuarioService uService;
	@Autowired	
	private DireccionController dir;
	
	private String mensaje;
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object>model)
	{
		model.put("listaSesions", sService.listar());
		return "listSesions";
	}
	
	@RequestMapping("/listarId")
	public String listarId(Map<String, Object>model, @ModelAttribute Sesion Sesion)
	{
		sService.listarId(Sesion.getIdSesion());
		return "listSesions";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model)
	{
		model.addAttribute("Sesion", new Sesion());
		return "Sesion";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Sesion objSesion, BindingResult binRes, Model model)
	throws ParseException
	{
		if(binRes.hasErrors())
			return "Sesion";
		else {
				boolean flag=sService.insertar(objSesion);
				if(flag)
					return "redirect:/sesion/listar";
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/sesion/irRegistrar";

				}
							
			}			
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Sesion objSesion, BindingResult binRes, Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors())
			return "redirect://sesion/listar";
		else {
				boolean flag=sService.modificar(objSesion);
				if(flag) {
					objRedir.addFlashAttribute("mensaje", "Se modificó correctamente");
					return "redirect:/sesion/listar";
					}
				else {
					objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
					return "redirect:/sesion/irRegistrar";

				}
							
			}			
	}
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Sesion>objSesion=sService.buscarId(id);
		if(objSesion==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://sesion/listar";
			
		}
		else
		{
			model.addAttribute("Sesion", objSesion);
			return "Sesion";
		}
	}	
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String,Object>model, @RequestParam(value="id")Integer id){
		try {
			if(id!=null&&id>0) {
				sService.eliminar(id);
				model.put("listaSesions", sService.listar());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "Sucedio un error");
			model.put("listaSesions", sService.listar());
		}
		return "listSesions";
	}
	@RequestMapping("/recuperar")
	public String recuperar(@ModelAttribute @Valid Sesion objSesion,  Model model)
	throws ParseException
	{
		boolean flag=false;
		int usuario=sService.elegirUsuarioRecuperar(objSesion.getEmailSesion());			
		if(usuario>0){
			
		if(sService.sendMail(objSesion.getEmailSesion()))
					flag=true;
				if(flag)
					return "redirect:/login";
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/monicovid/recuperarContrasenia";
					}			
		}
		else
			return "redirect:/monicovid/recuperarContrasenia";
	}
	
	@RequestMapping("/irModificarContrasenia")
	public String irModificarContraseña(Model model,RedirectAttributes objRedir)
	{
		if(mensaje!=null)
		if(!mensaje.equalsIgnoreCase(""))
			objRedir.addFlashAttribute("errormessage", mensaje);
		model.addAttribute("usuario", uService.getUsuario());
		return "modificarContraseña";
	}
	
	@RequestMapping("/actualizarContrasenia")
	public String actualizarContraseña(@ModelAttribute Usuario objUsuario, BindingResult binRes,  Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors()) {
			mensaje="Ocurrió un error";
			return "redirect:/sesion/irModificarContrasenia";	
		}
		else {
				if(objUsuario.getPassword().equalsIgnoreCase(objUsuario.getConfirmPassword())) {
					uService.getUsuario().setPassword(objUsuario.getPassword());
					
				
					
				boolean flag=uService.modificarNuevaPassword(uService.getUsuario());
				if(flag) {
					dir.setMensaje("Se cambió la contraseña correctamente");
					return "redirect:/monicovid/index";}
				else {
					mensaje="Ocurrió un error";
					objRedir.addFlashAttribute("errormessage", mensaje);
					return "redirect:/sesion/irModificarContrasenia";

				}

				}
				else {
					mensaje= "La contraseñas no coinciden";
					objRedir.addFlashAttribute("errormessage", mensaje);
					return "redirect:/sesion/irModificarContrasenia";

				}
							
			}		
	}
	@RequestMapping("/verUsuario/{id}")
	public String verEstudiante(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Usuario>objUsuario=uService.buscarId(id);
		if(objUsuario==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://usuario/lista";
			
		}
		else
		{
			if(objUsuario.isPresent())
				objUsuario.ifPresent(o->model.addAttribute("estudiante", o));
			return "alumnoInfo";
		}
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	
	
	
}

