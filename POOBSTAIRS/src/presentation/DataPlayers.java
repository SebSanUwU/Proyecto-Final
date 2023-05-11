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

public class DataPlayers extends JPanel{
	private JFrame father;
	
	private JTextField name1, name2;
	private JComboBox<String> colors, machineMode, colors2;
	protected DataPlayers(JFrame father) {
		super();
		this.father = father;
		prepareData();
	}
	
	
	/**
	 * Inicializa todos los coomponentes que hacen parte del JPanel dataPlayers 
	 */
	private void prepareData() {
		
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
	}
	/**
	 * Organiza el JPanel de acuerdo al modo de juego 
	 * @param machine, mode de juego
	 * @param principalMenu, boton para volver al menu principal del Frame
	 */
	protected void buildData(boolean machine,JButton next, JButton principalMenu) {
		name1.setText("Jugador1");
		name2.setText("Jugador2");
		JLabel name = new JLabel("Nombre:");
		JLabel nameS = new JLabel("Nombre:");
		name.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		nameS.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		JLabel modeOfMachine = new JLabel("Modo:");
		modeOfMachine.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		JLabel title = new JLabel("Datos del Juego");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		colors.setSelectedIndex(0);
		colors2.setSelectedIndex(1);
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);

		if (machine) {
			name2.setEnabled(false);
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

		}

		else {
			name2.setEnabled(true);
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
		}
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
	protected boolean name2Enabled() {
		return name2.isEnabled();
	}
}
