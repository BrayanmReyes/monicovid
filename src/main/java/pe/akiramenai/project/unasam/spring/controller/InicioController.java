package pe.akiramenai.project.unasam.spring.controller;

import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicio")
public class InicioController {
	@RequestMapping("/bienvenido")
	public String perfil(Model model) 
	throws ParseException{
		return "bienvenido";
	}

}
