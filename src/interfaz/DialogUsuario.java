package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogUsuario extends JDialog {

	//Variables
	private final JPanel contentPanel = new JPanel();
	private JTextField textUsuario;
	private int dimension;


	/**
	 * Create the dialog.
	 */
	public DialogUsuario(JFrame parent) {
		super(parent,true);
		crearVentanaInicio();
		textoIntroducirNombre();
		CrearCajaDeTexto();
		//dificultad media por defecto
		this.dimension=4;
	}
	
	
	/** Crea en pantalla la ventana de inicio al ejecutar el programa */
	private void crearVentanaInicio() {
		setTitle("Lights Out!");
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JLabel lblNombreJuego = new JLabel("Juego Ligths Out");
		lblNombreJuego.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNombreJuego.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreJuego.setForeground(Color.RED);
		lblNombreJuego.setBackground(Color.WHITE);
		lblNombreJuego.setBounds(100, 36, 200, 35);
		contentPanel.add(lblNombreJuego);
	}

	
	/** Muestra en pantalla un texto invitando al usuario a que escriba su nombre */	
	private void textoIntroducirNombre() {
		JLabel lblIntroducir = new JLabel("Introduzca su nombre: ");
		lblIntroducir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIntroducir.setBounds(20, 122, 150, 20);
		contentPanel.add(lblIntroducir);
	}

	
	/** Crea una caja de texto para que el usuario escriba su nombre y una serie de botones para 
	para seleccionar la dificultad e iniciar la partida*/
	private void CrearCajaDeTexto() {
		textUsuario = new JTextField();
		textUsuario.setBounds(199, 124, 150, 20);
		contentPanel.add(textUsuario);
		textUsuario.setColumns(10);
		
		textUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		JButton btn_facil = new JButton("Facil");
		btn_facil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dimension=3;
			}
		});
		btn_facil.setBounds(20, 170, 89, 23);
		contentPanel.add(btn_facil);
		
		JButton btn_moderado = new JButton("Moderado");
		btn_moderado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dimension=4;
			}
		});
		btn_moderado.setBounds(139, 170, 104, 23);
		contentPanel.add(btn_moderado);
		
		JButton btn_dificil = new JButton("Dificil");
		btn_dificil.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dimension=5;
			}
		});
		btn_dificil.setBounds(271, 170, 89, 23);
		contentPanel.add(btn_dificil);
		
		
		JButton okButton = new JButton("Play");
		okButton.setBounds(90, 200, 200, 35);
		okButton.setActionCommand("Play");
		contentPanel.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				iniciarPartida(nombreUsuario(), dimension);
				contentPanel.setVisible(false);
			}
		});

	}

	
	/** Inicia la partida con el nombre de usuario y dificultad(Dimension) ingresados */	
	private void iniciarPartida(String nombreDelJugador,int dimensionDelTablero) {
		setVisible(false);
		Interfaz_LightsOut interfaz = new Interfaz_LightsOut(dimensionDelTablero,nombreDelJugador);
		interfaz.getFrame().setVisible(true);
	}
	
	
	/** Retorna lo que ingres� el usuario por teclado */	
	public String nombreUsuario() {
		return textUsuario.getText();
	}
}
