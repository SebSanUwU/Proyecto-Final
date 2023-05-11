package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class IndependentPane extends JPanel {
	protected JFrame father;
	/**
	 * Constructor de la clase IndependentPane
	 */
	protected IndependentPane(JFrame father) {
		super();
		this.father = father;
		prepareElements();
	}
	
	 
	/**
	 * Inicializa todos los elementos del panel
	 */
	protected abstract void prepareElements();
	/**
	 * Crea la estructura del Panel
	 */
	protected abstract void build();
	
	
}
