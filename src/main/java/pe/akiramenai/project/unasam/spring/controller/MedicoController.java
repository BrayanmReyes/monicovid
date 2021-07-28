package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;
import java.util.List;
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
import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.repository.IRoleDAO;
import pe.akiramenai.project.unasam.spring.service.IReporteService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;

@Controller
@RequestMapping("/medico")
public class MedicoController {

	@Autowired
	private IUsuarioService uService;
	
	@Autowired
	private IReporteService rService;
	
	@Autowired
	private SesionController sController;
	
	private List<Reporte> listReportes;
	
	@RequestMapping("/verReportes")
	public String verReportes(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaReportes", null);
		return "monicovidMedicoVerReportes";
	}
	//test
	@RequestMapping("/verReportesTest")
	public String verReportesTest(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaReportes", null);
		return "monicovidTest";
	}
	
	
	@RequestMapping("/verReportesPaciente")
	public String verReportesPaciente(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaReportes", listReportes);
		return "monicovidMedicoVerReportes";
	}
	
	//test
	@RequestMapping("/test")
	public String verTest(Model model)
	{
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("listaReportes", listReportes);
		return "monicovidTest";
	}


	@RequestMapping("/buscar")
	public String buscar(@ModelAttribute Usuario objUsuario, BindingResult binRes, Model model)
			throws ParseException
	{
		if(binRes.hasErrors()) {
			return "monicovidMedicoVerReportes";
		
		}
		else {
				List<Reporte> reportes=rService.buscarporPacienteDNIOrdenado(objUsuario.getDni());
				boolean flag=false;
				
				if(reportes.size()>0)
					flag =true;
				
				if(flag) {
					
					model.addAttribute("listaReportes", reportes);
					listReportes = reportes;
					return "redirect:/medico/verReportesPaciente";}
				else {
					model.addAttribute("mensaje", "No se han encontrado resultados");
					return "redirect:/medico/verReportes";
				}							
			}		
	}	
	
	//test
	@RequestMapping("/buscarTest")
	public String buscarTest(@ModelAttribute Usuario objUsuario, BindingResult binRes, Model model)
			throws ParseException
	{
		if(binRes.hasErrors()) {
			return "monicovidMedicoVerReportes";
		
		}
		else {
				List<Reporte> reportes=rService.buscarporPacienteDNI(objUsuario.getDni());
				boolean flag=false;
				
				if(reportes.size()>0)
					flag =true;
				
				if(flag) {
					
					model.addAttribute("listaReportes", reportes);
					listReportes = reportes;
					return "redirect:/medico/test";}
				else {
					model.addAttribute("mensaje", "No se han encontrado resultados");
					return "redirect:/medico/verReportes";
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
	
}
