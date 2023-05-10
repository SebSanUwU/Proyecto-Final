package presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import domain.Advance;
import domain.Jumper;
import domain.Obstacle;
import domain.POOBSTAIRSException;
import domain.Piece;
import domain.PoobStairs;
import domain.QA;
import domain.Regression;
import domain.ReverseJumper;
import domain.SpecialSquare;
import domain.Square;

public class GamePane extends JPanel {
	private JFrame father;
	protected JPanel board, dataOrChoose;
	protected JLabel whoPlays, die, numSnakes, numStairs, numSpecials, numModifiers, maxPosition, extraMoves;
	protected JButton roll, change, endGame, confirm;
	protected JComboBox topics;
	protected JComboBox<Integer>specials;
	private Color color1, color2;
	/**
	 * Create the panel.
	 */
	public GamePane(JFrame father) {
		super();
		this.father = father;
		prepareElements();
		buildGame();

	}
	
	/**
	 * Inicializa todos los elementos del panel
	 */
	private void prepareElements() {
		this.setLayout(new BorderLayout(5,5));
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
		String[] themes = {"Clasic", "Christmas", "Chess"};
		endGame = new JButton("Salir del Juego");
		topics = new JComboBox(themes);
		dataOrChoose = new JPanel();
		confirm = new JButton("Confirm");
		specials = new JComboBox<Integer>();
		
	}
	/**
	 * Metodo que se encarga de estructurar el JPanel initGame
	 */
	
	
	private void  buildGame() {
		//Se dan las caracteristicas al tablero
		board.setOpaque(false);
		this.setBorder(new EmptyBorder(20,20,20,20));
		this.add(board, BorderLayout.CENTER);
		JPanel gameOptions = new JPanel();
		//Se crea un JPanel de opciones e indicaciones para realizar el juego de forma correcta 
		gameOptions.setBorder(new CompoundBorder(new EmptyBorder(1,2,1,2),
				new TitledBorder("Información Juego")));
		gameOptions.setLayout(new GridLayout(3,1));
		gameOptions.setPreferredSize(new Dimension(230,HEIGHT));
		gameOptions.setOpaque(false);
		this.add(gameOptions, BorderLayout.EAST);
		
		//Se crea la división de estado de juego
		
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
		info.setLayout(new GridLayout(5,2,2,2));
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
		for(Component component: info.getComponents()) {
			((JLabel)component).setOpaque(false);
		}
		info.setOpaque(false);

		//Se crea la división del dado
		JPanel dataDie = new JPanel();
		dataDie.setBorder(new LineBorder(new Color(0, 0, 0)));
		dataDie.setLayout(new BorderLayout(1,10));
		
		dataDie.add(roll,BorderLayout.NORTH);
		die.setHorizontalAlignment(SwingConstants.CENTER);
		die.setOpaque(false);
		assignValue(1);
		dataDie.add(die, BorderLayout.CENTER);
		dataOrChoose.setLayout(new CardLayout());
		dataOrChoose.add(extraMoves);
		dataOrChoose.setOpaque(false);
		JPanel chooseDie = new JPanel();
		chooseDie.setLayout(new GridLayout(1,2,4,0));
		chooseDie.setOpaque(false);
		chooseDie.setBorder(new EmptyBorder(0,1,2,1));
		chooseDie.add(specials);
		chooseDie.add(confirm);
		specials.setBackground(new Color(168, 202, 186));
		dataOrChoose.add(chooseDie);
		dataDie.add(dataOrChoose, BorderLayout.SOUTH);
		((POOBSTAIRSGUI) father).buildButton(chooseDie);
		((POOBSTAIRSGUI) father).buildButton(dataDie);
		dataDie.setOpaque(false);
		gameOptions.add(dataDie);
		
		//Se crea la división de extra button
		
		JPanel extraButtons = new JPanel();
		extraButtons.setBorder(new LineBorder(new Color(0, 0, 0)));
		extraButtons.setLayout(new GridLayout(2,1));
		JPanel topic = new JPanel();
		topic.setLayout(new GridLayout(1,2,5,5));
		topic.setBorder(new EmptyBorder(10,5,(int) Math.round(father.getSize().height * 0.2),5));
		topic.add(topics);
		topic.add(change);
		extraButtons.add(topic);
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
	 * @param value, valor actual del dado
	 */
	protected void assignValue(int value) {
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
	
	/**
	 * Se encarga de representar de manera visual el estado actual del tablero
	 * @param game, juego que va a ser representado
	 */
	protected void refresh(PoobStairs poobStairs) {
		Component component[] = board.getComponents();
        for(Component c: component){
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
							JOptionPane.showMessageDialog(father, 
							"Head: " + (obstacle.getHead().getNumSquare() + 1) + "//" +
							"Tail: " + (obstacle.getTail().getNumSquare() + 1));

						}
					});

					square.add(infoButton);
					
				}catch(POOBSTAIRSException e) {
					if(poobStairs.board()[i][j] instanceof SpecialSquare){
						square = new DiferentSquare("/img/Special.jpg");
						square.add(new JLabel(String.valueOf(poobStairs.board()[i][j].getNumSquare() + 1)));
						JButton infoButton = new JButton("i");
						String inf = specials(poobStairs.board()[i][j]);
						infoButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(father, 
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
	 * Construye la información para cierta casilla especial
	 * @param square, casiila espeacial
	 * @return, información de la casilla especial que esta siendo estudiada
	 */
	private String specials(Square square) {
		if(square instanceof Jumper) {
			return "Conmigo avanzas n casilla";
		}
		else if(square instanceof ReverseJumper) {
			return "Conmigo retrocedes n casilla";
		}else if(square instanceof Advance) {
			return "Conmigo vas hasta la siguiente escalera";
		}else if(square instanceof Regression) {
			return "Conmigo vas hasta la anterior serpiente";
		}else if(square instanceof QA) {
			return "A mi me tienes que contestar una pregunta para avanzar";
		}else {
			return "Yo te devuelvo a la casilla inicial";
		}
		
	}
	
	/**
	 * metodo que cambia la visión del tablero de juego
	 * @param topic, modo en el cual se quiere ver el tablero ()
	 */

	protected void changeTemplate(String topic) {
		if (topic.equals("Clasic")) {
			color1 = new Color(255, 233, 90); //Amarillo
			color2 = new Color(70, 165, 162); //Azul
		}else if(topic.equals("Christmas")) {
			color1 = new Color (255,255,255);
			color2 = new Color (248,61,31);
		}else {
			color1 = new Color (255,255,255);
			color2 = new Color (0,0,0);
		}
		
	}
	

	protected String getTopics(){
		return (String)topics.getSelectedItem();
	}
	

	
	protected Integer getSpecials() {
		return (Integer)specials.getSelectedItem();
	}

}
