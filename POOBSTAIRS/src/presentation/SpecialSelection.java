package presentation;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import domain.Advance;
import domain.Jumper;
import domain.Mortal;
import domain.QA;
import domain.Regression;
import domain.ReverseJumper;
import domain.Square;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
public class SpecialSelection extends JDialog{
	JComboBox<Integer> selection;
	JLabel infoSpecial;
	JButton confirm;
	POOBSTAIRSGUI owner;
	
	/**
	 * Constructor del JDialog para casillas especiales
	 * @param owner, frame owner del dialog
	 */
	SpecialSelection(JFrame owner){
		super(owner, "Selección de Casillas especiales");
		getContentPane().setBackground(new Color(232, 202, 175));
		this.owner = (POOBSTAIRSGUI)owner;
		prepareDialog();
		prepareActions();
		
	}
	/**
	 * Inicializa los componentes pertenecientes a la ventana
	 */
	private void prepareDialog() {
		setSize(350, 170);
		setLocation(0,0);
		setResizable(false);
		infoSpecial = new JLabel();
		selection = new JComboBox<Integer>();
		confirm = new JButton("Confirm");
		
		selection.setBackground(new Color(168, 202, 186));
		structure();
	}
	/**
	 * Metodo que le da la estructura a la ventana
	 */
	private void structure() {
		getContentPane().setLayout(null);
		JLabel title = new JLabel();
		title.setLayout(new GridLayout(2,1,0,3));
		JLabel title1 = new JLabel("Escoge la casilla a la cual te quieres mover");
		JLabel title2 = new JLabel("Y dale click a confirm");
		title.add(title1);
		title.add(title2);
		for(Component t: title.getComponents()) {
			((JLabel) t).setFont(new Font("Times New Roman", Font.PLAIN, 15));
			((JLabel) t).setHorizontalAlignment(SwingConstants.CENTER);
		}
		title.setBounds(10, 11, 314, 40);
		getContentPane().add(title);
		selection.setBounds(10, 62, 63, 22);
		getContentPane().add(selection);
		infoSpecial.setVerticalAlignment(SwingConstants.TOP);
		infoSpecial.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoSpecial.setBounds(83, 62, 241, 18);
		getContentPane().add(infoSpecial);
		confirm.setBounds(10, 95, 136, 25);
		getContentPane().add(confirm);
		owner.buildButton(getContentPane());
	}
	/**
	 * Metodo que se encarga de darle los valores a escoger dentro del JComboBox
	 * @param specialOp, casillas especiales dentro del rango de movimiento de la pieza en juego
	 * @param lastPosition, Posición maxima a la cual puede llegar la pieza en juego
	 */
	protected void build(Integer[] specialOp, int lastPosition) {
		
		for(Integer i: specialOp) {
			selection.addItem(i + 1);
		}
		selection.addItem(lastPosition);
	}
	
	/**
	 * Construye la información para cierta casilla
	 * @param square, casiila la cual esta siendo analizada
	 * @return, información de la casilla especial que esta siendo estudiada
	 */
	private String specials(Square square) {
		if(square instanceof Jumper) {
			return "Conmigo avanzas n casillas";
		}
		else if(square instanceof ReverseJumper) {
			return "Conmigo retrocedes n casilla";
		}else if(square instanceof Advance) {
			return "Conmigo vas hasta la siguiente escalera";
		}else if(square instanceof Regression) {
			return "Conmigo vas hasta la anterior serpiente";
		}else if(square instanceof QA) {
			return "A mi me tienes que contestar una pregunta para avanzar";
		}else if(square instanceof Mortal){
			return "Yo te devuelvo a la casilla inicial";
		}
		else return "Yo soy una casilla Normal";
		
	}
	/**
	 * Se preparan las acciones pertenecientes a los componentes del JDialog
	 */
	private void prepareActions() {
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(SpecialSelection.this, "No puede cerrar esta ventana sin realizar un movimiento");
				setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			}
			
			
		});
		 selection.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Square[] block =  owner.getLineBoard();
					try {
						infoSpecial.setText(specials(block[((Integer)selection.getSelectedItem()) - 1]));
					}catch(NullPointerException ex) {
						infoSpecial.setText("");
					}
					
				}
			});
		 
		 confirm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					owner.goToSelected((Integer)selection.getSelectedItem());
					SpecialSelection.this.setVisible(false);
					selection.removeAllItems();
					
				}
			});
	}
}
