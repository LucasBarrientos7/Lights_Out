package interfaz;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

public class Imagenes_Audio {
	//Variables
	private ImageIcon light_off_resized;
	private ImageIcon light_on_resized;
	private Clip click;
	private Clip musica;

	/** Constructor de la clase */
	public Imagenes_Audio() {
		light_off_resized = cambiarTamanio("src/imagenesYAudio/Light_OFF.png");
		light_on_resized = cambiarTamanio("src/imagenesYAudio/Light_ON.png");
	}
	
	
	/** Se encarga de reproducir el sonido de fondo al ser llamado */
	public void sonido_Background() {
		ReproducirSonido_Loop("src/imagenesYAudio/Background_Music.wav");
	}
	
	
	/** Se encarga de reproducir un sonido al seleccionar una lampara */
	public void sonido_Click() {
		ReproducirSonido_UnaVez("src/imagenesYAudio/Click_Sound.wav");
	}
	
	
	/** Se encarga de detener los sonidos al ser llamado */
	public void detenerAudio() {
		click.stop();
		musica.stop();
	}
	
	
	/** Reproduce un audio(Formato WAV) una sola vez */
    private void ReproducirSonido_UnaVez(String nombreSonido){
        try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
			click = AudioSystem.getClip();
			click.open(audioInputStream);
			click.start();
			} catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
				System.out.println("Error al reproducir el sonido.");
			}
    }
    
    
	/** Reproduce un audio(Formato WAV) indefinidamente */
    private void ReproducirSonido_Loop(String nombreSonido){
        try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
			musica = AudioSystem.getClip();
			musica.open(audioInputStream);
			musica.start();
			
        	//el loop se realiza la cantidad de veces indicada
        	//clip.loop(2);

        	//loop infinito
			musica.loop(Clip.LOOP_CONTINUOUSLY);
			} catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
				System.out.println("Error al reproducir el sonido.");
			}
    }
    

	/** Carga una ImageIcon, luego la convierte una Image, cambia 
	 * su tama�o y la convierte nuevamente en una ImageIcon */
    private ImageIcon cambiarTamanio(String imagen) {
		ImageIcon img_original = new ImageIcon(imagen);
		Image img = img_original.getImage();
		Image img_aux = img.getScaledInstance(144, 87, java.awt.Image.SCALE_SMOOTH);
		ImageIcon img_redimensionada = new ImageIcon(img_aux);
		
		return img_redimensionada;			
	}

	
	/** Getters */
	public ImageIcon getLuz_Apagada() {
		return light_off_resized;
	} 
	
	public ImageIcon getLuz_Encendida() {
		return light_on_resized;
	} 
}
