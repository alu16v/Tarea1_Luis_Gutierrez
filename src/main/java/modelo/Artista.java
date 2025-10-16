package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Artista extends Persona{
	
	
	private Long idArt; // UNIQUE
	private String apodo; // null
	private Set<Especialidad> Especialidad = new HashSet<>();
	private List<Numero> numeros = new ArrayList<>();

	public Artista(Long idArt, String email, String nombre, String nacionalidad, Credenciales credencial) {
		super(idArt, email, nombre, nacionalidad, credencial);
		this.apodo=null;
	}

	public Long getIdArt() {
		return idArt;
	}

	public void setIdArt(Long idArt) {
		this.idArt = idArt;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Set<Especialidad> getEspecialidad() {
		return Especialidad;
	}

	public void setEspecialidad(Set<Especialidad> especialidad) {
		Especialidad = especialidad;
	}
	
	// public void addEspecialidad(){}
	
	
	public void addNumero(Numero numero) {
		if(!numeros.contains(numero)) {
			numeros.add(numero);
		}else {
			System.out.println("El número ya está añadido al artista");
		}
		System.out.println("Número "+numero.getNombre()+" añadido al artista");
		
	}
	
	
}
