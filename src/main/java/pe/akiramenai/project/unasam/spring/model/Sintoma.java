package pe.akiramenai.project.unasam.spring.model;

import java.io.Serializable;
//import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="Sintoma")
public class Sintoma implements Serializable{

	private static final long serialVersionUID=2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idSintoma;
	
	@ManyToOne
	@JoinColumn(name="idPaciente")
	private Usuario paciente;
	
	@Column(name="valor", nullable=true)
	private String valor;
	
	@Column(name="iscontacto", nullable=true)
	private String iscontacto;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name="fechaRegistro",nullable = false)
	private Date  fechaRegistro;

	public Sintoma() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Sintoma(Long idSintoma, Usuario paciente, String valor, String iscontacto, Date fechaRegistro) {
		super();
		this.idSintoma = idSintoma;
		this.paciente = paciente;
		this.valor = valor;
		this.iscontacto = iscontacto;
		this.fechaRegistro = fechaRegistro;
	}



	public Long getIdSintoma() {
		return idSintoma;
	}

	public void setIdSintoma(Long idSintoma) {
		this.idSintoma = idSintoma;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}



	public String getIscontacto() {
		return iscontacto;
	}



	public void setIscontacto(String iscontacto) {
		this.iscontacto = iscontacto;
	}
	

		
}
