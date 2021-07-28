package pe.akiramenai.project.unasam.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pe.akiramenai.project.unasam.spring.auth.handler.LoginSuccessHandler;
import pe.akiramenai.project.unasam.spring.serviceimpl.JpaUserDetailsService;;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private JpaUserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		try {
			http.authorizeRequests()
					.antMatchers("/cuenta/**").permitAll()
					.antMatchers("/api/**").permitAll()
					.antMatchers("/usuario/**").access("hasRole('ROLE_ADMIN')")
					.antMatchers("/estudiante/**").access("hasRole('ROLE_ESTUDIANTE')")
					.antMatchers("/asesor/**").access("hasRole('ROLE_ASESOR')")
					.antMatchers("/juez/**").access("hasRole('ROLE_JUEZ')").and()
					.formLogin().successHandler(successHandler).loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/monicovid/index")
					.permitAll().and().logout().logoutSuccessUrl("/login").permitAll().and().exceptionHandling().accessDeniedPage("/error_403");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

	}	
}
