package utils;

import java.util.Scanner;

public class BuclesGenericos {

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
	
}
