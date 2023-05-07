package presentation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JLabel;


public class CirclePiece extends JLabel {

	 private int size;

	    public CirclePiece(int size, Color color) {
	        this.size = size;
	        setSize(new Dimension(size+500, size+500));
	        setForeground(color);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g.create();
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setColor(getForeground());
	        int x = (getWidth() - size) / 2;
	        int y = (getHeight() - size) / 2;
	        g2d.fill(new Ellipse2D.Double(x, y, size, size));
	        g2d.dispose();
	    }
}
