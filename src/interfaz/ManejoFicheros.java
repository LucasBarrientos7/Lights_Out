package interfaz;
import java.io.*;

public class ManejoFicheros{
	public ManejoFicheros(){	
	}
	/** Escribe sobre un fichero.txt, en caso de no existir se crea uno nuevo  */
	public void escribirFichero(String texto, String direccionFichero){
		File record = new File(direccionFichero);
        FileWriter fichero = null;
        PrintWriter printWriter = null;
        try
        {
    		//FileWriter fichero = new FileWriter("c:/prueba.txt",true) para trabajar con un nuevo fichero
			if(record.exists()){
				fichero = new FileWriter(direccionFichero,true);
			} else {
				//FileWriter fichero = new FileWriter("c:/prueba.txt") para trabajar con un fichero preexistente
				fichero = new FileWriter(direccionFichero);
			}
			printWriter = new PrintWriter(fichero);
            printWriter.println(texto);
            
		} catch (Exception e) {
			e.printStackTrace();
		
		// En el finally cerramos el fichero, para asegurarnos que se cierra tanto si todo va bien como si salta una excepcion.
		} finally {
			try {
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
			e2.printStackTrace();
			}
		}
		}
	
	
	/** Lee un fichero */
    public void leerFichero(String direccionFichero) {
		File fichero = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			// Abro el fichero y creo un BufferedReader para poder leer el texto(metodo readLine()).
			fichero = new File (direccionFichero);
			fileReader = new FileReader (fichero);
			bufferedReader = new BufferedReader(fileReader);
				
			// Lectura del fichero
			String linea;
			while((linea=bufferedReader.readLine())!=null)
				System.out.println(linea);
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			try{                    
				if( null != fileReader ){   
					fileReader.close();     
				}                  
			} catch (Exception e2){ 
			e2.printStackTrace();
			}
		}
	}
    
    
	/** Lee un fichero.txt y devuelve la primera linea como un String*/
    public String leerPrimeraLineaFichero(String direccionFichero) {
		File fichero = null;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try {
			// Abro el fichero y creo un BufferedReader para poder leer el texto(metodo readLine()).
			fichero = new File (direccionFichero);
			fileReader = new FileReader (fichero);
			bufferedReader = new BufferedReader(fileReader);
				
			// Lectura de la primera linea
			String linea = bufferedReader.readLine();
			return linea;
			}
			catch(Exception e){
				e.printStackTrace();
			}finally{
				try{                    
					if( null != fileReader ){   
						fileReader.close();     
					}                  
				}catch (Exception e2){ 
					e2.printStackTrace();
				}
		}
		return "Error al intentar leer el fichero";
	}
    
    
	/** Compara un entero y un String que contenga un valor entero y devuelve true si el entero es mayor que el String */
	public boolean compararPuntajes(int puntajeNuevo, String recordAnterior) {
		int pos = 0;
		String valorComparable = "";
		if(!recordAnterior.contains(" Turnos") || recordAnterior==null)
			return true;
		
		while(recordAnterior.indexOf(" Turnos") > pos) {
			valorComparable = valorComparable + recordAnterior.charAt(pos);
			pos++;
		}
		return puntajeNuevo < Integer.valueOf(valorComparable);
	}
	
	
	/** Vacia un fichero */
	public void borrarRecordAnterior(String direccionRecordAnterior) {
		BufferedWriter bufferedWriter = null;		
		File record = new File(direccionRecordAnterior);
        try
        {
			if(record.exists()) {
				bufferedWriter = new BufferedWriter(new FileWriter(direccionRecordAnterior));  
				bufferedWriter.write("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			try {
				if (null != bufferedWriter)
					bufferedWriter.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
}

