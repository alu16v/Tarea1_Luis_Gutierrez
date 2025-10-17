package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import modelo.Artista;
import modelo.Coordinacion;
import modelo.Credenciales;
import modelo.Especialidad;
import modelo.Perfil;
import modelo.Persona;
import modelo.Sesion;
import utils.BuclesGenericos;
import utils.Utilidades;

/**
* @author luisgb11
*/
public class Main {

	// maneja todos los inicios de sesión del programa
	public static Sesion inicioSesion() {
		Scanner in = new Scanner(System.in);
		String raiz = Thread.currentThread().getContextClassLoader().
					getResource("").getPath()+"application.properties";
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(raiz));
		} catch (IOException notFound) {
			notFound.printStackTrace();
		}
		String usuarioAdmin = props.getProperty("usuarioAdmin");
		String passAdmin = props.getProperty("passwordAdmin");
		
		Sesion resultado = new Sesion("no iniciada", null);
		boolean inicio=false;
			do {
				System.out.println("Introduzca su nombre de usuario: (Pulsar 0 para cancelar)");
				String usuario = in.next();
				if(usuario.equals("0")) {
					inicio = true; // cuenta como sesión iniciada, pero con estado 0 (intento de inicio fallido) 
					break;
				}
				System.out.println("Introduzca su contraseña:");
				String contra = in.next();
				
				if(usuario.equals(usuarioAdmin)&& contra.equals(passAdmin)) { 
					resultado = new Sesion("iniciada como admin", Perfil.Admin);
					System.out.println("Sesión iniciada como: "+resultado.getPerfil().toString());
					inicio = true;
				}else {
					resultado = confirmarCredenciales(leerCredenciales(), usuario, contra);
					// si devuelve null, el usuario ha introducido algún valor incorrecto
					// y no abandona el bucle de esta forma
					if(resultado.getPerfil()!=null) { 
						System.out.println("Sesión iniciada como: "+resultado.getPerfil().toString());
						inicio = true;
					}else {
						System.out.println("Usuario y/o contraseña incorrectos");
					}
				}
			}while(!inicio);
		return resultado;
	}
	
	/* 
	   navega el fichero credenciales.txt para ver los usuarios y devuelve
	   un mapa cuya clave es el usuario, y el valor es el contenido de su linea
	 */
	public static HashMap<String,List<String>> leerCredenciales() {
		String raiz = Thread.currentThread().getContextClassLoader().
				getResource("").getPath()+"application.properties";
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(raiz));
		} catch (IOException notFound) {
			notFound.printStackTrace();
		}
		
		File creds = new File(props.getProperty("ficherocredenciales"));
		HashMap<String,List<String>> mapaCreds = new HashMap<>();
		
		
		try(BufferedReader br = new BufferedReader(new FileReader(creds))) {
			
			String linea;
			while((linea=br.readLine())!=null) {
				String[] usuario = linea.split("\\|");
				List<String> camposUsuario = Arrays.asList(usuario);
				mapaCreds.put(usuario[1], camposUsuario);
			}
			
		}catch(FileNotFoundException fnf) {
			System.out.println("Fichero credenciales no encontrado: "+fnf.getLocalizedMessage());
		}catch(IOException ioe) {
			System.out.println("Error al leer el fichero: "+ioe.getLocalizedMessage());
		}
		return mapaCreds;
	}
	
	
	public static Sesion confirmarCredenciales(HashMap<String,List<String>> mapaCreds, 
												String username, String userpass ) {
		
		Sesion resultadoLogin = new Sesion("no iniciada", null);
		for (Map.Entry<String, List<String>> lineaCred : mapaCreds.entrySet()) {
			String usuario = lineaCred.getKey();
			String pass = lineaCred.getValue().get(2);
			int posicionProfesion = lineaCred.getValue().size()-1;
			
			if(username.equals(usuario) && userpass.equals(pass)) {
				if(lineaCred.getValue().get(posicionProfesion).toLowerCase().equals("coordinacion")) {
					resultadoLogin = new Sesion("iniciada", Perfil.Coordinacion);
					break;
				}else if(lineaCred.getValue().get(posicionProfesion).toLowerCase().equals("artista")) {
					resultadoLogin = new Sesion("iniciada", Perfil.Artista);
					break;
				}
			}
		}
		return resultadoLogin;
	}
	
	
	public static HashMap<String, String> leerNacionalidades() {
		Properties props = new Properties();
		String raiz = Thread.currentThread().getContextClassLoader().
				getResource("").getPath()+"application.properties";
		try{
			props.load(new FileInputStream(raiz));
		} catch (IOException ioe) {
			System.out.println("Error al cargar el fichero:");
			ioe.printStackTrace();
		}
		
		String ficheroNac = props.getProperty("ficheropaises");
		HashMap<String, String> nacionalidades = new HashMap<>();
		
		try {
			DocumentBuilderFactory fabricalectorDoc = DocumentBuilderFactory.newInstance();
			DocumentBuilder lectorDoc = fabricalectorDoc.newDocumentBuilder();
			
			File docPaises = new File(ficheroNac);
			Document documento = lectorDoc.parse(docPaises);
			NodeList listaPaises, listaPais;
			Element paises, pais, id, nombre;
			int indicePaises=0, indicePais;
			
			listaPaises = documento.getElementsByTagName("paises");
			indicePaises = 0;
			
			while(indicePaises < listaPaises.getLength()) {
				paises = (Element) listaPaises.item(indicePaises);
				listaPais = documento.getElementsByTagName("pais");
				indicePais = 0;
				
				while(indicePais < listaPais.getLength()) {
					pais = (Element) listaPais.item(indicePais);
					id = (Element) pais.getElementsByTagName("id").item(0);
					String letrasPais = id.getTextContent();
					
					nombre = (Element) pais.getElementsByTagName("nombre").item(0);
					String nombrePais = nombre.getTextContent();
					
					indicePais++;
					nacionalidades.put(letrasPais, nombrePais);
				}
				indicePaises++;
			}
			
			
		}catch (ParserConfigurationException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return nacionalidades;
	}
	
	
	
	
	
	// prompt para confirmar con si/no la salida y volver al menú principal en caso negativo
	public static boolean confirmarSalida() {
		try (Scanner in = new Scanner(System.in)) {
			String salida;
			boolean salir=false;
			boolean check = false;
			do {
				System.out.println("¿Está seguro de que desea salir? (Si/No)");
				salida = in.next().trim().toLowerCase();
				if(salida.equals("s") || salida.equals("si")) {
					check = true;
					System.out.println("Gracias por usar nuestro programa. Hasta luego!");
					salir= true;
				}else if(salida.equals("n") || salida.equals("no")){
					System.out.println("Volviendo al menú principal...");
					check = true;
				}
			}while(!check);
			return salir;
		}
	}
	
	
	
	
	
	/* esto es terriblemente ineficiente pero todavía no he pensado en una mejor forma
	   de hacer diferentes opciones en el mismo menú y switch
	*/
	public static void menuCoord() {
		Scanner in = new Scanner(System.in);
		int opcion=0;
		boolean salir=false;
		do {
			try {
				System.out.println("\n\n===== MENÚ DE COORDINACIÓN ====");
				System.out.println("1. Gestionar espectáculo");
				System.out.println("2. Asignar artista");
				System.out.println("3. Crear número");
				System.out.println("4. Modificar número");
				System.out.println("5. Crear espectáculo");
				System.out.println("6. Modificar espectáculo");
				System.out.println("7. Cerrar sesión");
				opcion=in.nextInt();
				switch(opcion) {
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
						System.out.println("En desarrollo...");
						break;
					case 7:
						System.out.println("Cerrando sesión...");
						salir = true;
						break;
					default: 
						System.out.println("Opción no válida");
						break;
							
				}
			
			}catch(InputMismatchException input) {
				System.out.println("Caracter no válido: "+input.getLocalizedMessage());
				in.nextLine();
			}
			
		}while(!salir);
	}
	
	public static void menuArtista() {
		Scanner in = new Scanner(System.in);
		int opcion=0;
		boolean salir=false;
		do {
			try {
				System.out.println("\n\n===== MENÚ DE COORDINACIÓN ====");
				System.out.println("1. Ver ficha de datos");
				System.out.println("2. Cerrar sesión");
				opcion=in.nextInt();
				switch(opcion) {
					case 1:
						System.out.println("Funcionalidad en desarrollo. Vuelva más tarde");
						break;
					case 2:
						System.out.println("Cerrando sesión...");
						salir = true;
						break;
					default: 
						System.out.println("Opción no válida");
						break;
							
				}
			
			}catch(InputMismatchException input) {
				System.out.println("Caracter no válido: "+input.getLocalizedMessage());
				in.nextLine();
			}
			
		}while(!salir); 
	}
	
	
	public static void menuAdmin() {
		
		Scanner in = new Scanner(System.in);
		int opcion=0;
		boolean salir=false;
		do {
			try {
				System.out.println("\n\n===== MENÚ DE ADMINISTRACIÓN ====");
				System.out.println("1. Registrar persona");
				System.out.println("2. Gestionar Artista - en desarrollo");
				System.out.println("3. Gestionar Coordinador - en desarrollo");
				System.out.println("4. Cerrar sesión");
				opcion=in.nextInt();
				switch(opcion) {
					case 1: 
						registrarPersona();
						break;
					case 2: 
						System.out.println("Funcionalidad en desarrollo. Vuelva más tarde");
						break;
					case 3:
						System.out.println("Funcionalidad en desarrollo. Vuelva más tarde");
						break;
					case 4:
						System.out.println("Cerrando sesión...");
						salir = true;
						break;
					default: 
						System.out.println("Opción no válida");
						break;
				}
			
			}catch(InputMismatchException input) {
				System.out.println("Caracter no válido: "+input.getLocalizedMessage());
				in.nextLine();
			}
			
		}while(!salir);
	}
	/**
	 * Método escogerEspecialidades. Genera una lista de especialidades y pide al usuario que introduzca un número
	 * para escoger una especialidad. Puede escoger tantas como quiera hasta que introduzca la opción de salir, 
	 * pero al guardar y retornar los valores en un Set, no permite duplicados. No obstante, avisa al usuario
	 * que su selección ya ha sido guardada.
	 * @return Set<Especialidad>
	 */
	public static Set<Especialidad> escogerEspecialidades() {
		Scanner in = new Scanner(System.in);
		Set<Especialidad> especialidades = new HashSet<Especialidad>();
		Especialidad[] valores = Especialidad.values();
		int cont=1, seleccion=0;
		boolean salir=false;
		
		do {
			System.out.println("Seleccione las especialidades del artista \n(múltiple opción, uno a uno)");
			for (Especialidad especialidad : valores) {
				System.out.println(cont+": "+especialidad.toString());
				cont++;
			}
			System.out.println("6. Salir");
			seleccion = in.nextInt();
			if(seleccion>0 && seleccion<6) {
				if(!especialidades.contains(valores[seleccion-1]))
					especialidades.add(valores[seleccion-1]);
				else 
					System.out.println("Valor ya introducido");
			}else if(seleccion==6) {
				salir=true;
			}else {
				System.out.println("Opción incorrecta");
			}
			System.out.println("Selección: "+especialidades.toString());
			cont=1;				
		}while(!salir);
		
		return especialidades;
	}
	
	public static String[] escogerCredenciales(HashMap<String, List<String>> usuariosActuales) {
		Scanner in = new Scanner(System.in);
		String[] creds ={"",""};
		String usuario, contrasenia;
		boolean usuarioValido=false,contraValida=false;
		
		// bucle de nombre de usuario, comprueba unicidad y espacios en blanco
		do {
			System.out.println("Introduzca el nombre de usuario:");
			usuario = in.nextLine();
			if(!usuariosActuales.containsKey(usuario)) {
				// comprueba espacios en blanco			
				if(usuario.length()>2) {
					if(!BuclesGenericos.contieneEspacios(usuario) && !BuclesGenericos.contieneEspeciales(usuario)) {
						creds[0]=usuario.toLowerCase();
						usuarioValido = true;
					}else {
						System.out.println("El nombre de usuario no puede contener espacios "
											+ "en blanco o caracteres no alfanuméricos");
					}	
				}else {
					System.out.println("La longitud del campo debe ser superior a dos caracteres");
				}
			}else {
				System.out.println("Nombre de usuario no disponible");
			}
		}while(!usuarioValido);
		
		// bucle de contraseña, la pide dos veces para comprobar que sea la deseada
		
		do{
			System.out.println("Introduzca la contraseña");
			contrasenia = in.nextLine();
			System.out.println("Confirme la contraseña:");
			String contraDup = in.nextLine();
			if(contrasenia.equals(contraDup)) {
				creds[1]=contrasenia;
				contraValida=true;
			}else
				System.out.println("Las contraseñas no coinciden");
			
		}while(!contraValida);
		return creds;
	}
	
	
	// pregunta todos los datos y los graba al fichero credenciales
	public static void registrarPersona() {
		
		String raiz = Thread.currentThread().getContextClassLoader().
				getResource("").getPath()+"application.properties";
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(raiz));
		} catch (IOException notFound) {
			notFound.printStackTrace();
		}
		
		File creds = new File(props.getProperty("ficherocredenciales"));
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(creds, true))) { 
			Scanner in = new Scanner(System.in);
			HashMap<String, List<String>> usuariosActuales = leerCredenciales();
			HashMap<String, String> paises = leerNacionalidades();
			Long idUltimoUsuario = Long.valueOf(usuariosActuales.size());
			int opcionProfesion;
			String user, pass, email, nombreCompleto,codNacionalidad ,nacionalidad = "";
			Perfil perfil;
			LocalDate fechaSenior=null;
			boolean emailEsUnico=false,nacionalidadEscogida=false, profesionEscogida=false, 
					seniorEscogido=false, esCoord=false;
			
			System.out.println("Formulario de creación de usuario");
			
			// pregunta al usuario por sus datos no específicos, como nombre, email...
			System.out.println("--- Datos personales ---");
			System.out.println("Introduzca el nombre completo");
			nombreCompleto = in.nextLine();
			do {
				System.out.println("Introduzca el email del usuario");
				email = in.next();
				// comprobar si email es único
				for (Map.Entry<String, List<String>> entry : usuariosActuales.entrySet()) {
					List<String> valores = entry.getValue();
					if(!email.equals(valores.get(3).toString())) 
						emailEsUnico=true;
						break;
				}
				System.out.println(emailEsUnico);
				if(!emailEsUnico) {
					System.out.println("La dirección: "+email+" ya está registrada. Pruebe de nuevo");
					email = in.next();
				}
			}while(!emailEsUnico);
			
			do {
				System.out.println("Indique el código de la nacionalidad del usuario:");
				System.out.println("Código | País");
				for (Map.Entry<String, String> entry : paises.entrySet()) {
					String codigoPais = entry.getKey();
					String pais = entry.getValue();
					System.out.println(codigoPais+" - "+pais);
				}
				codNacionalidad = in.next();
				if(!paises.containsKey(codNacionalidad.toUpperCase())) {
					System.out.println("El código introducido no existe.");
				}
				else{
					// en esta versión no se hace nada con la nacionalidad
					nacionalidad = paises.get(codNacionalidad);
					nacionalidadEscogida=true;
				}
			}while(!nacionalidadEscogida);
			System.out.println("Nacionalidad escogida: "+nacionalidad);
			
			// pregunta al usuario por sus datos específicos a su profesión en el circo
			
			System.out.println("--- Datos profesionales ---");
			do {
				System.out.println("¿El usuario pertenece a Coordinación o es un Artista?");
				System.out.println("1. Coordinación");
				System.out.println("2. Artista");
				opcionProfesion = in.nextInt();
				
				switch(opcionProfesion) {
					case 1:
						seniorEscogido = BuclesGenericos.bucleSiNo("¿Es un miembro senior?");
						if(seniorEscogido) {
							fechaSenior = Utilidades.leerFecha();
						}
						profesionEscogida=true;
						esCoord=true;
					break;
					case 2: 
						boolean tieneApodo= BuclesGenericos.bucleSiNo("¿El artista tiene apodo?");
						if(tieneApodo) {
							System.out.println("¿Cuál es su apodo?");
							// en esta versión no se hace nada con el apodo
							String apodoArtista = in.next();
						}						
						System.out.println("Indique las especializaciones del artista (opción múltiple, una a una)");
						Set<Especialidad> especialidades = escogerEspecialidades();
						profesionEscogida=true;
						break;
					default:
						System.out.println("Opción incorrecta");
						break;
				}
			}while(!profesionEscogida);
			
			// pregunta al usuario por sus datos de inicio de sesión, con sus correspondientes checks
			System.out.println("--- Credenciales ---");
			String[] credenciales = escogerCredenciales(usuariosActuales);
			
			
			// escritura de variables específicas a profesión 
			user = credenciales[0];
			pass = credenciales[1];
			if(esCoord) {
				perfil = Perfil.Coordinacion;
				Credenciales credsCoord = new Credenciales(idUltimoUsuario, user, pass, perfil);
				Coordinacion nuevoCoord = new Coordinacion(idUltimoUsuario, email,nombreCompleto, 
														nacionalidad,credsCoord,seniorEscogido, fechaSenior);
			}else {
				perfil = Perfil.Artista;
				Credenciales credsArtista = new Credenciales(idUltimoUsuario, user, pass, perfil);
				Artista nuevoArtista = new Artista(idUltimoUsuario, email, nombreCompleto,
													nacionalidad,credsArtista);
			} // estas instancias de Persona todavía no se usan en esta versión
			
			StringBuilder sb = new StringBuilder();
			sb.append(idUltimoUsuario+1).append("|"); // id numérica, siempre una unidad mayor que el último creado
			sb.append(user).append("|"); // nombre de usuario
			sb.append(pass).append("|"); // contraseña de usuario
			sb.append(email).append("|"); // email único
			sb.append(nombreCompleto).append("|"); // nombre completo
			sb.append(nacionalidad).append("|"); // nacionalidad
			sb.append(perfil); // coordinación/artista
			
			String usuarioFinal = sb.toString();
			System.out.println(sb.toString());
			
			bw.newLine();
			bw.write(usuarioFinal);

			System.out.println("Usuario creado con éxito");
			
		} catch (IOException e) {
			System.out.println("Error de escritura");
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		Sesion sesionActual = new Sesion("no logueado", null);
		int opcion = 0;
		boolean salirPrograma = false;
		
		do {
			try {
				System.out.println("\n===== MENÚ PRINCIPAL ========\nBienvenido, "
							+((sesionActual.getPerfil()) == null?"Invitado":sesionActual.getPerfil()));
				System.out.println("1. Ver espectáculos");
				System.out.println("2. Iniciar sesión");
					System.out.println("3. Salir");
				opcion = sc.nextInt();
				
				switch(opcion) {
					case 1: 
						System.out.println("Todavía no hay ningún espectáculo. Inténtelo de nuevo más tarde");
						break;
					case 2: 
						sesionActual = inicioSesion();
						if(sesionActual.getPerfil()==null) {
							break;
						}
						else if(sesionActual.getPerfil().equals(Perfil.Admin))
							menuAdmin();
						else if(sesionActual.getPerfil().equals(Perfil.Coordinacion))
							menuCoord();
						else if(sesionActual.getPerfil().equals(Perfil.Artista))
							menuArtista();
						sesionActual = new Sesion("no iniciada",null);
						break;
					case 3: 
						salirPrograma = confirmarSalida();
						break;
					case 4: escogerCredenciales(leerCredenciales());
					break;
					default:
						System.out.println("Opción incorrecta.");
						break;						
				}
			}catch(InputMismatchException input) {
				System.out.println("Caracter no válido");
				sc.nextLine();
			}
		}while(!salirPrograma);
		
	}
}
