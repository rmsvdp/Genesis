package FileTools;


import java.io.*;
public class ReadWriteText {
	
	private 	FileWriter fw ;
	private		FileReader fr;
	private     BufferedReader  bfr;
	private		BufferedWriter bfw;
	
	public ReadWriteText(String _rutaFich,String modo) throws IOException {
		
		if (modo == "r"){
				this.fr = new FileReader(_rutaFich);
				this.bfr = new BufferedReader (new FileReader(_rutaFich));
		}
		if (modo == "w"){
			this.fw = new FileWriter(_rutaFich);
			this.bfw = new BufferedWriter (new FileWriter(_rutaFich,false));
	}
		 
		
	}

	public void CloseBWrite() throws IOException {
		bfw.close();
	}
	public void CloseWrite() throws IOException {
		fw.close();
	}
	
	public void CloseBRead() throws IOException {
		fr.close();
	}
	public void CloseRead() throws IOException {
		fr.close();
	}
	
	public void writeSimple(String texto) throws IOException {
	
		// Escribir  
			fw.write(texto);	
		//System.out.println("ESCRITURA: OK!");	
		}
	
	
	public int readSimple(char[] texto) throws IOException {
		
		int valor;	

			valor = fr.read(texto);  			// Leer
			//System.out.println("Resultado de la Lectura:\n " + texto );
			return valor;					// Si no hay datos que leer valor = -1
		}
		
	
	public String readBuffered() throws  IOException {
		 return bfr.readLine();
	}	// readBuffered
	
	
	public void writeBuffered(String contenido) throws  IOException {
		 bfw.write(contenido);
	}	// readBuffered
	
	public void writeBufferednewLine() throws  IOException {
		 bfw.newLine();
	}	// readBuffered
	
	
} //ReadWriteText 
