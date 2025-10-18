package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
* Clase Utilidades.java
*
* @author repositorio de luisdbb
*/
public class Utilidades {

	public static java.time.LocalDate leerFecha() {
		LocalDate ret = null;
		int dia, mes, anio;
		boolean correcto = false;
		Scanner in;
		do {
			System.out.println("Introduzca un valor para el día (1...31)");
			in = new Scanner(System.in, "ISO-8859-1");
			dia = in.nextInt();
			System.out.println("Introduzca un valor para el mes (1...12)");
			in = new Scanner(System.in, "ISO-8859-1");
			mes = in.nextInt();
			System.out.println("Introduzca un valor para el año");
			in = new Scanner(System.in, "ISO-8859-1");
			anio = in.nextInt();

			try {
				ret = LocalDate.of(anio, mes, dia);
				correcto = true;
			} catch (Exception e) {
				System.out.println("Fecha introducida incorrecta.");
				correcto = false;
			}
		} while (!correcto);
		return ret;
	}
	
	/**
	 * Comprueba si hay más de un año de diferencia entre fechas
	 * @param fechaIni. fecha de inicio
	 * @param fechaFin. fecha de fin
	 * @return true si hay menos de un año de diferencia, false si hay más de un año de diferencia
	 */
	public static boolean diferenciaDeFechas(LocalDate fechaIni, LocalDate fechaFin) {
		boolean resultado;
	    long diferencia = ChronoUnit.DAYS.between(fechaIni, fechaFin);
	    if(diferencia<365)
	    	resultado=true;
	    else
	    	resultado=false;
	    
	    return resultado;
	}
	
}
