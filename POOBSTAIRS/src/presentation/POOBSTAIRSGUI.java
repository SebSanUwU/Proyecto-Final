package presentation;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;


/**
 * Interfaz Gráfica para el juego POOBSTAIRS
 * @author Castaño, Camargo
 * 2/05/2023
 */

public class POOBSTAIRSGUI extends JFrame {
	private JPanel panels,principal, mode, dataPlayers;
	private JButton newGame, lastGame,exit, onePlayer, multiPlayer, principalMenu, next;
	private JTextField name1, name2;
	private JComboBox colors, machineMode, colors2;
	
	
	
	private POOBSTAIRSGUI() {
		prepareFrame();
		 prepareElements();
		prepareActions();
		
	}
	private void prepareFrame() {
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        Dimension frameSize = this.getSize();
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	private void prepareElements() {
		panels = new JPanel(new CardLayout());
		setContentPane(panels);
		principal = new JPanel();
        newGame = new JButton("Nueva Partida");
        lastGame = new JButton("Cargar Partida");
        exit = new JButton("Salir del Juego");
        buildprincipal();
        panels.add(principal);
        mode = new JPanel();
        onePlayer = new JButton("Jugar Contra la Maquina");
        multiPlayer = new JButton("Jugar con amigo");
        onePlayer = new JButton("Jugador vs Maquina");
        multiPlayer = new JButton("Jugador vs Jugador");
        principalMenu = new JButton("Volver al Menu Principal");
        buildMode();
        panels.add(mode);
        dataPlayers = new JPanel();
        next = new JButton("Siguiente");
        prepareData();
        for(Component panel: panels.getComponents()) {
        	panel.setBackground(new Color(220, 93,83));
        }
	}
	
	private void buildprincipal() {
		JLabel title = new JLabel("POOBSTAIRS", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        principal.add(title);
        principal.add(newGame);
        principal.add(lastGame);
        principal.add(exit);
        buildButton(principal);
        principal.setLayout(new GridLayout(4,1,6,6));
        principal.setBorder(new EmptyBorder((int) (getSize().height * 0.25),(int) (getSize().width * 0.3), (int) (getSize().height * 0.25), (int)(getSize().width * 0.3)));
	}
	
	private void buildMode() {
		JLabel question = new JLabel();
		JLabel question1 = new JLabel("¿Qué modo de juego", JLabel.CENTER);
		JLabel question2 = new JLabel("desea jugar?", JLabel.CENTER);
        question1.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        question2.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        question.setLayout(new GridLayout(2,1));
        question.add(question1);
        question.add(question2);
        mode.add(question);
        mode.add(onePlayer);
        mode.add(multiPlayer);
        mode.add(principalMenu);
        buildButton(mode);
		mode.setLayout(new GridLayout(4,1,0,6));
        mode.setBorder(new EmptyBorder(100, (int) (getSize().width * 0.3), 100,(int) (getSize().width * 0.3)));
	}
	
	
	private void prepareData() {
		name1 = new JTextField("",10);
		name2 = new JTextField("",10);
		String[] colorOptions = {"Rojo", "Amarillo", "Azul", "Verde"};
		colors = new JComboBox(colorOptions);
		colors2 = new JComboBox(colorOptions);
		String[] mode = {"Principiante", "Aprendiz"};
		machineMode = new JComboBox(mode);
		panels.add(dataPlayers);
		
	}
	private void buildData(boolean machine) {
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
		GroupLayout layout = new GroupLayout(dataPlayers);
		dataPlayers.setLayout(layout);
		
		if(machine) {
			layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(title, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
								.addComponent(principalMenu)))
			);
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
								.addComponent(principalMenu)))
			);
			
		}
		
		else{
			layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(title, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
								.addComponent(principalMenu)))
			);
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
								.addComponent(principalMenu)))
			);
		}
		dataPlayers.setBorder(new EmptyBorder(100, (int) (getSize().width * 0.3), 0,0));
		 buildButton(dataPlayers);
	}
	
	
	private void buildButton(Container container) {
		for(Component componente: container.getComponents()) {
        	if(componente instanceof JButton) {
        		componente.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
                componente.setBackground(new Color(168,202,186));
                ((JButton) componente).setOpaque(true);
        	}
        }
	}
	
	
	
	private void prepareActions() {
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int siNo = JOptionPane.showConfirmDialog(POOBSTAIRSGUI.this, "Esta seguro de terminar el juego?");
                if (siNo == 0)
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });
		
		exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int siNo = JOptionPane.showConfirmDialog(POOBSTAIRSGUI.this, "¿Esta seguro de terminar el juego?");
                if (siNo == 0){
                	setVisible(false);
                    System.exit(0);
                }
            }
        });
		
		newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	nextPane();
            }
        });
		
		principalMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dataPlayers.removeAll();
            	CardLayout layout = (CardLayout) panels.getLayout();
        		layout.first(panels);
            }
        });
		
		onePlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	buildData(true);
            	CardLayout layout = (CardLayout) panels.getLayout();
        		layout.next(panels);
            	
            }
        });
		
		multiPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	buildData(false);
            	CardLayout layout = (CardLayout) panels.getLayout();
        		layout.next(panels);
            	
            }
        });
		
		
	}
	
	private void nextPane() {
		CardLayout layout = (CardLayout) panels.getLayout();
		layout.next(panels);
	}
	
	public static void main(String[] args) {
		POOBSTAIRSGUI frame = new POOBSTAIRSGUI();
		frame.setVisible(true);
	}
}
