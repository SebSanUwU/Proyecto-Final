package presentation;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GradientPaint;

/**
 * Clase inspirada en el video de youtube https://www.youtube.com/watch?v=zmpCIBBAqns
 * @author L-Code
 * 21 de mayo del 2021
 */

public class Principal extends JPanel{
	protected JButton newGame, lastGame, exit;
	private JFrame father;
	
	public Principal(JFrame father) {
		super();
		this.father = father;
		prepareElements();
	}
	
	
	
	/**
	 * Prepala los elementos del Jpanel Principal
	 */
	private void prepareElements() {
		newGame = new JButton("Nueva Partida");
		lastGame = new JButton("Cargar Partida");
		exit = new JButton("Salir del Juego");
		buildprincipal();
	}
	/**
	 * Metodo que se encarga de estructurar el JPanel principal
	 */

	private void buildprincipal() {
		JLabel title = new JLabel("POOBSTAIRS", JLabel.CENTER);
		title.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		this.add(title);
		this.add(newGame);
		this.add(lastGame);
		this.add(exit);
		this.setLayout(new GridLayout(4, 1, 6, 6));
		this.setBorder(new EmptyBorder((int) (father.getSize().height * 0.25), (int) (father.getSize().width * 0.3),
				(int) (father.getSize().height * 0.25), (int) (father.getSize().width * 0.3)));
	}
	
	
	

}
