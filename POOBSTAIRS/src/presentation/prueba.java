package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

public class prueba extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField nameGame;
	private JButton open;
	

	/**
	 * Create the dialog.
	 */
	public prueba() {
		open = new JButton("Abrir");
		nameGame = new JTextField();
		JPanel dialog = new JPanel();
		dialog.setBackground(new Color(232, 202, 175));
		dialog.setLayout(new GridBagLayout());
		JLabel title1 = new JLabel("Indique el Nombre de la Partida", JLabel.CENTER);
		title1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		JLabel title2 = new JLabel("que Desea Abrir", JLabel.CENTER);
		title2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
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
		constraints4.insets = new Insets(10, 50, 0, 50);
		constraints4.gridx = 0;
		constraints4.gridy = 3;
		constraints4.gridwidth = 1;
		constraints4.gridheight = 1;
		constraints4.fill = GridBagConstraints.BOTH;
		
		dialog.add(open, constraints4);
	
		setContentPane(dialog);
	}

}
