package datamining;

import java.util.Set;

import representation.BooleanVariable;

/**
 * Cette permet de représenter un motif.
 */
public class Itemset {
	private Set<BooleanVariable> items;
	private  float frequence ;
	
	/**
	 * Construit une instance de la classe 
	 * @param items est un ensemble items
	 * @param frequence représente une valeur  
	 */
	public Itemset(Set<BooleanVariable> items, float frequence) {
		super();
		this.items = items;
		this.frequence = frequence;
	}

	/**
	 * Est un accesseur sur les items qui sont des instances de la classe BooleanVariable
	 * @return  un ensemble contenant tous les items
	 */
	public Set<BooleanVariable> getItems() {
		return items;
	}

	/**
	 * Cette méthode est accessseur sur la frequence d'un motif définissant si oui ou non ce dernier est frequent
	 * @return la valeur de la frequence
	 */
	public float getFrequency() {
		return frequence;
	}

	@Override
	public String toString() {
		return "Itemset [items=" + items + ", frequence=" + frequence + "]";
	}

	

	
	
	
}
