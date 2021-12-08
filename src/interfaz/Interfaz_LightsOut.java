package interfaz;

import juego.Logica_LightsOut;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JDialog;
import java.util.ArrayList;

public class Interfaz_LightsOut {
	//Variables
	private JFrame frame;
	private JLabel txtNombreusuario;
	private JLabel lblTurnos;
	private JLabel lblRecordActual;
	private JLabel lblRecord;
	private ArrayList<JButton> tablero;
	private int dimension;
	private String usuario;
	private String direccionRecord;
	private DialogUsuario dialog;
	private Logica_LightsOut matriz;
	private ManejoFicheros manejoFicheros;
	private Imagenes_Audio imagenesYAudio;
	
	
	/**
	 * Create the application.
	 */
	public Interfaz_LightsOut(int dimension, String usuario) {
		this.tablero = new ArrayList<>();
		this.dimension = dimension;
		this.usuario= usuario;
		this.matriz = new Logica_LightsOut(dimension);
		this.manejoFicheros = new ManejoFicheros();
		this.imagenesYAudio = new Imagenes_Audio();
		if(dimension==3)
			direccionRecord = "src/records/Record_Facil.txt";
		if(dimension==4)
			direccionRecord = "src/records/Record_Moderado.txt";
		if(dimension==5)
			direccionRecord = "src/records/Record_Dificil.txt";
		initialize();
	}
	

	/** Inicializa los contenidos del JFrame */
	private void initialize() {
		
		JPanel panel = crearPanel();

	//Ventana emergente (JDialog modal) que solicita el nombre de usuario
		dialog = new DialogUsuario(frame);
		dialog.setVisible(false);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
	//Inicializacion de JLabels donde almacenos nombre de usuario, turnos, y el record 		
		lblTurnos = new JLabel("Turnos: "+ Integer.toString(matriz.cantidadTurnos()));
		lblTurnos.setBounds(144*dimension + 30, 66, 60, 14);
		panel.add(lblTurnos);
		
		lblRecord = new JLabel("Record:");
		lblRecord.setBounds(144*dimension + 30, 123, 46, 14);
		panel.add(lblRecord);
		
		lblRecordActual = new JLabel(manejoFicheros.leerPrimeraLineaFichero(direccionRecord));
		lblRecordActual.setBounds(144*dimension + 30, 143, 200, 14);
		panel.add(lblRecordActual);
		
		txtNombreusuario = new JLabel();
		txtNombreusuario.setText("Jugador: " + usuario);
		txtNombreusuario.setBounds(144*dimension + 30, 5, 200, 20);
		panel.add(txtNombreusuario);
		
	//Logica, graficos y tama�o del frame
		crearTablero(dimension, panel);
		actualizarTablero();
		cambiarEstado_Click();
		frame.setBounds(100, 100, 144*dimension +200, 87 * dimension +40);
		imagenesYAudio.sonido_Background();	
	}
	
	
	/** Crea un JPanel en pantalla */
	private JPanel crearPanel() {
		frame = new JFrame("Lights Out! - Game");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/imagenesYAudio/Light_Icon.jpg"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		return panel;
	}
	
	
	/** Crea un tablero con el tama�o ingresado (tama�o: dimension * dimension) */
	private void crearTablero(int dimension, JPanel panel) {
		this.dimension=dimension;
		int cont=0;
		int x= 0;
		int y = 0;
		
		for(int i = 1; i<=dimension*dimension ;i++) {
			JButton boton = new JButton();
			if(cont==dimension) {
				x=0;
				y=y+87;
				cont=0;
			}
			boton.setBounds(x, y, 144, 87);
			boton.setBackground(Color.BLACK);
			panel.add(boton);
			x=x+144;
			cont++;
			tablero.add(boton);
			}
	}
	
	
	/** Cambia el estado del JButton al que se le hace click con el mouse */
	private void cambiarEstado_Click() {
		for(int i=0; i<tablero.size(); i++) {
			int posicion = i;
			tablero.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					matriz.jugarUnaPartida(posicion);
					actualizarTablero();
					lblTurnos.setText("Turnos: "+ Integer.toString(matriz.cantidadTurnos()));
					imagenesYAudio.sonido_Click();
				}		
			});	
		} 
	}
	
	
	/** Actualiza el tablero con los datos de la matriz de juego */
	private void actualizarTablero() {
		int lucesTotales=tablero.size();
		for (int i=0; i<this.tablero.size(); i++) {
			int[] idxBoton = matriz.buscarIndices(i);
			if(matriz.estaEncendida(idxBoton[0],idxBoton[1])) {
				tablero.get(i).setSelected(true);
				tablero.get(i).setIcon(imagenesYAudio.getLuz_Encendida());
				lucesTotales++;
			}
			else {
				tablero.get(i).setSelected(false);
				tablero.get(i).setIcon(imagenesYAudio.getLuz_Apagada());
				lucesTotales--;
			}
			terminarPartida(lucesTotales==0);
		}
	}
	
	
	/**Verifica el estado de la partida, en caso de que el estado sea true(victoria), ejecuta un cuadro con la felicitaci�n 
	  por pantalla, guarda el record en caso de que se haya superado el record anterior en la dificultad seleccionada, 
	  y da la opcion de volver a jugar o cerrar el JFrame*/
	private void terminarPartida(boolean estadoDePartida) 
	{
		if(estadoDePartida) {
			guardarRecord();
			JOptionPane.showMessageDialog(null, "Felicidades "+ usuario + "\n"+"Ganaste la partida en "
			+ Integer.toString(matriz.cantidadTurnos()) + " turnos.");
			
			imagenesYAudio.detenerAudio();
			
			int opcion = JOptionPane.showOptionDialog(null, "Desea jugar nuevamente? Oprima SI \n Si desea finalizar oprima NO", 
					"Continuar?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			
			if(opcion == 0) {
				frame.dispose();
				Interfaz_LightsOut window = new Interfaz_LightsOut(dimension,usuario);
				window.frame.setVisible(true);
			}
			else
				frame.dispose();
		}
	}
	
	
	/** Verifica en un Fichero si el record actual es mayor que el record previamente guardado, de ser mayor lo reemplaza, 
	  si es menor o igual no hace nada */
	private void guardarRecord() {
		File record = new File(direccionRecord);
        if(!record.exists())
    		manejoFicheros.escribirFichero(Integer.toString(matriz.cantidadTurnos()) + " Turnos - " + usuario, direccionRecord);
        else {
        	//True si el primer parametro(puntaje nuevo) es mayor que el segundo(ultimo record)
        	if(manejoFicheros.compararPuntajes(matriz.cantidadTurnos(), manejoFicheros.leerPrimeraLineaFichero(direccionRecord))) {
        		manejoFicheros.borrarRecordAnterior(direccionRecord);
        		manejoFicheros.escribirFichero(Integer.toString(matriz.cantidadTurnos()) + " Turnos - " + usuario, direccionRecord);
        	}
        }
	}
	
	
	/** Getter*/
	public JFrame getFrame() {
		return frame;
	}
}
