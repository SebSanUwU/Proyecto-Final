package presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import domain.Advance;
import domain.Jumper;
import domain.Mortal;
import domain.Obstacle;
import domain.POOBSTAIRSException;
import domain.Piece;
import domain.PoobStairs;
import domain.QA;
import domain.Regression;
import domain.ReverseJumper;
import domain.SpecialSquare;
import domain.Square;

public class GamePane extends IndependentPane {
	protected JPanel board, dataOrChoose;
	protected JLabel whoPlays, die, numSnakes, numStairs, numSpecials, numModifiers, maxPosition, extraMoves;
	protected JButton roll, change, endGame, saveGame, save;
	protected JComboBox topics;
	protected JTextField saveName;
	protected JComboBox<Integer> specials;
	private Color color1, color2;
	protected JDialog saveDialog;

	/**
	 * Create the panel.
	 */
	public GamePane(JFrame father) {
		super(father);
		build();
		buildDialog();
		

	}

	/**
	 * Inicializa todos los elementos del panel
	 */
	public void prepareElements() {
		this.setLayout(new BorderLayout(5, 5));
		board = new JPanel();
		whoPlays = new JLabel("");
		whoPlays.setFont(new Font("Tahoma", Font.PLAIN, 15));
		whoPlays.setPreferredSize(new Dimension(0, 10));
		whoPlays.setVerticalAlignment(SwingConstants.TOP);
		die = new JLabel();
		roll = new JButton("Lanzar Dado");
		numSnakes = new JLabel("");
		numStairs = new JLabel("");
		numSpecials = new JLabel("");
		numModifiers = new JLabel("");
		maxPosition = new JLabel("");
		extraMoves = new JLabel("");
		extraMoves.setFont(new Font("Tahoma", Font.PLAIN, 15));
		change = new JButton("Cambiar");
		String[] themes = { "Clasic", "Christmas", "Chess" };
		endGame = new JButton("Salir de la Partida");
		topics = new JComboBox(themes);
		dataOrChoose = new JPanel();
		specials = new JComboBox<Integer>();
		saveGame = new JButton("Salvar Partida");
		save = new JButton("Salvar");
		saveName = new JTextField();
		saveDialog = new JDialog(father, "Salvar Partida");
	}

	/**
	 * Metodo que se encarga de estructurar el JPanel initGame
	 */

	public void build() {
		// Se dan las caracteristicas al tablero
		board.setOpaque(false);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.add(board, BorderLayout.CENTER);
		JPanel gameOptions = new JPanel();
		// Se crea un JPanel de opciones e indicaciones para realizar el juego de forma
		// correcta
		gameOptions.setBorder(new CompoundBorder(new EmptyBorder(1, 2, 1, 2),
				new TitledBorder("Información Juego")));
		gameOptions.setLayout(new GridLayout(3, 1));
		gameOptions.setPreferredSize(new Dimension(230, HEIGHT));
		gameOptions.setOpaque(false);
		this.add(gameOptions, BorderLayout.EAST);
		// Se crea la división de estado de juego
		JPanel playerData = new JPanel();
		playerData.setBorder(new LineBorder(new Color(0, 0, 0)));
		gameOptions.add(playerData);
		playerData.setLayout(new GridLayout(3, 1, 0, 2));
		JLabel turn = new JLabel("Es el Turno de");
		turn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		turn.setVerticalAlignment(SwingConstants.BOTTOM);
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		playerData.add(turn);
		whoPlays.setHorizontalAlignment(SwingConstants.CENTER);
		playerData.add(whoPlays);
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(5, 2, 2, 2));
		info.add(new JLabel("Num Snakes: "));
		info.add(numSnakes);
		info.add(new JLabel("Num Stairs: "));
		info.add(numStairs);
		info.add(new JLabel("Num Specials: "));
		info.add(numSpecials);
		info.add(new JLabel("Modifiers: "));
		info.add(numModifiers);
		info.add(new JLabel("Max Position: "));
		info.add(maxPosition);
		playerData.setOpaque(false);
		playerData.add(info);
		for (Component component : info.getComponents()) {
			((JLabel) component).setOpaque(false);
		}
		info.setOpaque(false);

		// Se crea la división del dado
		JPanel dataDie = new JPanel();
		dataDie.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataDie.setLayout(new BorderLayout(1, 10));
		dataDie.add(roll, BorderLayout.NORTH);
		die.setHorizontalAlignment(SwingConstants.CENTER);
		die.setOpaque(false);
		assignValue(1);
		dataDie.add(die, BorderLayout.CENTER);
		dataDie.add(extraMoves, BorderLayout.SOUTH);
		((POOBSTAIRSGUI) father).buildButton(dataDie);
		dataDie.setOpaque(false);
		gameOptions.add(dataDie);

		// Se crea la división de extra button

		JPanel extraButtons = new JPanel();
		extraButtons.setBorder(new LineBorder(new Color(0, 0, 0)));
		extraButtons.setLayout(new GridLayout(3, 1,0,5));
		JPanel topic = new JPanel();
		topic.setLayout(new GridLayout(1, 2, 5, 5));
		topic.setBorder(new EmptyBorder(5, 5, (int) Math.round(father.getSize().height * 0.1), 5));
		topic.add(topics);
		topic.add(change);
		extraButtons.add(topic);
		extraButtons.add(saveGame);
		extraButtons.add(endGame);
		gameOptions.add(extraButtons);
		extraButtons.setOpaque(false);
		topic.setOpaque(false);
		
		((POOBSTAIRSGUI) father).buildButton(extraButtons);
		((POOBSTAIRSGUI) father).buildButton(topic);
		topics.setBackground(new Color(168, 202, 186));
	}

	/**
	 * Asigna el valor visual del dado
	 * 
	 * @param value, valor actual del dado
	 */
	protected void assignValue(int value) {
		switch (value) {
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

	/**
	 * Se encarga de representar de manera visual el estado actual del tablero
	 * 
	 * @param game, juego que va a ser representado
	 */
	protected void refresh(PoobStairs poobStairs) {
		Component component[] = board.getComponents();
		for (Component c : component) {
			c.setVisible(false);
		}
		board.removeAll();
		whoPlays.setText(poobStairs.getTurn().getName());
		JPanel square;
		Color color;
		numSnakes.setText(String.valueOf(poobStairs.getTurn().getNumSnakes()));
		numStairs.setText(String.valueOf(poobStairs.getTurn().getNumStairs()));
		numSpecials.setText(String.valueOf(poobStairs.getTurn().numSpecials()));
		numModifiers.setText(String.valueOf(poobStairs.getTurn().getNumModifiers()));
		maxPosition.setText(String.valueOf(poobStairs.getTurn().getMax()));
		extraMoves.setText("Te vas a mover desde: " + String.valueOf(poobStairs.getTurn().getPiecePosition() + 1));
		for (int i = 0; i < poobStairs.board().length; i++) {
			for (int j = 0; j < poobStairs.board()[0].length; j++) {

				try {
					Obstacle obstacle = poobStairs.board()[i][j].getObstacle();
					if (obstacle.getType().equals("snake"))
						square = new DiferentSquare("/img/snake.jpg");
					else
						square = new DiferentSquare("/img/stair.jpg");
					square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
					JButton infoButton = new JButton("i");
					infoButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(father,
									"Head: " + (obstacle.getHead().getNumSquare() + 1) + "//" +
											"Tail: " + (obstacle.getTail().getNumSquare() + 1) + "//"
											+ obstacle.getClass().getName());

						}
					});
					square.add(infoButton);
				} catch (POOBSTAIRSException e) {
					if (poobStairs.board()[i][j] instanceof SpecialSquare) {
						if(poobStairs.board()[i][j] instanceof Mortal){
							square = new DiferentSquare("/img/mortal.png");
						}else if(poobStairs.board()[i][j] instanceof Jumper){
							square = new DiferentSquare("/img/Jumper.png");
						}else if(poobStairs.board()[i][j] instanceof ReverseJumper){
							square = new DiferentSquare("/img/ReverseJumper.png");
						}else if(poobStairs.board()[i][j] instanceof Advance){
							square = new DiferentSquare("/img/Advance.png");
						}else if(poobStairs.board()[i][j] instanceof Regression){
							square = new DiferentSquare("/img/Regression.png");
						}else{
							square = new DiferentSquare("/img/Special.jpg");
						}
						square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
						JButton infoButton = new JButton("i");
						String inf = (poobStairs.board()[i][j]).getClass().getName().replace("domain.", "");
						infoButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(father,
										inf);

							}
						});
						square.add(infoButton);
					} else {
						square = new JPanel();
						if ((i + j) % 2 == 0)
							color = color2;
						else
							color = color1;
						square.setBackground(color);
						square.setOpaque(true);
						square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
					}

				}
				square.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 2));
				square.setBorder(new LineBorder(Color.BLACK, 1));
				for (Piece piece : poobStairs.board()[i][j].getPieces()) {
					if (piece != null) {
						VisualPiece visualPiece = new VisualPiece(piece.getColor(), piece.getRepresentation());
						visualPiece.setPreferredSize(
								new Dimension((int) Math.round(square.getPreferredSize().height * 1.2),
										(int) Math.round(square.getPreferredSize().height * 1.2)));
						visualPiece
								.setMaximumSize(new Dimension((int) Math.round(square.getPreferredSize().height * 1.5),
										(int) Math.round(square.getPreferredSize().height * 1.5)));
						visualPiece.setMinimumSize(new Dimension((int) Math.round(square.getPreferredSize().height * 2),
								(int) Math.round(square.getPreferredSize().height * 2)));
						visualPiece.setOpaque(true);
						square.add(visualPiece);
					}

				}
				board.add(square);

			}
		}
	}

	/**
	 * metodo que cambia la visión del tablero de juego
	 * 
	 * @param topic, modo en el cual se quiere ver el tablero ()
	 */

	protected void changeTemplate(String topic) {
		if (topic.equals("Clasic")) {
			color1 = new Color(255, 233, 90); // Amarillo
			color2 = new Color(70, 165, 162); // Azul
		} else if (topic.equals("Christmas")) {
			color1 = new Color(255, 255, 255);
			color2 = new Color(248, 61, 31);
		} else {
			color1 = new Color(255, 255, 255);
			color2 = new Color(0, 0, 0);
		}

	}

	protected String getTopics() {
		return (String) topics.getSelectedItem();
	}

	protected Integer getSpecials() {
		return (Integer) specials.getSelectedItem();
	}
	
	protected void buildDialog() {
		Dimension windowSize = father.getSize();
		saveDialog.setSize(windowSize.width / 2, windowSize.height / 2);
		Dimension dialogSize = saveDialog.getSize();
		saveDialog.setLocation((windowSize.width + dialogSize.width) / 2, (windowSize.height + dialogSize.height) / 2);
		saveDialog.setResizable(false);
		JPanel dialog = new JPanel();
		dialog.setBackground(new Color(232, 202, 175));
		dialog.setLayout(new GridBagLayout());
		JLabel title1 = new JLabel("Indique el Nombre de la Partida", JLabel.CENTER);
		title1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		JLabel title2 = new JLabel("que Desea Abrir", JLabel.CENTER);
		title2.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		saveName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		saveName.setBackground(new Color(168, 202, 186));
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
		
		dialog.add(saveName, constraints3);
		
		
		GridBagConstraints constraints4 = new GridBagConstraints();
		constraints4.insets = new Insets(10, 120, 0, 120);
		constraints4.gridx = 0;
		constraints4.gridy = 3;
		constraints4.gridwidth = 1;
		constraints4.gridheight = 1;
		constraints4.fill = GridBagConstraints.BOTH;
		
		dialog.add(save, constraints4);
		((POOBSTAIRSGUI) father).buildButton(dialog);
		saveDialog.setContentPane(dialog);
		
	}
	
	protected String getSaveName() {
		return saveName.getText();
	}
	
}
