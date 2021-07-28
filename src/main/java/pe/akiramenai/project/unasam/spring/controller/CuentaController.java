package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.repository.IRoleDAO;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;

@Controller
@RequestMapping("/cuenta")
public class CuentaController {

	@Autowired
	private IUsuarioService uService;
	
	@Autowired
	private IRoleDAO roleDAO;
	
	private String back;
	
	@Autowired
	private SesionController sController;
	
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		return "monicovidUsuarioCrear";
	}
	
	@RequestMapping("/Adminstrator-ir-Registrar")
	public String irRegistrarAdmin(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		return "monicovidAdminCrear";
	}
	
	@RequestMapping("/ir-Medico-Registrar")
	public String irRegistrarMedico(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		return "monicovidMedicoCrear";
	}
		
	@RequestMapping("/irModificarContraseña")
	public String irModificarContraseña(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		return "modificarContraseña";
	}
	
	//
	@RequestMapping("/listaProcesos")
	public String listarA(Map<String, Object>model)
	{
		model.put("listaEstudiantes", uService.listarPacientes());
		return "adminListProcesos";
	}
	//
	
	@RequestMapping("/registrarAdmin")
	public String registrar(@ModelAttribute @Valid Usuario objUsuario, BindingResult binRes, Model model)
			throws ParseException
	{
		if(binRes.hasErrors()) {
			return "monicovidAdminCrear";
		
		}
		else {
			
				boolean flag=uService.insertar(objUsuario);
				if(flag) {
					roleDAO.insertarRole(objUsuario.getAutoridad(), objUsuario.getId());
					return "redirect:/login";}
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/cuenta/Adminstrator-ir-Registrar";

				}
							
			}		
	}	
	@RequestMapping("/registrarUsuario")
	public String registrarUsuario(@ModelAttribute @Valid Usuario objUsuario, BindingResult binRes, Model model)
			throws ParseException
	{
		if(binRes.hasErrors()) {
			return "monicovidUsuarioCrear";
		
		}
		else {
			
				boolean flag=uService.insertar(objUsuario);
				if(flag) {
					roleDAO.insertarRole(objUsuario.getAutoridad(), objUsuario.getId());
					return "redirect:/login";}
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/cuenta/irRegistrar";

				}
							
			}		
	}
	
	@RequestMapping("/registrarMedico")
	public String registrarMedico(@ModelAttribute @Valid Usuario objUsuario, BindingResult binRes, Model model)
			throws ParseException
	{
		if(binRes.hasErrors()) {
			return "monicovidMedicoCrear";
		
		}
		else {
			
				boolean flag=uService.insertar(objUsuario);
				if(flag) {
					roleDAO.insertarRole(objUsuario.getAutoridad(), objUsuario.getId());
					return "redirect:/login";}
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/cuenta/ir-Medico-Registrar";

				}
							
			}		
	}	
	@RequestMapping("/lista")
	public String lista(Model model)
	{
		model.addAttribute("listaUsuarios", uService.listar());
		model.addAttribute("texto", "Lista de todos los Usuarios");
		sController.setMensaje(null);
		return "adminListUsuarios";
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Usuario objUsuario, BindingResult binRes, Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors()) {
			return "redirect://usuario/listar";
		
		}
		else {
			
				boolean flag=uService.insertar(objUsuario);
				if(flag) {
					roleDAO.insertarRole(objUsuario.getAutoridad(), objUsuario.getId());
					return "redirect:/usuario/lista";}
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/usuario/irRegistrar";

				}
							
			}		
	}
	@RequestMapping("/actualizarContraseña")
	public String actualizarContraseña(@ModelAttribute @Valid Usuario objUsuario, BindingResult binRes, Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors()) {
			return "redirect://sesion/irModificarContrasenia";
		
		}
		else {
			
				boolean flag=uService.insertar(objUsuario);
				if(flag) {
					return "redirect:/monicovid/index";}
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/sesion/irModificarContraseniar";

				}
							
			}		
	}
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Usuario>objUsuario=uService.buscarId(id);
		if(objUsuario==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://usuario/lista";
			
		}
		else
		{
			model.addAttribute("ciclos", uService.listarCiclos(1980,2999));
			if(objUsuario.isPresent())
				objUsuario.ifPresent(o->model.addAttribute("usuario", o));
			return "adminUsuario";
		}
	}	
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String,Object>model, @RequestParam(value="id")Long id){
		try {
			if(id!=null&&id>0) {
				uService.eliminar(id);
				model.put("listaUsuarios", uService.listar());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "Sucedio un error");
			model.put("listaUsuarios", uService.listar());
		}
		return "adminListUsuarios";
	}

	
	@RequestMapping("/back")
	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}
	
	@GetMapping("/crudDNIBuscar")
	public String buscarDNICRUD(@RequestParam("dni") String dni,Map<String, Object> model) {
		model.put("listaUsuarios", uService.buscarUsuario(dni));
		return "adminListUsuarios";
	}
	
	@GetMapping("/crudCodigoBuscar")
	public String buscarCodigoCRUD(@RequestParam("username") String username,Map<String, Object> model) {
		model.put("listaUsuarios", uService.buscarByCodigo(username.toUpperCase()));
		return "adminListUsuarios";
	}
	
	@GetMapping("/crudApellidosBuscar")
	public String buscarApellidoCRUD(@RequestParam("apellido") String apellido,Map<String, Object> model) {
		model.put("listaUsuarios", uService.buscarByApellidos(apellido.toUpperCase()));
		return "adminListUsuarios";
	}
	
	@GetMapping("/procesoDNIBuscar")
	public String buscarDNIProceso(@RequestParam("dni") String dni,Map<String, Object> model) {
		model.put("listaEstudiantes", uService.buscarEstudianteDNI(dni));
		return "adminListProcesos";
	}
	
	@GetMapping("/procesoCodigoBuscar")
	public String buscarCodigoProceso(@RequestParam("username") String username,Map<String, Object> model) {
		model.put("listaEstudiantes", uService.buscarEstudianteCodigo(username.toUpperCase()));
		return "adminListProcesos";
	}
	
	@GetMapping("/procesoApellidosBuscar")
	public String buscarApellidoProceso(@RequestParam("apellido") String apellido,Map<String, Object> model) {
		model.put("listaEstudiantes", uService.buscarEstudianteApellidos(apellido.toUpperCase()));
		return "adminListProcesos";
	}
}
