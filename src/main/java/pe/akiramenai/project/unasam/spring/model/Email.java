package pe.akiramenai.project.unasam.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Email")
public class Email implements Serializable{

	private static final long serialVersionUID=2L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEmail;
	
	@Column(name="fromEmail", nullable=true)
	private String fromEmail;

	@Column(name="toEmail", nullable=true)
	private String toEmail;

	@Column(name="asuntoEmail", nullable=true)
	private String asuntoEmail;
	
	@Column(name="mensajeEmail", nullable=true)
	private String mensajeEmail;

	@Column(name="passwordEmail", nullable=true)
	private String passwordEmail;

	public Email() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Email(int idEmail, String fromEmail, String toEmail, String asuntoEmail, String mensajeEmail,
			String passwordEmail) {
		super();
		this.idEmail = idEmail;
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.asuntoEmail = asuntoEmail;
		this.mensajeEmail = mensajeEmail;
		this.passwordEmail = passwordEmail;
	}




	public int getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(int idEmail) {
		this.idEmail = idEmail;
	}
	
	public String getPasswordEmail() {
		return passwordEmail;
	}

	public void setPasswordEmail(String passwordEmail) {
		this.passwordEmail = passwordEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getAsuntoEmail() {
		return asuntoEmail;
	}

	public void setAsuntoEmail(String asuntoEmail) {
		this.asuntoEmail = asuntoEmail;
	}

	public String getMensajeEmail() {
		return mensajeEmail;
	}

	public void setMensajeEmail(String mensajeEmail) {
		this.mensajeEmail = mensajeEmail;
	}
		
}
