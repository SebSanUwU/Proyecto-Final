package presentation;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GradientPaint;

/**
 * Clase inspirada en el video de youtube https://www.youtube.com/watch?v=zmpCIBBAqns
 * @author L-Code
 * 21 de mayo del 2021
 */

public class Principal extends IndependentPane{
	
	protected JButton newGame, lastGame, exit, open;
	protected JDialog openGame;
	private JTextField nameGame;
	
	
	protected Principal(JFrame father) {
		super(father);
		build();
		buildDialog();
		prepareActions();
	}
	
	
	
	/**
	 * Prepala los elementos del Jpanel Principal
	 */
	protected void prepareElements() {
		newGame = new JButton("Nueva Partida");
		lastGame = new JButton("Cargar Partida");
		exit = new JButton("Salir del Juego");
		open = new JButton("Cargar");
		openGame = new JDialog(father, "Cargar Vieja Partida");
		nameGame = new JTextField();
	}
	/**
	 * Metodo que se encarga de estructurar el JPanel principal
	 */

	protected void build() {
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
	
	private void buildDialog() {
		Dimension windowSize = father.getSize();
		openGame.setSize(windowSize.width / 2, windowSize.height / 2);
		Dimension dialogSize = openGame.getSize();
		openGame.setLocation((windowSize.width + dialogSize.width) / 2, (windowSize.height + dialogSize.height) / 2);
		openGame.setResizable(false);
		JPanel dialog = new JPanel();
		dialog.setBackground(new Color(232, 202, 175));
		dialog.setLayout(new GridBagLayout());
		JLabel title1 = new JLabel("Indique el Nombre de la Partida", JLabel.CENTER);
		title1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		JLabel title2 = new JLabel("que Desea Abrir", JLabel.CENTER);
		title2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		nameGame.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		nameGame.setBackground(new Color(168, 202, 186));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		dialog.add(title1, constraints);
		
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridx = 0;
		constraints2.gridy = 1;
		constraints2.gridwidth = 1;
		constraints2.gridheight = 1;
		constraints2.fill = GridBagConstraints.BOTH;
		constraints2.weightx = 1.0;
		constraints2.weighty = 0.0;
		dialog.add(title2, constraints2);

		GridBagConstraints constraints3 = new GridBagConstraints();
		constraints3.insets = new Insets(10, 20, 0, 20);
		constraints3.gridx = 0;
		constraints3.gridy = 2;
		constraints3.gridwidth = 1;
		constraints3.gridheight = 1;
		constraints3.fill = GridBagConstraints.BOTH;
		
		dialog.add(nameGame, constraints3);
		
		
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4.insets = new Insets(10, 120, 0, 120);
		constraints4.gridx = 0;
		constraints4.gridy = 3;
		constraints4.gridwidth = 1;
		constraints4.gridheight = 1;
		constraints4.fill = GridBagConstraints.BOTH;
		
		dialog.add(open, constraints4);
		((POOBSTAIRSGUI) father).buildButton(dialog);
		openGame.setContentPane(dialog);
		
	}
	
	private void prepareActions() {
		/* General*/
		lastGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameGame.setText("");
				openGame.setVisible(true);
			}
		});
	}
	
	protected String getOpenName() {
		return nameGame.getText();
	}
	

}
