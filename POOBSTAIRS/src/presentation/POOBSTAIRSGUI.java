package presentation;
import domain.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
		 Player[] players = {new Player("Camilo", "red"), new Player("Pollis", "blue")};
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
		startPlaying.setLayout(new BorderLayout());
		board = new JPanel();
		startPlaying.setBorder(new EmptyBorder(20,20,20,20));
		startPlaying.add(board, BorderLayout.CENTER);
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
		String[] colorOptions = { "Rojo", "Amarillo", "Azul", "Verde" };
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
		JPanel square;
		Color color;
		ImageIcon wallpaper;
		Icon icon;
		for(int i = 0; i < poobStairs.board().length; i++
				) {
			for(int j = 0; j < poobStairs.board()[0].length; j++) {
				
				try {
					if(poobStairs.board()[i][j].getObstacle().getType().equals("snake")) square = new DiferentSquare("/img/snake.jpg");
					else square = new DiferentSquare("/img/stair.jpg");
					
				}catch(POOBSTAIRSException e) {
					if(!(poobStairs.board()[i][j] instanceof Normal)) {
						square = new DiferentSquare("/img/Special.jpg");
						
					}
					else {
						square = new JPanel();
						if((i + j)%2 == 0) color = color2;
						else color = color1;
						square.setBackground(color);
						square.setOpaque(true);
					}
				}
				square.setLayout(new FlowLayout(FlowLayout.LEFT, 8,2));
				square.setBorder(new LineBorder(Color.BLACK, 1));
				square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
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
					layout.next(panels);
					
				}catch(POOBSTAIRSException exception) {
					JOptionPane.showMessageDialog(POOBSTAIRSGUI.this, exception.getMessage());
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
}
