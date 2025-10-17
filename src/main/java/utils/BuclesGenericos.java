package utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuclesGenericos {

	
	/**
	 * Método bucleSiNo. Hace al usuario una pregunta de si/no, comprueba que su valor sea válido y no deja 
	 * salir del bucle hasta que se obtenga una respuesta válida, y devuelve un booleano en consecuencia
	 * @param pregunta. La String a preguntar, seguida de una indicación al usuario sobre qué valor introducir (si/no)
	 * @return boolean true en caso afirmativo, boolean false si es negativo.
	 */
	public static boolean bucleSiNo(String pregunta) {
		Scanner in = new Scanner(System.in);
		boolean opcionEscogida=false;
		boolean salirBucle=false;
		
		do {
			System.out.println(pregunta+" (s/n)");
			String opcionSenior = in.next();
			opcionSenior=opcionSenior.toLowerCase();
			if(opcionSenior.equals("s")|| opcionSenior.equals("si")) {
				opcionEscogida = true;
				salirBucle=true;
			}else if(opcionSenior.equals("n")|| opcionSenior.equals("no")) {
				opcionEscogida = false;
				salirBucle=true;
			}else 
				System.out.println("Opción incorrecta");		
		}while(!salirBucle);
		
		return  opcionEscogida;
	}
	
	
	/**
	 * Método contieneEspacios. Comprueba que una String no contenga espacios en blanco en su cuerpo.
	 * @param palabra. La palabra a comprobar.
	 * @return true si contiene espacios en blanco, false si no los contiene.
	 */
	public static boolean contieneEspacios(String palabra){
	    if(palabra != null){
	        for(int i = 0; i < palabra.length(); i++){
	            if(Character.isWhitespace(palabra.charAt(i)))
	                return true;
	        }
	    }
	    return false;
	}
	
	
	public static boolean contieneEspeciales(String palabra){
		/*Pattern especiales = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
		Pattern digit = Pattern.compile("[0-9]");
		
		Matcher matchEsp = especiales.matcher(palabra);
		Matcher matchDigit = digit.matcher(palabra);
		
		
		boolean nocontieneEspeciales = matchEsp.find();
		boolean noContieneNum = matchDigit.find();
		
		
		
		if(nocontieneEspeciales && noContieneNum) {
			return true;
		}
		else {
			return false;
		}*/
		
		// de momento uso este codigo porque el pattern matcher no me funciona
		char[] chars = palabra.toCharArray();
		boolean contieneEspeciales=false;
		
	    for (char c : chars) {
	        if(!Character.isLetter(c))
	            contieneEspeciales=true;
	        else
	        	contieneEspeciales=false;
	    }

	    return contieneEspeciales;
	}
	
}
