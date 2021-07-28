package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.List;

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

import pe.akiramenai.project.unasam.spring.model.Oxigeno;
import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.service.IOxigenoService;
import pe.akiramenai.project.unasam.spring.service.IReporteService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;


@Controller
@RequestMapping("/oxigeno")
public class OxigenoController {


	@Autowired
	private IOxigenoService aService;
	
	@Autowired
	private IReporteService rService;
	
	@Autowired
	private IUsuarioService eService;//paciente
		
	@RequestMapping("/listar")
	public String listar(Map<String, Object>model)
	{
		model.put("listaOxigenacion", aService.listarOxigenacionbyUsername(eService.obtenerUsuario()));
		model.put("listaUsuarios", eService.listar());
		return "monicovidlistOxigenacion";
	}
	

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object>model, @ModelAttribute Oxigeno Proceso)
	{
		aService.listarId(Proceso.getIdOxigeno());
		return "monicovidlistOxigenacion";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model)
	{	
		Usuario usuario= eService.buscarPorUserName(eService.obtenerUsuario());
		Oxigeno oxigeno=new Oxigeno();
		Date dateActual=new Date();
		oxigeno.setFechaRegistro(dateActual);
		oxigeno.setPaciente(usuario);
		
		model.addAttribute("oxigeno", oxigeno);
		model.addAttribute("listaUsuarios", eService.listar());
		return "monicovidPacienteOxigeno";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Oxigeno objOxigeno, BindingResult binRes, Model model)
	throws ParseException
	{
		if(binRes.hasErrors()) 
		{
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteOxigeno";
		}
		else {

				boolean flag=aService.insertar(objOxigeno);
				if(flag) {
					List<Reporte> listReportes= rService.buscarporPacienteUserName(eService.obtenerUsuario());
					Reporte reporte = listReportes.get(listReportes.size()-1);
					reporte.setOxigeno(objOxigeno);
					rService.modificarReporteOxigenoPorUserName(objOxigeno, reporte.getId());
					return "redirect:/sintoma/irRegistrar";
					
				}
					
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/oxigeno/irRegistrar";

				}
							
			}			
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Oxigeno objOxigeno, BindingResult binRes, Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors())
			return "redirect://oxigeno/listar";
		else {
				boolean flag=aService.modificar(objOxigeno);
				if(flag) {
					objRedir.addFlashAttribute("mensaje", "Se modificó correctamente");
					return "redirect:/oxigeno/listar";
					}
				else {
					objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
					return "redirect:/oxigeno/irRegistrar";

				}
							
			}			
	}
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Oxigeno>obj=aService.buscarId(id);
		if(obj==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://oxigeno/listar";
			
		}
		else
		{
			if(obj.isPresent())
				obj.ifPresent(o-> model.addAttribute("oxigeno",o));
			model.addAttribute("listaOxigenacion", aService.listar());
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteOxigeno";
		}
	}	
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String,Object>model, @RequestParam(value="id")Long id){
		try {
			if(id!=null&&id>0) {
				aService.eliminar(id);
				model.put("listaOxigenacion", aService.listar());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "Sucedio un error");
			model.put("listaOxigenacion", aService.listar());
		}
		return "monicovidlistOxigenacion";
	}
	
	///////////////////GUIA///////////////////////////////////////////////////////////////
	@GetMapping("/form/{id}")
	public String newInvoice(@PathVariable(value = "id") Long id, Model model) {
		try {
			Optional<Usuario> usuario = eService.buscarId(id);
			if (!usuario.isPresent()) {
				model.addAttribute("info", "Usuario no existe");
				return "redirect:/oxigeno/listar";
			} else {
				Oxigeno Ox = new Oxigeno();
				Ox.setPaciente(usuario.get());
				
				Date dateActual=new Date();

				Ox.setFechaRegistro(dateActual);
				model.addAttribute("oxigeno", Ox);

				model.addAttribute("listaUsuarios", eService.listar());
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "monicovidPacienteOxigeno";
	}
}

