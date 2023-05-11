package presentation;
import domain.*;
import domain.Die.Face;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
 * 
 * @author Castaño, Camargo
 *         2/05/2023
 */

public class POOBSTAIRSGUI extends JFrame {
	
	/* Paneles que hacen parte del juego*/
	private JPanel panels;
	private Principal principal;
	private Mode mode;
	private DataPlayers dataPlayers;
	private InitGame initGame;
	private GamePane startPlaying;
	
	
	/*startPlaying*/
	
	
	/*General*/
	
	
	private PoobStairs poobStairs;
	
	
	
	/**
	 * Constructor de la clase POOBSTAIRSGUI
	 */
	private POOBSTAIRSGUI() {
		prepareFrame();
		prepareElements();
		prepareActions();
		
	}
	
	/**
	 * Metodo que indica las propiedades del JFrame
	 */

	private void prepareFrame() {
		setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width / 2, screenSize.height / 2);
		Dimension frameSize = this.getSize();
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	
	/**
	 * Le da el color a las fichas de acuerdo a la elección de los jugadores
	 * @param color, color escogido por el jugador
	 * @return Objeto de la clase Color que le da el color a la ficha del Jugador
	 */
	private Color giveColor(String color) {
		if(color.equals("RED")) return Color.RED;
		else if(color.equals("BLUE")) return Color.BLUE;
		else if(color.equals("GREEN")) return Color.GREEN;
		else return Color.BLACK;
	}
	/**
	 * Metodo que inicializa y prepara el juego en el paquete de dominio
	 * @param rows, numero de filas que va a tener el tablero.
	 * @param columns, numero de columnas que va a tener el tablero
	 * @param snakes, numero de serpientes dentro del tablero
	 * @param stairs, numero de escaleras dentro del tablero
	 * @param pSpecials, probabilidad de que una casilla sea especial
	 * @param pPowers, probabilidad de que me salga un modificador dentro del dado
	 * @throws POOBSTAIRSException NUM_OBSTACLE, si Se lanza un excepcion si se llega a tener un
	 *                             numero excesivo
	 *                             de casillas especiales y obstaculos.
	 */
	private void setGame(int rows, int columns, int snakes, int stairs, double pSpecials, double pPowers) throws POOBSTAIRSException{
		 Player[] players = new Player[2];
		 String name1 =  dataPlayers.getName1(); 
		 String colors =  dataPlayers.getColors(); 
		 String name2 = dataPlayers.getName2(); 
		 String colors2 = dataPlayers.getColors2(); 
		 String machineMode = dataPlayers.getMachineMode(); 
		 
		 players[0] = new Player(name1, giveColor(colors));
		 if(!dataPlayers.isMachine()) players[1] = new Player(name2, giveColor(colors2));
		 else if(machineMode.equals("Principiante")) players[1] = new Beginner(giveColor( colors));
		 else players[1] = new Trainee(giveColor( colors));
		
		poobStairs = new PoobStairs(rows, columns,players);
		poobStairs.setGame(snakes, stairs, (float) pSpecials, (float) pPowers);
	}
	/**
	 * Metodo que inicializa todos los componentes que hacen parte del GUI 
	 */
	private void prepareElements() {
		panels = new JPanel(new CardLayout());
		setContentPane(panels);
		//Se inicializan y crea la esstructura del JPanel principal
		principal = new Principal(this);
		buildButton(principal);
		panels.add(principal);
		//Se inicializan los componentes que hacen parte del JPanel mode
		mode = new Mode(this);
		buildButton(mode);
		panels.add(mode);
		//Se inicializan los componentes que hacen parte del JPanel dataPlayers
		dataPlayers = new DataPlayers(this);
		panels.add(dataPlayers);
		//Se inicializan los componentes que hacen partedel JPanel initGame
		initGame = new InitGame(this);
		panels.add(initGame);
		//Se inicializan los componentes que hacen parte del JPanel startPlaying
		startPlaying = new GamePane(this);
		panels.add(startPlaying);
		for (Component panel : panels.getComponents()) {
			panel.setBackground(new Color(220, 93, 83));
		}
	}
	
	
	
	
	/**
	 * Metodo que se encarga de estructurar el JPanel dataPlayers
	 */

	
	
	/**
	 * Metodo que le asigna de forma general ciertas propiedades a los JLabels
	 * @param container, componente que contiene a los JLabel
	 * @param title, JLabel que cual no va a ser modifica
	 */
	protected void buildLabels(Container container, String title) {
		for (Component componente : container.getComponents()) {
			if (componente instanceof JLabel && !(((JLabel) componente).getText().equals(title))) {
				componente.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
			}
		}
	}
	
	/**
	 * Metodo que le asigna de forma general ciertas propiedades a los JButton
	 * @param container, componente que contiene a los JButton
	 */
	protected void buildButton(Container container) {
		for (Component componente : container.getComponents()) {
			if (componente instanceof JButton) {
				componente.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
				componente.setBackground(new Color(168, 202, 186));
				((JButton) componente).setOpaque(true);
			}
		}
	}

	private void prepareActions() {
		
		/* General*/
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int siNo = JOptionPane.showConfirmDialog(POOBSTAIRSGUI.this, "Esta seguro de terminar el juego?");
				System.out.print(siNo);
				if (siNo == 0)
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				else
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
			
		});
		
		
		/*principal*/
		principal.exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int siNo = JOptionPane.showConfirmDialog(POOBSTAIRSGUI.this, "¿Esta seguro de terminar el juego?");
				if (siNo == 0) {
					setVisible(false);
					System.exit(0);
				}
			}
		});

		 principal.newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextPane();
			}
		});
		
		/*mode y dataPlayers*/

		dataPlayers.principalMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstPane();
			}
		});
		
		mode.principalMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstPane();
			}
		});

		 mode.onePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!dataPlayers.isMachine()) {
					dataPlayers.removeAll();
					 dataPlayers.buildMachine();
				}
				nextPane();

			}
		});

		mode.multiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dataPlayers.isMachine()) {
					dataPlayers.removeAll();
					 dataPlayers.build();
				}
				nextPane();

			}
		});

		dataPlayers.next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!dataPlayers.isMachine() && dataPlayers.getColors().equals(dataPlayers.getColors2()))
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "Los jugadores no pueden tener el mismo color");
				
				else {
					CardLayout layout = (CardLayout) panels.getLayout();
					layout.next(panels);
				}

			}
		});
		/*initGame*/
		initGame.beggin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) panels.getLayout();
				try {
					Integer dataRows = initGame.getDataRows();
					Integer dataColumns = initGame.getDataColumns();
					Integer dataSnakes = initGame.getDataSnakes();
					Integer dataStairs = initGame.getDataStairs();
					Double dataPowers = initGame.getDataPowers();
					Double dataSpecials = initGame.getDataSpecials();
					setGame(dataRows, dataColumns, dataSnakes,dataStairs,dataPowers, dataSpecials);
					startPlaying.changeTemplate("Clasic");
					startPlaying.board.setLayout(new GridLayout(poobStairs.board().length, poobStairs.board()[0].length));
					startPlaying.refresh(poobStairs);
					POOBSTAIRSGUI.this.setExtendedState(MAXIMIZED_BOTH);
					
					layout.next(panels);
					
				}catch(POOBSTAIRSException exception) {
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, exception.getMessage());
				}
			}
		});
		
		/*startPlaying*/
		startPlaying.roll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Face current = poobStairs.rollDice();
				
				startPlaying.assignValue(current.getValue());
				if(activePower(current) == 0) {
					specialOptions(poobStairs.usePower());
				}else if(activePower(current) == 4){
					specialOptions(current.getValue());
				}
			}
		});
		startPlaying.change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startPlaying.changeTemplate(startPlaying.getTopics());
				startPlaying.refresh(poobStairs);
			}
		});
		
		startPlaying.endGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				POOBSTAIRSGUI.this.setExtendedState(NORMAL);
				CardLayout layout = (CardLayout) panels.getLayout();
				layout.first(panels);
				dataPlayers.removeAll();
			}
		});
		startPlaying.confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				poobStairs.movePiece(startPlaying.getSpecials() - poobStairs.getTurn().getPiecePosition() -1,true );
				CardLayout layout = (CardLayout) startPlaying.dataOrChoose.getLayout();
				layout.next(startPlaying.dataOrChoose);
				startPlaying.refresh(poobStairs);
				startPlaying.roll.setEnabled(true);
			}
		});

	}
	/**
	 * Metodo que cambia de Jpanel al siguiente
	 */
	private void nextPane() {
		CardLayout layout = (CardLayout) panels.getLayout();
		layout.next(panels);
	}
	
	private void firstPane() {
		CardLayout layout = (CardLayout) panels.getLayout();
		layout.first(panels);
	}

	public static void main(String[] args) {
		POOBSTAIRSGUI frame = new POOBSTAIRSGUI();
		frame.setVisible(true);
	}
	
	
	
	
	
	
	private int activePower(Face face) {
		try {
			int option;
			if(!(face.indicatePowers().equals(Power.CHANGE))) {
				option =JOptionPane.showConfirmDialog(this, face.indicatePowers() + "¿Desea utilizarlo?");
				return option;
			}else {
				JOptionPane.showMessageDialog(this, "Usted ha adquirido el poder " + Power.CHANGE);
				int movements = poobStairs.usePower();
				startPlaying.refresh(poobStairs);
				specialOptions(movements);
				return 3;
			}
		}catch(POOBSTAIRSException e) {
			return 4;
		}
	}
	
	private void specialOptions(int movements) {
		Integer[] specialOp;
		try {
			specialOp = poobStairs.analize(movements);
			startPlaying.roll.setEnabled(false);
			startPlaying.specials.removeAllItems();
			for(Integer i: specialOp) {
				startPlaying.specials.addItem(i + 1);
			}
			startPlaying.specials.addItem(poobStairs.getTurn().getPiecePosition() + movements + 1);
			CardLayout layout = (CardLayout) startPlaying.dataOrChoose.getLayout();
			layout.next(startPlaying.dataOrChoose);
		} catch (POOBSTAIRSException e) {
			poobStairs.movePiece(movements,true);
			startPlaying.refresh(poobStairs);
			
		}
		
	}
	
}
