package pe.akiramenai.project.unasam.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class ProjectUnasamApplication implements CommandLineRunner{

	private String nuevaPassword;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectUnasamApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		
		String padmin="admin";
		String palumno="usuario";

		
			String bcryptPassword =passwordEncoder.encode(padmin);
			System.out.println(bcryptPassword);
			String bcryptPassword2 =passwordEncoder.encode(palumno);
			System.out.println(bcryptPassword2);

	} 
	
	public String getNuevaPassword() {
		return nuevaPassword;
	}

	
	public void setNuevaPassword(String nuevaPassword) {
		this.nuevaPassword = nuevaPassword;
	}

	public String encriptar(String password) {
		return passwordEncoder.encode(password);
	}

}
