package presentation;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Mode extends IndependentPane {
	protected JButton onePlayer, multiPlayer, principalMenu;
		
	public Mode(JFrame father) {
		super(father);
		build();
	}
	/**
	 * Prepara los elementos del Jpanel Mode
	 */
	public void prepareElements() {
		onePlayer = new JButton("Jugar con Maquina");
		multiPlayer = new JButton("Jugar con Amigo");
		principalMenu = new JButton("Volver al Menu Principal");
	}
	
	/**
	 * Metodo que se encarga de estructurar el JPanel mode
	 */

	public void build() {
		JLabel question = new JLabel();
		JLabel question1 = new JLabel("¿Qué modo de juego", JLabel.CENTER);
		JLabel question2 = new JLabel("desea jugar?", JLabel.CENTER);
		question1.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		question2.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		question.setLayout(new GridLayout(2, 1));
		question.add(question1);
		question.add(question2);
		this.add(question);
		this.add(onePlayer);
		this.add(multiPlayer);
		this.add(principalMenu);
		this.setLayout(new GridLayout(4, 1, 0, 6));
		this.setBorder(new EmptyBorder(100, (int) (father.getSize().width * 0.3), 100, (int) (father.getSize().width * 0.3)));
	}
	
}
