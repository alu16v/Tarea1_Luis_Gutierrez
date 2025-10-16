package modelo;
/**
* @author luisgb11
*/
public class Sesion {
	
	private String estadoLogin;
	private Perfil perfil;
	
	public Sesion(String estadoLogin, Perfil perfil) {
		super();
		this.estadoLogin = estadoLogin;
		this.perfil = perfil;
	}

	public String getEstadoLogin() {
		return estadoLogin;
	}

	public void setEstadoLogin(String estadoLogin) {
		this.estadoLogin = estadoLogin;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
	
	

}
