package domain;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
/**
 * Clase que se encarga de abstraer un dado compuesto por caras.
 * @author Castaño-Camargo
 *04 de Mayo del 2023
 */

public class Die {
	private int numPowers;
	private ArrayList<Face> faces;
	private Random random;
	/**
	 * Metodo constructor de un Objeto de la clase Dado
	 * @param numFaces, numero de caras que va a tener el dado.
	 * @param percentage, probabilidad de que te salga un modificador en el dado (Numero de caras que van a tener
	 * poder)
	 * 
	 */
	public Die(int numFaces, float percentage) {
		faces = new ArrayList<Face>();
		random = new Random();
		numPowers = (int) Math.round(numFaces*percentage);
		assemble(numFaces);
		
	}
	/**
	 * Metodo que ensambla las caras del dado
	 * @param numFaces, numero de caras quue va a tener el dado.
	 */
	private void assemble(int numFaces) {
		for(int i = 1; i <= numFaces; i++) {
			faces.add(new Face(i));
		}
	}
	/**
	 * Metodo que de forma aleatoria distribuye los modificadores que puede tener el dado atravez de 
	 * sus caras
	 * Cada vez que se valla a lanzar el dado, este metodo es invocado para cambiar los modificadores
	 * y la cara en la cual se ubican
	 */
	private void assignPowers() {
		int powersToGive = numPowers;
		
		for(Face f: faces) {
			f.removePowers();
		}
		ArrayList<Face> emptyFaces = (ArrayList<Face>) faces.clone();
		int i = 0;
		while(powersToGive > 0) {
			if(random.nextBoolean()) {
				try {
					emptyFaces.get(i).addPower(Power.givePowers()[random.nextInt(3)]);
				} catch (POOBSTAIRSException e) {
					emptyFaces.remove(emptyFaces.get(i));
				}
				powersToGive--;
			}
			i++;
			if(i == emptyFaces.size()) i = 0;
		}
	}
	
	/**
	 * Función que simula el lanzamiento de un dado.
	 * @return currentFace, cara ganadora selecciona de forma aleatoria
	 */
	
	public Face roll() {
		assignPowers();
		return faces.get(random.nextInt(faces.size()));
	}
	
	/**
	 * Clase que se encarga de declarar a los objetos de tipo Face que componen a un dado
	 * @author Castaño-Camargo
	 * 04 de Mayo del 2023
	 *
	 */
	public class Face{
		private int value;
		private HashSet<String> powers;
		private static final int LIM_POWERS = 1;
		
		/**
		 * Contructor del objeto de la clase Face
		 * @param value, valor numero de la cara
		 */
		Face(int value) {
			this.value = (int)value;
			powers = new HashSet<String>();
			
		}
		/**
		 * Metodo que se encarga de eliminar los poderes pertenecientes a la cara del dado.
		 */
		private void removePowers() {
			powers.clear();
		}
		/**
		 * 
		 * @return powersS, arreglo tipo String que indica los poderes que contiene la cara del dado.
		 * @throws NO_POWERS, si a la cara no se le asigno ningun poder 
		 */
		
		public String[] indicatePowers() throws POOBSTAIRSException{
			if(powers.size() == 0) throw new POOBSTAIRSException(POOBSTAIRSException.NO_POWERS);
			String[] powersS = new String[powers.size()];
			powersS = powers.toArray(powersS);
			return powersS;
		}
		/**
		 * 
		 * @param power, poder que se le asigna a la cara del dado
		 * @throws SUP_POWERS, si se quieren asignar más poderes de los que puede aceptar el dado.
		 */
		
		private void addPower(String power) throws POOBSTAIRSException{
			if(powers.size() + 1 > LIM_POWERS) throw new POOBSTAIRSException(POOBSTAIRSException.SUP_POWERS);
			powers.add(power);
		}
		
		public int getValue() {
			return value;
		}
		
	}
	
}
