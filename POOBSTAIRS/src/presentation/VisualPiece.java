package presentation;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Clase declarada para representar visualmente las fichas dentro de POOBSTAIRS
 * 
 * @author Castaño - Camargo
 * 05/05/2023
 *
 */

public class VisualPiece extends JPanel {
	private Image backgroundImage;
	private URL background;

	/**
	 * Create the panel.
	 */
	public VisualPiece(String color, String shape) {
		background = this.getClass().getResource(selectPiece(color, shape));
		backgroundImage = new ImageIcon(background).getImage();
	}
	private String selectPiece(String color, String shape) {
		String type;
		if(shape.equals("Normal")) {
			if(color.equals("BLACK")) type = "/img/blackPiece.png";
			else if(color.equals("BLUE")) type = "/img/bluePiece.png";
			else if(color.equals("RED")) type = "/img/redPiece.png";
			else  type = "/img/greenPiece.png";
		}else if(shape.equals("Car")) {
			if(color.equals("BLACK")) type = "/img/blackCar.png";
			else if(color.equals("BLUE")) type = "/img/blueCar.png";
			else if(color.equals("RED")) type = "/img/redCar.png";
			else  type = "/img/greenCar.png";
		}else{
			if(color.equals("BLACK")) type = "/img/blackHat.png";
			else if(color.equals("BLUE")) type = "/img/blueHat.png";
			else if(color.equals("RED")) type = "/img/redHat.png";
			else  type = "/img/greenHat.png";
		}
		
		return type;
	}
	
	
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
		
		
	}

}
