package presentation;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.JPanel;
/**
 * Clase declarada para representar de una forma diferente aquellas casillas dentro de POOBSTAIRS
 * que tienen un obstaculo o son casillas especiales.
 * Basado en el video https://www.google.com/search?client=firefox-b-d&q=dale+a+tu+JPanel+una+imagen+de+fondo#fpstate=ive&vld=cid:ef8e4615,vid:VqDFzDFZWQc
 * @author Castaño - Camargo
 * 05/05/2023
 *
 */

public class DiferentSquare extends JPanel {
	private Image backgroundImage;
	private URL background;

	/**
	 * Create the panel.
	 */
	public DiferentSquare(String type) {
		background = this.getClass().getResource("/img/snake.jpg");
		backgroundImage = new ImageIcon(background).getImage();
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
		
		
	}

}
