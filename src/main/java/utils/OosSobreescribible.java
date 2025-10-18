package utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *  Clase creada para solucionar el problema "invalid type code: AC" que provoca un 
 *  OOS llamado varias veces para sobreescribir un fichero binario, corrompiendo el archivo
 */
public class OosSobreescribible extends ObjectOutputStream {

	  public OosSobreescribible(OutputStream out) throws IOException {
	    super(out);
	  }

	  @Override
	  protected void writeStreamHeader() throws IOException {
	    reset();
	  }

}