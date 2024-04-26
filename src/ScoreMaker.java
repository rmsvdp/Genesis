import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import FileTools.*;

public class ScoreMaker  {

	BufferedReader bfr ;
	BufferedWriter bfw;
	// Path absoluto
//	private final String ScoreTemplate = "c:\\rms\\template.txt";
//	private final String ScoreBoard = "c:\\rms\\scoreboard.txt";
	// Path relativo dentro del proyecto
	private final String ScoreTemplate = ".\\res\\template.txt";
	private final String ScoreBoard = ".\\res\\scoreboard.txt";
	private final String ScoreTemplateHtml = ".\\res2\\indext.html";
	private final String ScoreBoardHtml = ".\\res2\\index.html";
	private final String[] HtmlTags = {"<div class=" + "\"" +  "titulo-tabla" + "\""  +">" ,"<!--fila 1-->"};
	//private final String[] HtmlTags = {"<div class=" + "\"" +  "titulo-tabla" + "\""  +">" ,"<!--fila 1-->"};
	private String[][] Scores = {
			{"RMS;1WET;1000;2000","LAN;1WET;1000;2000","AFX;1WET;1000;2000","MUR;1WET;1000;2000","JLV;1WET;1000;2000",
			  "ZAP;1WET;1000;2000","UMD;1WET;1000;2000","SSD;1WET;1000;2000","RMS;1WET;1000;2000","RMS;1WET;1000;2000"},
	        {"RMS;1WET;1500;2000","---;---;----;----","---;---;----;----","---;---;----;----","---;---;----;----",
				  "---;---;----;----","---;---;----;----","---;---;----;----","---;---;----;----","---;---;----;----"
	}};
	private String[][] Titulos = {{"Best Moon Landers"},{"Best Mars Landers"}};
	private  enum _MODO {PLAIN,HTML};
	
	public ScoreMaker() throws IOException {

	}
	
	/**
	 * Genera dinámica el contenido del ficheros a partir del array de contenidos que se le pasa
	 * @param ScoreSet Indice del marcador a volcar dentro del conjunto de contenidos
	 * @throws IOException
	 */
	public void updateScore(int ScoreSet, int _modo) throws IOException {

			_MODO modo;
			Boolean tablaEncontrada = false;
			Boolean tituloEncontrado = false;
			Integer marca  = 0;
			
					
			if (_modo == 0) {
				modo = _MODO.PLAIN;
				bfr = new BufferedReader(new FileReader(ScoreTemplate));	// Abrir plantilla
				bfw = new BufferedWriter (new FileWriter(ScoreBoard,false));// Abrir pagina final	
					
			}
			
			else {
				modo = _MODO.HTML;
			bfr = new BufferedReader(new FileReader(ScoreTemplateHtml));	// Abrir plantilla
			bfw = new BufferedWriter (new FileWriter(ScoreBoardHtml,false));// Abrir pagina final	
			}
			
			while (!tituloEncontrado) {									// Escribir hasta inicio de tabla
				String _linea = bfr.readLine();
				
				if (modo==_MODO.HTML)
						marca = _linea.indexOf(HtmlTags[0]);
					else
						marca = _linea.indexOf("<H1>");
				if (marca !=-1) {
					String _linHasta = _linea.substring(0, marca);		// encontrado el inicio
					bfw.write(_linHasta);
					dep(_linHasta);
					tituloEncontrado = true;								// Ya estamos listos para generar la tabla 
				} 
				else {	// Copio toda la linea
					bfw.write(_linea);	bfw.newLine();
					dep(_linea);
				}
			} // while no encuentre el título
			escribe(Titulos[ScoreSet],modo,0);
			while (!tablaEncontrada) {									// Escribir hasta inicio de tabla
				String _linea = bfr.readLine();
				if (modo==_MODO.HTML)
					marca = _linea.indexOf(HtmlTags[1]);
				else
					marca = _linea.indexOf("<TD>");
				if (marca !=-1) {
					String _linHasta = _linea.substring(0, marca);		// encontrado el inicio
					bfw.write(_linHasta);	
					dep(_linHasta);
					tablaEncontrada = true;								// Ya estamos listos para generar la tabla 
				} 
				else {	// Copio toda la linea
					bfw.write(_linea);	bfw.newLine();
					dep(_linea);
				}
			} // while no encuentre marca de tabla
			escribe(Scores[ScoreSet],modo,1);						// Procesar datos
			String _linea;
			while((_linea = bfr.readLine()) != null) {				// Escribir hasta final de plantilla
				bfw.write(_linea);		bfw.newLine();
			}
		bfr.close();													// Cerrar archivos
		bfw.close();
	} // updateScore
	
	/**
	 * Inserta los valores dinámicos de puntaciones
	 * @param marcador	Array de Strings que contiene la Tabla de puntuaciones
	 * @param m			Modo de ejecución : PLAIN, traslada los valores tal cual aparecen en el array de Strings
	 * @paran label		Tipo de etiqueta : 0 - Texto plano , 1 - Fila de tabla
	 * @throws IOException
	 */
	private void escribe(String[] marcador, _MODO m, int label) throws IOException {
	
		for (int i=0 ;i<marcador.length;i++) {	
			switch (m) {
			case PLAIN:
				bfw.write(parseScore(marcador[i]));
				bfw.newLine();
				break;
			case HTML:
				bfw.write(parseHtml(label,marcador[i]));
				bfw.newLine();
				break;
			default:	
			}
		}
	} // escribe
	
	/**
	 * Añade 4 espacios entre columnas
	 * @param datos array con los datos en formato csv, con separador ;
	 * @return cadena con campos formateados
	 */
	private String parseScore(String datos) {
		
		String cad="";
		String[] campos =datos.split(";"); 
		for (int i=0;i<campos.length;i++) {
			cad = cad + "    " + campos[i];
		}
		dep(cad);
		return cad;
	} // extraePlayer
	
	
	/**
	 * Genera una línea nueva en la tabla de puntuaciones
	 * @param datos array con los datos en formato csv, con separador ;
	 * @return cadena con campos formateados
	 */
	private String parseHtml(int Tag, String datos) {
		
		String cad="";

		switch(Tag) {
		
		case 0:
			cad = "<div class=\"titulo-tabla\">" + datos +"</div>" ;
			break;
		case 1:
			String[] campos =datos.split(";");
			cad =        "<div class=\"dato-tabla\">" + campos[0]+ "</div>\n"; 	// Nick
			cad = cad +  "<div class=\"dato-tabla\">" + campos[1]+ "</div>\n" ; 	// Grupo
			cad = cad +  "<div class=\"dato-tabla\">" + campos[2]+ "</div>\n" ; 	// Tiempo
			cad = cad +  "<div class=\"dato-tabla\">" + campos[3]+ "</div>\n" ; 	// Fuel
			break;
		}
		dep(cad);
		return cad;
	} // parseHtml
	
	public void dep(String s) {
		
//		Scanner sc = new Scanner(System.in);
//		System.out.println(s);
//		sc.nextLine();
//		
	}
} // ScoreMaker
