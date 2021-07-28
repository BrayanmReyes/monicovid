package pe.akiramenai.project.unasam.spring.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.akiramenai.project.unasam.spring.service.IContactoService;
import pe.akiramenai.project.unasam.spring.service.ITemperaturaService;
import pe.akiramenai.project.unasam.spring.service.IUsuarioService;


@Controller
@RequestMapping("/graph")
public class GraphController {
	@Autowired
	private ITemperaturaService aService;
	
	@Autowired
	private IContactoService cService;
	
	@Autowired
	private IUsuarioService eService;//paciente
	
	@Autowired
	private MessageController mController;//paciente
	
	
	@RequestMapping("/reporteTemperatura")
	public String listarTemperaturasUsuario(Map<String, Object>model)
	{
		model.put("listaTemperaturas", aService.listar());
		model.put("listaUsuarios", eService.listar());
		return "monicovidReporteTemperaturaUsuario";
	}
	
	@RequestMapping("/reporteOxigenacion")
	public String listarOxigenoUsuario(Map<String, Object>model)
	{
		model.put("listaTemperaturas", aService.listar());
		model.put("listaUsuarios", eService.listar());
		return "monicovidReporteOxigenoUsuario";
	}
	
	@RequestMapping("/irAlerta")
	public String irEnviarSMS(Map<String, Object>model)
	{	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		
		
		model.put("listaContactos", cService.listarContactosbyUsername(userName));
		model.put("listaUsuarios", eService.listar());

		mController.testSMS();
		
		return "monicovidAlertaComplicaciones";
	}
}

