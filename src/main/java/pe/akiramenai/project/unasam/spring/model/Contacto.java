package pe.akiramenai.project.unasam.spring.model;

import java.io.Serializable;
//import java.time.ZonedDateTime;
//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Contacto")
public class Contacto implements Serializable{

	private static final long serialVersionUID=2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idContacto;
	
	@ManyToOne
	@JoinColumn(name="idPaciente")
	private Usuario paciente;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="numero", nullable=false)
	private String numero;

	public Contacto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contacto(Long idContacto, Usuario paciente, String nombre, String numero) {
		super();
		this.idContacto = idContacto;
		this.paciente = paciente;
		this.nombre = nombre;
		this.numero = numero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Long getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}
		
}
