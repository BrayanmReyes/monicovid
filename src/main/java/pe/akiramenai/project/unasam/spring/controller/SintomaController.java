package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
import pe.akiramenai.project.unasam.spring.model.Sintoma;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.service.IOxigenoService;
import pe.akiramenai.project.unasam.spring.service.IReporteService;
import pe.akiramenai.project.unasam.spring.service.ISintomaService;
import pe.akiramenai.project.unasam.spring.service.ITemperaturaService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;


@Controller
@RequestMapping("/sintoma")
public class SintomaController {


	@Autowired
	private ITemperaturaService tService;
	
	@Autowired
	private IOxigenoService oService;
	
	@Autowired
	private ISintomaService sService;
	
	@Autowired
	private IReporteService rService;
	
	@Autowired
	private IUsuarioService eService;//paciente
	
	@RequestMapping("/listar")
	public String listar(Map<String, Object>model)
	{
		model.put("listaSintomas", sService.listarSintomasbyUsername(eService.obtenerUsuario()));
		model.put("listaUsuarios", eService.listar());
		return "monicovidlistSintomas";
	}
	

	@RequestMapping("/listarId")
	public String listarId(Map<String, Object>model, @ModelAttribute Sintoma Proceso)
	{
		sService.listarId(Proceso.getIdSintoma());
		return "monicovidlistSintomas";
	}
	
	@RequestMapping("/irRegistrar")
	public String irRegistrar(Model model)
	{	
		Usuario usuario= eService.buscarPorUserName(eService.obtenerUsuario());
		Sintoma sintoma=new Sintoma();
		Date dateActual=new Date();
		sintoma.setFechaRegistro(dateActual);
		sintoma.setPaciente(usuario);
				
		model.addAttribute("sintoma", sintoma);
		model.addAttribute("listaUsuarios", eService.listar());
		return "monicovidPacienteSintoma";
	}
	
	@RequestMapping("/registrar")
	public String registrar(@ModelAttribute @Valid Sintoma objSintoma, BindingResult binRes, Model model)
	throws ParseException
	{
		if(binRes.hasErrors()) 
		{
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteSintoma";
		}
		else {
				
				System.out.println(objSintoma.getValor());
				boolean flag=sService.insertar(objSintoma);
				if(flag) {
					List<Reporte> listReportes= rService.buscarporPacienteUserName(eService.obtenerUsuario());
					Reporte reporte = listReportes.get(listReportes.size()-1);
					reporte.setSintoma(objSintoma);
					rService.modificarReporteSintomaPorUserName(objSintoma, reporte.getId());
					if(tService.isComplicacionTemperatura(eService.obtenerUsuario()) || oService.isComplicacionOxigeno(eService.obtenerUsuario()))
						return "redirect:/graph/irAlerta";
					else
						return "redirect:/usuario/verReportes";
				}
					
				else {
					model.addAttribute("mensaje", "Ocurrió un error");
					return "redirect:/sintoma/irRegistrar";

				}
							
			}			
	}
	
	@RequestMapping("/actualizar")
	public String actualizar(@ModelAttribute @Valid Sintoma objSintoma, BindingResult binRes, Model model, RedirectAttributes objRedir)
	throws ParseException
	{
		if(binRes.hasErrors())
			return "redirect://sintoma/listar";
		else {
				boolean flag=sService.modificar(objSintoma);
				if(flag) {
					objRedir.addFlashAttribute("mensaje", "Se modificó correctamente");
					return "redirect:/sintoma/listar";
					}
				else {
					objRedir.addFlashAttribute("mensaje", "Ocurrió un error");
					return "redirect:/sintoma/irRegistrar";

				}
							
			}			
	}
	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Sintoma>obj=sService.buscarId(id);
		if(obj==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://sintoma/listar";
			
		}
		else
		{
			if(obj.isPresent())
				obj.ifPresent(o-> model.addAttribute("sintoma",o));
			model.addAttribute("listaSintomas", sService.listar());
			model.addAttribute("listaUsuarios", eService.listar());
			return "monicovidPacienteSintoma";
		}
	}	
	
	@RequestMapping("/eliminar")
	public String eliminar(Map<String,Object>model, @RequestParam(value="id")Long id){
		try {
			if(id!=null&&id>0) {
				sService.eliminar(id);
				model.put("listaSintomas", sService.listar());
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			model.put("mensaje", "Sucedio un error");
			model.put("listaSintomas", sService.listar());
		}
		return "monicovidlistSintomas";
	}
	
	///////////////////GUIA///////////////////////////////////////////////////////////////
	@GetMapping("/form/{id}")
	public String newInvoice(@PathVariable(value = "id") Long id, Model model) {
		try {
			Optional<Usuario> usuario = eService.buscarId(id);
			if (!usuario.isPresent()) {
				model.addAttribute("info", "Usuario no existe");
				return "redirect:/sintoma/listar";
			} else {
				Sintoma Sint = new Sintoma();
				Sint.setPaciente(usuario.get());
				
				Date dateActual=new Date();

				Sint.setFechaRegistro(dateActual);
				model.addAttribute("sintoma", Sint);

				model.addAttribute("listaUsuarios", eService.listar());
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "monicovidPacienteSintoma";
	}
}

