import java.io.IOException;

public class AppMain {

	enum _MODO {PLAIN,HTML};
	
	public static void main(String[] args) {

		int ScoreSet = 0;
		int _delay = 5000;

		try {
			ScoreMaker sm = new ScoreMaker();
			System.out.println("Ejecutando desde : "+ System.getProperty("user.dir"));
			int j = 0;
			while (j<12) {
				System.out.println("Generando archivo ...");
				sm.updateScore(ScoreSet,_MODO.HTML.ordinal());
				ScoreSet = (ScoreSet+1) % 2;
				Thread.sleep(_delay);
				j++;
			} // repetir j veces
			System.out.println("Fin Aplicación .");
		}
		catch (InterruptedException e) {
				e.printStackTrace();
			}	
		catch (IOException e) {
			System.out.println("Error al acceder a los archivos de configuración del programa");
			}
	}

}
