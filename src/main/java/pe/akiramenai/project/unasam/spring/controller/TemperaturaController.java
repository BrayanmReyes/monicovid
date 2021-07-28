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

import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.model.Temperatura;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.service.IReporteService;
import pe.akiramenai.project.unasam.spring.service.ITemperaturaService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;


@Controller
@RequestMapping("/temperatura")
public class TemperaturaController {


	@Autowired
	private ITemperaturaService aService;
	
	@Autowired
	private IReporteService rService;
	
	@Autowired
	private IUsuarioService eService;//paciente
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object>model)
	{	
		model.put("listaUsuarios", eService.listar());
		model.put("listaTemperaturas", aService.listarTemperaturabyUsername(eService.obtenerUsuario()));	
		//model.put("listaUsuarios", eService.buscarUsuario("72792847"));
		return "monicovidlistTemperaturas";
	}

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object>model, @ModelAttribute Temperatura Proceso)
	{
		aService.listarId(Proceso.getIdTemperatura());
		return "monicovidlistTemperaturas";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model)
	{	
		Temperatura temperatura=new Temperatura();
		Usuario usuario= eService.buscarPorUserName(eService.obtenerUsuario());
		//Usuario usuario= eService.getUsuario();
		Date dateActual=new Date();
		temperatura.setFechaRegistro(dateActual);
		temperatura.setPaciente(usuario);
		
		model.addAttribute("temperatura", temperatura);
		model.addAttribute("listaUsuarios", eService.listar());
		return "monicovidPacienteTemperatura";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Temperatura objTemperatura, BindingResult binRes, Model model)
	throws ParseException
	{
		if(binRes.hasErrors()) 
		{
			//model.addAttribute("listaAsesors", rService.listarAsesores());
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteTemperatura";
		}
		else {

				boolean flag=aService.insertar(objTemperatura);
				
				if(flag) {
					Reporte reporte= new Reporte();
					reporte.setTemperatura(objTemperatura);
					rService.insertar(reporte);
					return "redirect:/oxigeno/irRegistrar";
				}
					
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/temperatura/irRegistrar";
				}			
			}			
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Temperatura objTemperatura, BindingResult binRes, Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors())
			return "redirect://temperatura/listar";
		else {
				boolean flag=aService.modificar(objTemperatura);
				if(flag) {
					objRedir.addFlashAttribute("mensaje", "Se modificó correctamente");
					return "redirect:/temperatura/listar";
					}
				else {
					objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
					return "redirect:/temperatura/irRegistrar";

				}
							
			}			
	}
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Temperatura>objTemperatura=aService.buscarId(id);
		if(objTemperatura==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://temperatura/listar";
			
		}
		else
		{
			if(objTemperatura.isPresent())
				objTemperatura.ifPresent(o-> model.addAttribute("temperatura",o));
			model.addAttribute("listaTemperaturas", aService.listar());
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteTemperatura";
		}
	}	
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String,Object>model, @RequestParam(value="id")Long id){
		try {
			if(id!=null&&id>0) {
				aService.eliminar(id);
				model.put("listaTemperaturas", aService.listar());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "Sucedio un error");
			model.put("listaTemperaturas", aService.listar());
		}
		return "monicovidlistTemperaturas";
	}
	
	@GetMapping("/form/{id}")
	public String newInvoice(@PathVariable(value = "id") Long id, Model model) {
		try {
			Optional<Usuario> usuario = eService.buscarId(id);
			if (!usuario.isPresent()) {
				model.addAttribute("info", "Usuario no existe");
				return "redirect:/temperatura/listar";
			} else {
				Temperatura Temperatura = new Temperatura();
				Temperatura.setPaciente(usuario.get());
				//Date date=new Date();
				//DateFormat hourdateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
				// cod= String.valueOf(hourdateFormat.format(date));
				//Proceso.setCodigoProceso(cod+"-PR");
				Date dateActual=new Date();
				//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Temperatura.setFechaRegistro(dateActual);
				model.addAttribute("temperatura", Temperatura);
				//model.addAttribute("listaAsesors", rService.listarAsesores());
				model.addAttribute("listaUsuarios", eService.listar());
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "monicovidPacienteTemperatura";
	}
}

