package presentation;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

public class DataPlayers extends IndependentPane{
	
	private JTextField name1, name2;
	private JComboBox<String> colors, machineMode, colors2;
	protected JButton next, principalMenu;
	private JLabel name, title;
	private boolean isMachine;
	protected DataPlayers(JFrame father) {
		super(father);
		build();
		
	}
	
	
	/**
	 * Inicializa todos los coomponentes que hacen parte del JPanel dataPlayers 
	 */
	protected void prepareElements() {
		
		name1 = new JTextField("", 10);
		name2 = new JTextField("", 10);
		String[] colorOptions = { "RED", "BLACK", "BLUE", "GREEN" };
		colors = new JComboBox<String>(colorOptions);
		colors2 = new JComboBox<String>(colorOptions);
		String[] mode = { "Principiante", "Aprendiz" };
		machineMode = new JComboBox(mode);
		machineMode.setBackground(new Color(168, 202, 186));
		name1.setBackground(new Color(168, 202, 186));
		name2.setBackground(new Color(168, 202, 186));
		colors.setBackground(new Color(168, 202, 186));
		colors2.setBackground(new Color(168, 202, 186));
		next = new JButton("Siguiente");
		principalMenu = new JButton("Volver al Menu Principal");
		name = new JLabel("Nombre:");
		name.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		title = new JLabel("Datos del Juego");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		title.setHorizontalAlignment(SwingConstants.CENTER);
	}
	/**
	 * Metodo que contrusye la estrucutra del panel si su modo es jugar co0n amigo
	 */
	
	protected void build() {
		isMachine = false;
		name1.setText("Jugador1");
		name2.setText("Jugador2");
		JLabel nameS = new JLabel("Nombre:");
		nameS.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		colors.setSelectedIndex(0);
		colors2.setSelectedIndex(1);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(title, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(name)
										.addComponent(nameS))
								.addGap(5)
								.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(name1, 0, 100, 100)
										.addComponent(name2, 0, 100, 100)
										.addGroup(layout.createSequentialGroup()))
								.addGap(5)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(colors, 0, 100, 100)
										.addComponent(colors2, 0, 100, 100)))
						.addGroup(layout.createSequentialGroup()
								.addComponent(next)
								.addGap(5)
								.addComponent(principalMenu))));
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(title)
								.addGap(20)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(name)
										.addComponent(name1, 0, 25, 25)
										.addComponent(colors, 0, 25, 25))
								.addGap(20)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(nameS)
										.addComponent(name2, 0, 25, 25)
										.addComponent(colors2, 0, 25, 25))
								.addGap(20)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(next)
										.addComponent(principalMenu))));
		((POOBSTAIRSGUI) father).buildButton(this);
		this.setBorder(new EmptyBorder(100, (int) (father.getSize().width * 0.3), 0, 0));
	}
	
	
	
	/**
	 * Organiza el JPanel si el modo de juego es jugar con maquina
	 */
	protected void buildMachine() {
		isMachine = true;
		name1.setText("Jugador1");
		JLabel modeOfMachine = new JLabel("Modo:");
		modeOfMachine.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		colors.setSelectedIndex(0);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
			layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(title, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE)
							.addGroup(layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addComponent(name)
											.addComponent(modeOfMachine))
									.addGap(5)
									.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
											.addComponent(name1, 0, 100, 100)
											.addComponent(machineMode, 0, 100, 100))
									.addGap(5)
									.addComponent(colors, 0, 100, 100))
							.addGroup(layout.createSequentialGroup()
									.addComponent(next)
									.addGap(5)
									.addComponent(principalMenu))));
			layout.setVerticalGroup(
					layout.createParallelGroup(Alignment.LEADING)
							.addGroup(layout.createSequentialGroup()
									.addComponent(title)
									.addGap(20)
									.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addComponent(name)
											.addComponent(name1, 0, 25, 25)
											.addComponent(colors, 0, 25, 25))
									.addGap(20)
									.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addComponent(modeOfMachine, 0, 25, 25)
											.addComponent(machineMode, 0, 25, 25))
									.addGap(20)
									.addGroup(layout.createParallelGroup(Alignment.LEADING)
											.addComponent(next)
											.addComponent(principalMenu))));
			((POOBSTAIRSGUI) father).buildButton(this);
			this.setBorder(new EmptyBorder(100, (int) (father.getSize().width * 0.3), 0, 0));
	}
	
	protected String getName1() {
		return name1.getText();
	}
	
	protected String getName2() {
		return name2.getText();
	}
	
	protected String getColors() {
		return (String)colors.getSelectedItem();
	}
	
	protected String getColors2() {
		return (String)colors2.getSelectedItem();
	}
	
	protected String getMachineMode() {
		return (String)machineMode.getSelectedItem();
	}
	
	protected boolean isMachine() {
		return isMachine;
	}
	
}
