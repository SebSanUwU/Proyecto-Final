package presentation;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Interfaz Gráfica para el juego POOBSTAIRS
 * @author Castaño, Camargo
 * 2/05/2023
 */

public class POOBSTAIRSGUI extends JFrame {
	private JPanel inicio;
	private JButton newGame;
	private JButton lastGame;
	private JButton exit;
	private POOBSTAIRSGUI() {
		prepareElements();
		
	}
	
	private void prepareElements() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        Dimension frameSize = this.getSize();
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        inicio = new JPanel();
        newGame = new JButton("Nueva Partida");
        newGame.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 15));
        newGame.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        newGame.setBackground(new Color(168,202,186));
        newGame.setOpaque(true);
        lastGame = new JButton("Cargar Partida");
        lastGame.setBackground(new Color(168,202,186));
        lastGame.setOpaque(true);
        exit = new JButton("Salir del Juego");
        exit.setBackground(new Color(168,202,186));
        exit.setOpaque(true);
        inicio.add(newGame);
        inicio.add(lastGame);
        inicio.add(exit);
        setContentPane(inicio);
        inicio.setLayout(new GridLayout(3,1,6,6));
        inicio.setBorder(new EmptyBorder(60,(int) (getSize().width * 0.3), 60, (int)(getSize().width * 0.3)));
	}
	
	public static void main(String[] args) {
		POOBSTAIRSGUI prueba = new POOBSTAIRSGUI();
		prueba.setVisible(true);
	}
}
