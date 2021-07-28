package pe.akiramenai.project.unasam.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration

public class MvcConfig extends WebMvcConfigurerAdapter{

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error404").setViewName("error404");
	}
}
