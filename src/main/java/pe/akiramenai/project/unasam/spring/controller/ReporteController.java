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

import pe.akiramenai.project.unasam.spring.model.Reporte;
import pe.akiramenai.project.unasam.spring.model.Usuario;
import pe.akiramenai.project.unasam.spring.repository.IRoleDAO;
import pe.akiramenai.project.unasam.spring.service.IReporteService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;

@Controller
@RequestMapping("/reporte")
public class ReporteController {

	@Autowired
	private IUsuarioService uService;
	
	@Autowired
	private IReporteService rService;
	
	private String back;
	
	@RequestMapping("/verReportes")
	public String verReportes(Model model)
	{
		model.addAttribute("listaReportes", rService.buscarporPacienteUserNameOrdenado(uService.obtenerUsuario()));
		return "monicovidPacienteVerReportes";
	}
	
	@RequestMapping("/verReportesSintoma/{id}")
	public String verReportesSintoma(@PathVariable Long id, Model model, RedirectAttributes objRedir)
	throws ParseException{
		
		Optional<Reporte>objReporte=rService.buscarId(id);
		if(objReporte==null)
		{
			objRedir.addFlashAttribute("mensaje", "Ocurrió un error al cargar");
			return "redirect://reporte/verReportes";
			
		}
		else
		{
			model.addAttribute("ciclos", uService.listarCiclos(1980,2999));
			if(objReporte.isPresent())
				objReporte.ifPresent(o->model.addAttribute("reporte", o));
			
			return "monicovidPacienteVerSintomas";
		}
	}
		
	@RequestMapping("/back")
	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}
	
}
