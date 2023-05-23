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
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	private SpecialSelection specialDialog;
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
		 String piece = dataPlayers.getPiece(); 
		 String piece2 = dataPlayers.getPiece2(); 
		 String machineMode = dataPlayers.getMachineMode();
		 
		 
		 players[0] = new Player(name1);
		 players[0].setPiece(colors, piece);
		 if(!dataPlayers.isMachine()) {
			 players[1] = new Player(name2);
			 players[1].setPiece(colors2, piece2);
		 }else{
			if(dataPlayers.getMachineMode().equals("Principiante")){
				players[1] = new MachineBegginer("Maquina principiante", null);
			}else{
				players[1] = new MachineLearner("Maquina aprendiz", null);
			}
		 }
		
		poobStairs = new PoobStairs(rows, columns,players,initGame.getChange());
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
		specialDialog = new SpecialSelection(this);
	}
	
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
					startPlaying.saveName.setText("");
					layout.next(panels);
					//machinePlay();
					
				}catch(POOBSTAIRSException exception) {
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, exception.getMessage());
				}
			}
		});
		
		/*startPlaying*/
		startPlaying.roll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(machinePlay()){
				}else{
					Face current = poobStairs.rollDice();
					startPlaying.assignValue(current.getValue());
					int option = activePower(current);
					if(option == 0) {
						specialOptionsJD(poobStairs.usePower());
					}else if(option == -1 || (option > 1 && option < 4)){
						specialOptionsJD(current.getValue());
					}
					startPlaying.refresh(poobStairs);
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
				
			}
		});
		
		startPlaying.saveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = startPlaying.getSaveName();
				name.trim();
				if(name.equals("")) {
					startPlaying.saveDialog.setVisible(true);
				}else {
					try {
						poobStairs.save(name);
					} catch (Exception e1) {
	                    JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "Se ha producido un error ");
					}
				}
			}
		});
		
		startPlaying.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = startPlaying.getSaveName();
				name.trim();
				if(name != "") {
					startPlaying.saveDialog.setVisible(false);
					try {
						poobStairs.save(name);
					} catch (Exception e1) {
	                    JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "Se ha producido un error ");
					}
				}else {
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "Debe especificar un nombre");
				}
			}
		});
		
		principal.open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = principal.getOpenName();
				name.trim();
				if(name != "") {
					principal.openGame.setVisible(false);
					try {
						poobStairs = PoobStairs.open(name);
						startPlaying.board.setLayout(new GridLayout(poobStairs.board().length, poobStairs.board()[0].length));
						startPlaying.changeTemplate("Clasic");
						startPlaying.refresh(poobStairs);
						CardLayout layout = (CardLayout) panels.getLayout();
						layout.last(panels);
						POOBSTAIRSGUI.this.setExtendedState(MAXIMIZED_BOTH);
						startPlaying.saveName.setText(name);
					} catch(FileNotFoundException ex) {
						JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "No existe una partida con ese nombre ");
					}catch (Exception e1) {
	                    JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "Se ha producido un error ");
					}
				}else {
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "Debe especificar un nombre");
				}
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
	/**
	 * metodo que lleva al panel de inicio
	 */
	private void firstPane() {
		CardLayout layout = (CardLayout) panels.getLayout();
		layout.first(panels);
	}

	/**
	 * Trata de utilizar el poder otorgado por la cara actual del dado
	 * @param face, Cara actual del dado
	 * @return, la decición si se utiliza el poder del dado o no a elección del jugador en turno
	 */
	private int activePower(Face face) {
		int option;
		try {
			
			if(!(face.indicatePowers().equals(Power.CHANGE))) {
				option =JOptionPane.showConfirmDialog(this, face.indicatePowers() + "¿Desea utilizarlo?");
				
			}else {
				JOptionPane.showMessageDialog(this, "Usted ha adquirido el poder " + Power.CHANGE);
				int movements = poobStairs.usePower();
				startPlaying.refresh(poobStairs);
				specialOptionsJD(movements);
				option =4;
			}
		}catch(FaceException e) {
			 option = 3;
		}
		return option;
	}
	/**
	 * Mueve la pieza luego de analizar que casillas especiales existen en su rango de movimiento
	 * @param movements, numero de casillas que va a avanzar la pieza
	 */
	private void specialOptionsJD(int movements) {
		Integer[] specialOp;
		try {
			specialOp = poobStairs.analize(movements);
			startPlaying.roll.setEnabled(false);
			specialDialog.build(specialOp, poobStairs.getTurn().getPiecePosition() + movements + 1);
			specialDialog.setVisible(true);
		} catch (POOBSTAIRSException e) {
			Square destination;
			if((poobStairs.getTurn().getPiecePosition() + movements < poobStairs.getInLine().length - 1)
					&& (poobStairs.getTurn().getPiecePosition() + movements >0)) {
				destination = poobStairs.getInLine()[poobStairs.getTurn().getPiecePosition() + movements];
				if(destination instanceof SpecialSquare) {
					JOptionPane.showMessageDialog(this, "Vas a caer en una casilla " + destination.getClass().getName().replace("domain.", ""));
				}
			}
			poobStairs.movePiece(movements);
			startPlaying.refresh(poobStairs);
		}
		
	}
	/**
	 * En caso de que el jugador en turno sea de tipo maquina, la maquina juega por si misma.
	 */
	private boolean machinePlay() {
		if(poobStairs.getTurn() instanceof Machine) {
			startPlaying.roll.setEnabled(false);
			Face current = poobStairs.rollDice();
			startPlaying.assignValue(current.getValue());
			poobStairs.playMachine();
			startPlaying.refresh(poobStairs);
			startPlaying.roll.setEnabled(true);
			return true;
		}
		return false;
	}
	/**
	 * En caso de que dentro del rango de movimiento de la pieza en juego hallan casillas especiales,
	 * se mueve dicha pieza a la casilla seleccionada por el jugador
	 * @param selectedSquare, casilla a la cual se quiere mover el jugador.
	 */
	protected void goToSelected(int selectedSquare) {
		poobStairs.movePiece(selectedSquare- poobStairs.getTurn().getPiecePosition() -1 );
		startPlaying.refresh(poobStairs);
		startPlaying.roll.setEnabled(true);
		
		
	}
	/**
	 * Indica el arreglo de casillas que tiene el juego en esos momentos
	 * @return poobStairs.getInline()
	 */
	protected Square[] getLineBoard() {
		return poobStairs.getInLine();
	}
	/**
	 * Duerme el hilo del GUI
	 * @param seconds, segundos que se desea dormir el hilo
	 */
	private void waitSeconds(int seconds) {
		try
		{
		    Thread.sleep (seconds*1000);  
		} 
		catch (Exception e)
		{
		    
		}
	}
	
	
	  
	public static void main(String[] args) {
		POOBSTAIRSGUI frame = new POOBSTAIRSGUI();
		frame.setVisible(true);
	}
}