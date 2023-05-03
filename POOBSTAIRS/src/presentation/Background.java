package presentation;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;

/**
 * Clase inspirada en el video de youtube https://www.youtube.com/watch?v=zmpCIBBAqns
 * @author L-Code
 * 21 de mayo del 2021
 */

public class Background extends JPanel{
	private final Color color1 = new Color(220, 93,83);
	private final Color color2 = new Color(228, 191,63);
	
	/**
	 * Se sobre escribe el metodo paint de Component
	 */
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		GradientPaint gp = new GradientPaint(0,0, color1,0, height ,color2);
		g2D.setPaint(gp);
		g2D.fillRect(0,0,width, height);
		
		
	}
	

}
