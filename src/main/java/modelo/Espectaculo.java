package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Espectaculo implements Serializable {
	
	private Long id;
	private String nombre;
	private LocalDate fechaini;
	private LocalDate fechafin;
	private Long idCoord; // de momento no hace falta
	private Set<Numero> numeros = new HashSet<>(); // de momento no hace falta
	
	// constructor
	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechafin) {
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
	}
	
	
	// getters y setters
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
	public LocalDate getFechaini() {
		return fechaini;
	}
	public void setFechaini(LocalDate fechaini) {
		this.fechaini = fechaini;
	}
	public LocalDate getFechafin() {
		return fechafin;
	}
	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}
	public Set<Numero> getNumeros() {
		return numeros;
	}
	public void setNumeros(Set<Numero> numeros) {
		this.numeros = numeros;
	}
	public Long getIdCoord() {
		return idCoord;
	}
	public void setIdCoord(Long idCoord) {
		this.idCoord = idCoord;
	}
	
	

	// logica
	
	public void addNumero(Numero numero) {
		if(!numeros.contains(numero)) {
			numeros.add(numero);
		}else {
			System.out.println("El número ya está en el espectáculo");
		}
		
		System.out.println("Número "+numero.getNombre()+" añadido con éxito al espectáculo");
	}
	
	
}
