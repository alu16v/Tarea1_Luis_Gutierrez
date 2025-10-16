package modelo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Numero {

	private Long id;
	private int orden;
	private String nombre;
	private double duracion;
	private Set<Artista> artistas = new HashSet<>();
	private Long idEspectaculo;
	
	// constructor
	public Numero() {}
	
	
	public Numero(Long id, int orden, String nombre, double duracion) {
		this.id = id;
		this.orden = orden;
		this.nombre = nombre;
		this.duracion = duracion;
	}
	
	
	// getters y setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	public Set<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}
	
	// logica
	
	public void addArtista(Artista artista) {
		if(!artistas.contains(artista)) {
			artistas.add(artista);
		}else {
			System.out.println("El artista ya participa");
		}
		System.out.println("Artista "+artista.getNombre()+" añadido con éxito");
		
	}
	
	
}
