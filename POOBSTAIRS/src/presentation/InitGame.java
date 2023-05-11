package presentation;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

public class InitGame extends IndependentPane{
	/*initGame*/
	protected JButton beggin;
	protected JSpinner dataRows, dataColumns, dataSnakes, dataStairs, dataSpecials, dataPowers;
	
	/**
	 * Create the panel.
	 */
	public InitGame(JFrame father) {
		super(father);
		prepareElements();
	}
	/**
	 * Se encarga de preparar todos los elementos del panel para ser utilizados
	 */
	public void prepareElements() {
		beggin = new JButton("Comenzar");
		dataRows= new JSpinner(new SpinnerNumberModel(3,3,50,1));
		dataColumns= new JSpinner(new SpinnerNumberModel(3,3,50,1));
		dataSnakes= new JSpinner(new SpinnerNumberModel(0,0,50,1));
		dataStairs= new JSpinner(new SpinnerNumberModel(0,0,50,1));
		dataSpecials= new JSpinner(new SpinnerNumberModel(0.0,0.0,1.05,0.05));
		dataPowers= new JSpinner(new SpinnerNumberModel(0.0,0.0,1.05,0.05));
		build();
	}
	/**
	 * Se encarga de inicializar los elementod del Panel
	 */
	
	public void build() {
		JLabel title = new JLabel("Datos del Tablero");
		title.setFont(new Font("Times New Roman", Font.PLAIN, 22));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel rows = new JLabel("Numero de Filas:");
		JLabel columns = new JLabel("Numero de Columnas:");
		JLabel snakes = new JLabel("Numero de Serpientes:");
		JLabel stairs = new JLabel("Numero de Escaleras:");
		JLabel specials = new JLabel("%Casilla Especial:");
		JLabel powers = new JLabel("%Modificador:");
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
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
		this.setBorder(new EmptyBorder(90, (int) (father.getSize().width * 0.3), 0, 0));
		((POOBSTAIRSGUI)father).buildLabels(this, title.getText());
		((POOBSTAIRSGUI)father).buildButton(this);
		
	}
	protected Integer getDataRows() {
		return (Integer) dataRows.getValue();
	}
	
	protected Integer getDataColumns() {
		return (Integer)dataColumns.getValue();
	}
	
	protected Integer getDataSnakes() {
		return  (Integer)dataSnakes.getValue();
	}
	
	protected Integer getDataStairs() {
		return  (Integer)dataStairs.getValue();
	}
	
	protected Double getDataPowers() {
		return (Double) dataPowers.getValue();
	}
	
	protected Double getDataSpecials() {
		return (Double)dataSpecials.getValue();
	}
	
	

}
