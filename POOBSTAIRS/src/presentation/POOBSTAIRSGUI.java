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
	private JPanel panels, principal, mode, dataPlayers, initGame,startPlaying;
	
	/* principal*/
	private JButton newGame, lastGame, exit;
	
	/*mode*/
	private JButton onePlayer, multiPlayer, principalMenu;
	
	
	/* dataplayers*/
	private JButton next;
	private JTextField name1, name2;
	private JComboBox colors, machineMode, colors2;
	
	/*initGame*/
	private JButton beggin;
	private JSpinner dataRows, dataColumns, dataSnakes, dataStairs, dataSpecials, dataPowers;
	
	/*startPlaying*/
	private JPanel board;
	private JLabel whoPlays, die;
	private JButton roll;
	
	/*General*/
	
	private Color color1, color2;
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
	 * metodo que cambia la visión del tablero de juego
	 * @param topic, modo en el cual se quiere ver el tablero ()
	 */

	private void changeTemplate(String topic) {
		if (topic.equals("Clasic")) {
			color1 = new Color(255, 233, 90); //Amarillo
			color2 = new Color(70, 165, 162); //Azul
		}
		refresh();
	}
	
	private Color giveColor(String color) {
		if(color.equals("RED")) return Color.RED;
		else if(color.equals("BLUE")) return Color.BLUE;
		else if(color.equals("GREEN")) return Color.GREEN;
		else return Color.YELLOW;
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
		 
		 players[0] = new Player(name1.getText(), giveColor( (String)colors.getSelectedItem()));
		 if(name2.isEnabled()) players[1] = new Player(name2.getText(), giveColor( (String)colors2.getSelectedItem()));
		 else if(((String) machineMode.getSelectedItem()).equals("Principiante")) players[1] = new Beginner(giveColor( (String)colors.getSelectedItem()));
		 else players[1] = new Trainee(giveColor( (String)colors.getSelectedItem()));
		
		poobStairs = new PoobStairs(rows, columns,players);
		poobStairs.setGame(snakes, stairs, (float) pSpecials, (float) pPowers);
	}
	/**
	 * Metodo que inicializa todos los componentes que hacen parte del GUI 
	 */
	private void prepareElements() {
		panels = new JPanel(new CardLayout());
		setContentPane(panels);
		//Se inicializan los componentes que hacen parte  del JPanel principal
		principal = new JPanel();
		newGame = new JButton("Nueva Partida");
		lastGame = new JButton("Cargar Partida");
		exit = new JButton("Salir del Juego");
		buildprincipal();
		panels.add(principal);
		//Se inicializan los componentes que hacen parte del JPanel mode
		mode = new JPanel();
		onePlayer = new JButton("Jugar con Maquina");
		multiPlayer = new JButton("Jugar con Amigo");
		principalMenu = new JButton("Volver al Menu Principal");
		buildMode();
		panels.add(mode);
		//Se inicializan los componentes que hacen parte del JPanel dataPlayers
		dataPlayers = new JPanel();
		next = new JButton("Siguiente");
		prepareData();
		//Se inicializan los componentes que hacen partedel JPanel initGame
		initGame = new JPanel();
		beggin = new JButton("Comenzar");
		dataRows= new JSpinner(new SpinnerNumberModel(3,3,50,1));
		dataColumns= new JSpinner(new SpinnerNumberModel(3,3,50,1));
		dataSnakes= new JSpinner(new SpinnerNumberModel(0,0,50,1));
		dataStairs= new JSpinner(new SpinnerNumberModel(0,0,50,1));
		dataSpecials= new JSpinner(new SpinnerNumberModel(0.0,0.0,1.05,0.05));
		dataPowers= new JSpinner(new SpinnerNumberModel(0.0,0.0,1.05,0.05));
		buildInit();
		panels.add(initGame);
		//Se inicializan los componentes que hacen parte del JPanel startPlaying
		startPlaying = new JPanel();
		startPlaying.setLayout(new BorderLayout(5,5));
		board = new JPanel();
		
		whoPlays = new JLabel("");
		whoPlays.setVerticalAlignment(SwingConstants.TOP);
		die = new JLabel();
		roll = new JButton("Lanzar dado");
		buildGame();
		panels.add(startPlaying);
		for (Component panel : panels.getComponents()) {
			panel.setBackground(new Color(220, 93, 83));
		}
	}
	/**
	 * Metodo que se encarga de estructurar el JPanel principal
	 */

	private void buildprincipal() {
		JLabel title = new JLabel("POOBSTAIRS", JLabel.CENTER);
		title.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		principal.add(title);
		principal.add(newGame);
		principal.add(lastGame);
		principal.add(exit);
		buildButton(principal);
		principal.setLayout(new GridLayout(4, 1, 6, 6));
		principal.setBorder(new EmptyBorder((int) (getSize().height * 0.25), (int) (getSize().width * 0.3),
				(int) (getSize().height * 0.25), (int) (getSize().width * 0.3)));
	}
	/**
	 * Metodo que se encarga de estructurar el JPanel mode
	 */

	private void buildMode() {
		JLabel question = new JLabel();
		JLabel question1 = new JLabel("¿Qué modo de juego", JLabel.CENTER);
		JLabel question2 = new JLabel("desea jugar?", JLabel.CENTER);
		question1.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		question2.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		question.setLayout(new GridLayout(2, 1));
		question.add(question1);
		question.add(question2);
		mode.add(question);
		mode.add(onePlayer);
		mode.add(multiPlayer);
		mode.add(principalMenu);
		buildButton(mode);
		mode.setLayout(new GridLayout(4, 1, 0, 6));
		mode.setBorder(new EmptyBorder(100, (int) (getSize().width * 0.3), 100, (int) (getSize().width * 0.3)));
	}
	/**
	 * Inicializa todos los coomponentes que hacen parte del JPanel dataPlayers 
	 */
	private void prepareData() {
		name1 = new JTextField("", 10);
		name2 = new JTextField("", 10);
		String[] colorOptions = { "RED", "YELLOW", "BLUE", "GREEN" };
		colors = new JComboBox(colorOptions);
		colors2 = new JComboBox(colorOptions);
		String[] mode = { "Principiante", "Aprendiz" };
		machineMode = new JComboBox(mode);
		machineMode.setBackground(new Color(168, 202, 186));
		name1.setBackground(new Color(168, 202, 186));
		name2.setBackground(new Color(168, 202, 186));
		colors.setBackground(new Color(168, 202, 186));
		colors2.setBackground(new Color(168, 202, 186));
		panels.add(dataPlayers);

	}
	
	/**
	 * Metodo que se encarga de estructurar el JPanel dataPlayers
	 */

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
		colors.setSelectedIndex(0);
		colors2.setSelectedIndex(1);
		GroupLayout layout = new GroupLayout(dataPlayers);
		dataPlayers.setLayout(layout);

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
		dataPlayers.setBorder(new EmptyBorder(100, (int) (getSize().width * 0.3), 0, 0));
		buildButton(dataPlayers);
	}
	/**
	 * Metodo que se encarga de estructurar el JPanel initGame
	 */
	private void buildInit() {
		JLabel title = new JLabel("Datos del Tablero");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel rows = new JLabel("Numero de Filas:");
		JLabel columns = new JLabel("Numero de Columnas:");
		JLabel snakes = new JLabel("Numero de Serpientes:");
		JLabel stairs = new JLabel("Numero de Escaleras:");
		JLabel specials = new JLabel("%Casilla Especial:");
		JLabel powers = new JLabel("%Modificador:");
		GroupLayout layout = new GroupLayout(initGame);
		initGame.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(title, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(rows)
										.addComponent(columns)
										.addComponent(snakes)
										.addComponent(stairs)
										.addComponent(specials)
										.addComponent(powers)
										.addComponent(beggin))
								.addGap(5)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(dataRows)
										.addComponent(dataColumns)
										.addComponent(dataSnakes)
										.addComponent(dataStairs)
										.addComponent(dataSpecials)
										.addComponent(dataPowers)))));
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(title)
								.addGap(10)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(rows)
										.addComponent(dataRows, 0, 25, 25))
								.addGap(10)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(columns)
										.addComponent(dataColumns, 0, 25, 25))
								.addGap(10)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(snakes)
										.addComponent(dataSnakes, 0, 25, 25))
								.addGap(10)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(stairs)
										.addComponent(dataStairs, 0, 25, 25))
								.addGap(10)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(specials)
										.addComponent(dataSpecials, 0, 25, 25))
								.addGap(10)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addComponent(powers)
										.addComponent(dataPowers, 0, 25, 25))
								.addGap(10)
								.addComponent(beggin)));
		initGame.setBorder(new EmptyBorder(90, (int) (getSize().width * 0.3), 0, 0));
		buildLabels(initGame, title.getText());
		buildButton(initGame);
		
	}
	
	private void  buildGame() {
		//Se dan las caracteristicas al tablero
		board.setOpaque(false);
		startPlaying.setBorder(new EmptyBorder(20,20,20,20));
		startPlaying.add(board, BorderLayout.CENTER);
		JPanel gameOptions = new JPanel();
		//Se crea un JPanel de opciones e indicaciones para realizar el juego de forma correcta 
		gameOptions.setBorder(new CompoundBorder(new EmptyBorder(1,2,1,2),
				new TitledBorder("Información Juego")));
		gameOptions.setLayout(new GridLayout(3,1));
		gameOptions.setPreferredSize(new Dimension(230,HEIGHT));
		startPlaying.add(gameOptions, BorderLayout.EAST);
		
		//Se crea la división de estado de juego
		
		JPanel playerData = new JPanel();
		
		gameOptions.add(playerData);
		playerData.setLayout(new GridLayout(3, 1, 5, 0));
		
		JLabel turn = new JLabel("Es el Turno de");
		turn.setVerticalAlignment(SwingConstants.BOTTOM);
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		playerData.add(turn);
		whoPlays.setHorizontalAlignment(SwingConstants.CENTER);
		playerData.add(whoPlays);
		
		
		//Se crea la división del dado
		JPanel dataDie = new JPanel();
		dataDie.setBorder(new EmptyBorder(3,3,3,3));
		dataDie.setLayout(new BorderLayout(1,10));
		dataDie.add(roll,BorderLayout.NORTH);
		die.setVerticalAlignment(SwingConstants.TOP);
		die.setHorizontalAlignment(SwingConstants.CENTER);
		assignValue(1);
		dataDie.add(die, BorderLayout.CENTER);
		buildButton(dataDie);
		
		gameOptions.add(dataDie);
		
	}
	/**
	 * Metodo que le asigna de forma general ciertas propiedades a los JLabels
	 * @param container, componente que contiene a los JLabel
	 * @param title, JLabel que cual no va a ser modifica
	 */
	private void buildLabels(Container container, String title) {
		for (Component componente : container.getComponents()) {
			if (componente instanceof JLabel && !(((JLabel) componente).getText().equals(title))) {
				componente.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
			}
		}
	}
	/**
	 * Se encarga de representar de manera visual el estado actual del tablero
	 */
	private void refresh() {
		Component component[] = board.getComponents();
        for(Component c: component){
            c.setVisible(false);
        }
		board.removeAll();
		whoPlays.setText(poobStairs.getTurn());
		JPanel square;
		Color color;
		
		for(int i = 0; i < poobStairs.board().length; i++
				) {
			for(int j = 0; j < poobStairs.board()[0].length; j++) {
				
				try {
					Obstacle obstacle= poobStairs.board()[i][j].getObstacle();
					if(obstacle.getType().equals("snake")) square = new DiferentSquare("/img/snake.jpg");
					else square = new DiferentSquare("/img/stair.jpg");
					square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
					JButton infoButton = new JButton("i");
					infoButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, 
							"Head: " + (obstacle.getHead().getNumSquare() + 1) + "//" +
							"Tail: " + (obstacle.getTail().getNumSquare() + 1));

						}
					});

					square.add(infoButton);
					
				}catch(POOBSTAIRSException e) {
					if(!(poobStairs.board()[i][j] instanceof Normal)) {
						square = new DiferentSquare("/img/Special.jpg");
						square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
						JButton infoButton = new JButton("i");
						String inf = specials(poobStairs.board()[i][j]);
						infoButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, 
								inf);

							}
						});
						square.add(infoButton);
					}
					else {
						square = new JPanel();
						if((i + j)%2 == 0) color = color2;
						else color = color1;
						square.setBackground(color);
						square.setOpaque(true);
						square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
					}
					
				}
				
				
				square.setLayout(new FlowLayout(FlowLayout.LEFT, 8,2));
				square.setBorder(new LineBorder(Color.BLACK, 1));
				
				for(Piece piece: poobStairs.board()[i][j].getPieces()) {
					JLabel visualPiece = new JLabel();
					visualPiece.setPreferredSize(new Dimension((int) Math.round(square.getPreferredSize().height*0.5),(int) Math.round(square.getPreferredSize().height*0.5)));
					visualPiece.setMaximumSize(new Dimension((int) Math.round(square.getPreferredSize().height*0.5),(int) Math.round(square.getPreferredSize().height*0.5)));
					visualPiece.setMinimumSize(new Dimension((int) Math.round(square.getPreferredSize().height*0.5),(int) Math.round(square.getPreferredSize().height*0.5)));
					visualPiece.setBackground(piece.getColor());
					visualPiece.setOpaque(true);
					square.add(visualPiece);
				}
				board.add(square);
				
			}
		}
	}
	/**
	 * Metodo que le asigna de forma general ciertas propiedades a los JButton
	 * @param container, componente que contiene a los JButton
	 */
	private void buildButton(Container container) {
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
				if (siNo == 0)
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				else
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
			
			
		});
		
		
		/*principal*/
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int siNo = JOptionPane.showConfirmDialog(POOBSTAIRSGUI.this, "¿Esta seguro de terminar el juego?");
				if (siNo == 0) {
					setVisible(false);
					System.exit(0);
				}
			}
		});

		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode.add(principalMenu);
				nextPane();
			}
		});
		
		/*mode y dataPlayers*/

		principalMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) panels.getLayout();
				if(startPlaying.isVisible()) POOBSTAIRSGUI.this.setExtendedState(ICONIFIED);
				layout.first(panels);
				dataPlayers.removeAll();
				mode.add(principalMenu);
				
			}
		});

		onePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildData(true);
				nextPane();

			}
		});

		multiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildData(false);
				nextPane();

			}
		});

		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (colors.getSelectedItem().equals(colors2.getSelectedItem()))
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, "Los jugadores no pueden tener el mismo color");
				else {
					CardLayout layout = (CardLayout) panels.getLayout();
					layout.next(panels);
					mode.add(principalMenu);

				}

			}
		});
		/*initGame*/
		beggin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) panels.getLayout();
				try {
					setGame((Integer) dataRows.getValue(), (Integer)dataColumns.getValue(), (Integer)dataSnakes.getValue(),(Integer)dataStairs.getValue()
							,(Double)dataPowers.getValue(), (Double)dataSpecials.getValue());
					changeTemplate("Clasic");
					board.setLayout(new GridLayout(poobStairs.board().length, poobStairs.board()[0].length));
					
					POOBSTAIRSGUI.this.setExtendedState(MAXIMIZED_BOTH);
					
					layout.next(panels);
					
				}catch(POOBSTAIRSException exception) {
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, exception.getMessage());
				}
			}
		});
		
		/*startPlaying*/
		roll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Face current = poobStairs.rollDice();
				assignValue(current.getValue());
				
				try {
					if(activePower(current)) {
						
					}
					poobStairs.advancePlayer(current.getValue());
					refresh();
				} catch (POOBSTAIRSException e1) {
					e1.printStackTrace();
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

	public static void main(String[] args) {
		POOBSTAIRSGUI frame = new POOBSTAIRSGUI();
		frame.setVisible(true);
	}
	
	private void assignValue(int value) {
		switch(value) {
			case 1:
				die.setIcon(new ImageIcon(POOBSTAIRSGUI.class.getResource("/img/numberOne.jpg")));
				break;
			case 2:
				die.setIcon(new ImageIcon(POOBSTAIRSGUI.class.getResource("/img/numberTwo.jpg")));
				break;
			case 3:
				die.setIcon(new ImageIcon(POOBSTAIRSGUI.class.getResource("/img/numberTree.jpg")));
				break;
			case 4:
				die.setIcon(new ImageIcon(POOBSTAIRSGUI.class.getResource("/img/numberFour.jpg")));
				break;
			case 5:
				die.setIcon(new ImageIcon(POOBSTAIRSGUI.class.getResource("/img/numberFive.jpg")));
				break;
			case 6:
				die.setIcon(new ImageIcon(POOBSTAIRSGUI.class.getResource("/img/number6.jpg")));
				break;
			default:
				die.setIcon(new ImageIcon(POOBSTAIRSGUI.class.getResource("/img/numberOne.jpg")));
				break;
		}
	}
	
	private String specials(Square square) {
		if(square instanceof Jumper) {
			return "Conmigo avanzas 5 casilla";
		}
		else if(square instanceof ReverseJumper) {
			return "Conmigo retrocedes 5 casilla";
		}else if(square instanceof Advance) {
			return "Conmigo Vas hasta la siguiente escalera";
		}else if(square instanceof Regression) {
			return "Conmigo Vas hasta la anterior serpiente";
		}else if(square instanceof QA) {
			return "A mi me tienes que contestar una pregunta para avanzar";
		}else {
			return "Yo te devuelvo a la casilla inicial";
		}
		
	}
	private static void waitSeconds(int segundos){
        try{
            Thread.sleep(segundos*100);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
	
	private boolean activePower(Face face) {
		return false;
	}
}
