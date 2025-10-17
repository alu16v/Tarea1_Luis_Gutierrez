package modelo;

import java.time.LocalDate;

public class Coordinacion extends Persona {
	
	private Long idCoord; // UNIQUE
	private boolean senior = false; 
	private LocalDate fechasenior = null; 
	
	public Coordinacion(Long idCoord, String email, String nombre, String nacionalidad, Credenciales credencial,
			 boolean senior, LocalDate fechasenior) {
		super(idCoord, email, nombre, nacionalidad, credencial);
		this.senior = senior;
		this.fechasenior = fechasenior;
	}

	public Long getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Long idCoord) {
		this.idCoord = idCoord;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public LocalDate getFechasenior() {
		if(fechasenior!=null) {
			return fechasenior;
		}else {
			// devuelve la menor fecha posible para mostrar que no está registrada
			// y que no arroje una NullPointerException
			return LocalDate.ofEpochDay(0);
		}
	}

	public void setFechasenior(LocalDate fechasenior) {
		this.fechasenior = fechasenior;
	}
	
	
	
}
