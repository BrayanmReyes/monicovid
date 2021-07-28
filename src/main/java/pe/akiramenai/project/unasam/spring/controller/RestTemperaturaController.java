package pe.akiramenai.project.unasam.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import pe.akiramenai.project.unasam.spring.model.Temperatura;
import pe.akiramenai.project.unasam.spring.service.ITemperaturaService;

@RestController
@RequestMapping("api/temperatura")
public class RestTemperaturaController {


	@Autowired
	private ITemperaturaService aService;
	
	@RequestMapping("/paciente")
	public String TemperaturaPaciente(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		
		List<Temperatura> foo = aService.listarTemperaturabyUsernameOrdenado(userName);
		String json = new Gson().toJson(foo);
		return json;
	}
		
	@RequestMapping(value="findall", method=RequestMethod.GET,
			produces= {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<Temperatura>> findAll() {
		try {
			return new ResponseEntity<Iterable<Temperatura>>(aService.listar(),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Iterable<Temperatura>>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="findPaciente}", method=RequestMethod.GET,
			produces= {MimeTypeUtils.APPLICATION_JSON_VALUE})
	public ResponseEntity<Iterable<Temperatura>> findAllByUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			  userDetails = (UserDetails) principal;
			}
		String userName = userDetails.getUsername();
		
		try {			
			return new ResponseEntity<Iterable<Temperatura>>(aService.listarTemperaturabyUsername(userName),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Iterable<Temperatura>>(HttpStatus.BAD_REQUEST);
		}
	}

}

