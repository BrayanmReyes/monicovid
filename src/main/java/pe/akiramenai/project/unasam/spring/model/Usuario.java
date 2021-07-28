package pe.akiramenai.project.unasam.spring.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@NotBlank(message="No puede estar vacío")
	private String nombre;
	
	@Column
	@NotBlank(message="No puede estar vacío")
	private String apellido;
	
	@Column(unique = true)
	@NotBlank(message="No puede estar vacío")
	@Size(min=8,message="El dni debe tener 8 caracteres")
	private String dni;
	
	@Column
	private String email;
	
	@Column(name="numero", nullable=true)
	private String numero;
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	@Column
	@NotBlank(message="No puede estar vacío")
	private String direccion;
	
	@Column(unique = true)
	@Email(message="coloque un email valido")
	@NotBlank(message="No puede estar vacío")
	private String username;

	@Column
	@NotBlank(message="No puede estar vacío")
	private String password;

	@Transient
	private String confirmPassword;
	
	@Column
	@NotNull
	private boolean enable;
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Role> roles;
	
	@Column
	@NotBlank(message="No puede estar vacío")
	private String autoridad;
	

	public Usuario(Long id) {
		super();
		this.id = id;
	}

	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getAutoridad() {
		return autoridad;
	}
	public void setAutoridad(String autoridad) {
		this.autoridad = autoridad;
	}
		
}
