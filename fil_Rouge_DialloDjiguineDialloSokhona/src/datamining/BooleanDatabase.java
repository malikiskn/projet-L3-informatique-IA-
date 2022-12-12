package datamining;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import representation.BooleanVariable;

/**
 * Cette classe est une représentation des données de notre base transactionnelle
 */
public class BooleanDatabase {
	private Set<BooleanVariable> items;
	private List<Set<BooleanVariable>> transactions;
	
	/**
	 * Construit une instance de la classe 
	 * @param items est un ensemble d'items
	 */
	public BooleanDatabase(Set<BooleanVariable> items) {
		this.items=items;
		this.transactions = new ArrayList<>();
	}
	
	/**
	 * Cette méthode permet d'ajouter une transaction dans notre base 
	 * c'est a dire un ensemble d'items
	 * @param items
	 */
	public void add(Set<BooleanVariable> items) {
		this.transactions.add(items);
	}
	
	/**
	 * Est un accesseur sur les items qui sont des instances de la classe BooleanVariable
	 * @return l'ensemble des items
	 */
	public Set<BooleanVariable> getItems() {
		return items;
	}

	/**
	 * Cette méthode permet de retourner la liste des transactions de notre base 
	 * qui représente un ensemble d'ensemble d'items.
	 * @return une liste de toutes les transactions
	 */
	public List<Set<BooleanVariable>> getTransactions(){
		return this.transactions;
	}

	@Override
	public String toString() {
		return "BooleanDatabase [items=" + items + "]";
	}
	
}
