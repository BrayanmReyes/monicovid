package pe.akiramenai.project.unasam.spring.model;

import java.io.Serializable;
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
@Table(name="Temperatura")
public class Temperatura implements Serializable{

	private static final long serialVersionUID=2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idTemperatura;
	
	@ManyToOne
	@JoinColumn(name="idPaciente")
	private Usuario paciente;
	
	@Column(name="valor", nullable=true)
	private double valor;
	

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name="fechaRegistro",nullable = false)
	private Date fechaRegistro;

	public Temperatura() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Temperatura(Long idTemperatura, Usuario paciente, double valor, Date fechaRegistro) {
		super();
		this.idTemperatura = idTemperatura;
		this.paciente = paciente;
		this.valor = valor;
		this.fechaRegistro = fechaRegistro;
	}

	public Long getIdTemperatura() {
		return idTemperatura;
	}

	public void setIdTemperatura(Long idTemperatura) {
		this.idTemperatura = idTemperatura;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

		
}
