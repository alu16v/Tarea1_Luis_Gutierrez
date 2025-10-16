package modelo;

public abstract class Persona {  
	
	protected Long id; // UNIQUE
	protected String email; //UNIQUE
	protected String nombre;
	protected String nacionalidad;
	
	protected Credenciales credencial;
	
	public Persona(Long id, String email, String nombre, String nacionalidad, Credenciales credencial) {
		super();
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.credencial = credencial;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Credenciales getCredencial() {
		return credencial;
	}

	public void setCredencial(Credenciales credencial) {
		this.credencial = credencial;
	}
	
		
	
	
	
}
