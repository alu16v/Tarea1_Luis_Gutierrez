package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Espectaculo implements  Serializable, Comparator<Espectaculo>{
	
	private Long id = 1L;
	private String nombre;
	private LocalDate fechaini;
	private LocalDate fechafin;
	private Long idCoord;
	private Set<Numero> numeros = new HashSet<>(); // de momento no hace falta
	
	// constructor
	public Espectaculo(Long id, String nombre, LocalDate fechaini, LocalDate fechafin, Long idCoord) {
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.idCoord = idCoord;
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
	
	@Override
	public String toString() {
		DateTimeFormatter formatoEspaniol = DateTimeFormatter
		        .ofPattern("d/MM/uuuu");
		String fechainicio = fechaini.format(formatoEspaniol);
		String fechafinal = fechafin.format(formatoEspaniol);
		return "- Nombre: " + nombre + ", Fecha de inicio: " + fechainicio + ", Fecha de fin: " + fechafinal;
	}


	public void addNumero(Numero numero) {
		if(!numeros.contains(numero)) {
			numeros.add(numero);
		}else {
			System.out.println("El número ya está en el espectáculo");
		}
		
		System.out.println("Número "+numero.getNombre()+" añadido con éxito al espectáculo");
	}


	
	
	@Override
	public int compare(Espectaculo e1, Espectaculo e2) {
		
		if(ChronoUnit.DAYS.between(e1.getFechaini(), e2.getFechaini())>0){
			// segunda fecha mayor que la primera
			return -1;
		}else if(ChronoUnit.DAYS.between(e1.getFechaini(), e2.getFechaini())==0){
			// misma fecha de inicio, orden alfabético
			return e1.getNombre().compareTo(e2.getNombre());
		}else {
			// primera fecha mayor que la segunda
			return 1;
		}
	}
	
	
	
}
