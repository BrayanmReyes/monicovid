package pe.akiramenai.project.unasam.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.akiramenai.project.unasam.spring.model.Oxigeno;
import pe.akiramenai.project.unasam.spring.service.IOxigenoService;


@RestController
@RequestMapping("api/oxigenacion")
public class RestOxigenoController {

	@Autowired
	private IOxigenoService aService;
		
	@RequestMapping("/paciente")
	public String OxigenoPaciente(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		
		List<Oxigeno> foo = aService.listarOxigenacionbyUsernameOrdenado(userName);
		String json = new Gson().toJson(foo);
		return json;
	}
	
}

