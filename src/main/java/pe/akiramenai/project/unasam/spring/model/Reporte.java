package pe.akiramenai.project.unasam.spring.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Reporte")
public class Reporte implements Serializable{

	private static final long serialVersionUID=2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="temperatura", nullable=true)
	private Temperatura temperatura;

	@ManyToOne
	@JoinColumn(name="oxigeno", nullable=true)
	private Oxigeno oxigeno;

	@ManyToOne
	@JoinColumn(name="sintoma", nullable=true)
	private Sintoma sintoma;

	public Reporte() {
		super();
		// TODO Auto-generated constructor stub
	}

	



	public Reporte(Long id, Temperatura temperatura, Oxigeno oxigeno, Sintoma sintoma) {
		super();
		this.id = id;
		this.temperatura = temperatura;
		this.oxigeno = oxigeno;
		this.sintoma = sintoma;
	}





	public Temperatura getTemperatura() {
		return temperatura;
	}



	public void setTemperatura(Temperatura temperatura) {
		this.temperatura = temperatura;
	}



	public Oxigeno getOxigeno() {
		return oxigeno;
	}



	public void setOxigeno(Oxigeno oxigeno) {
		this.oxigeno = oxigeno;
	}



	public Sintoma getSintoma() {
		return sintoma;
	}



	public void setSintoma(Sintoma sintoma) {
		this.sintoma = sintoma;
	}





	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}


		
}
