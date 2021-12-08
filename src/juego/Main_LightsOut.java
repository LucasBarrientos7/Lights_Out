package juego;
import java.awt.EventQueue;
import javax.swing.JFrame;
import interfaz.DialogUsuario;

public class Main_LightsOut {
	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DialogUsuario dialogo = new DialogUsuario(new JFrame());
					dialogo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

