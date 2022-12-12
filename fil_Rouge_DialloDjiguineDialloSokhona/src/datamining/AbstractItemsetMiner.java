package datamining;

import java.util.Comparator;
import java.util.Set;

import representation.BooleanVariable;

/**
 * Cette class represente un extracteur de données
 */
public abstract class AbstractItemsetMiner implements ItemsetMiner{
	protected BooleanDatabase dataBase;
	/**
	 * Construit une instance 
	 * @param base est la base donnée
	 */
	public AbstractItemsetMiner(BooleanDatabase base) {
		this.dataBase= base;
	}

	/**
	 * Cette méthode est un accesseur à la base de données transactionnelle (BooleanDatabase)
	 * @return une instance de BooleanDatbase
	 */
	public BooleanDatabase getBase() {
		return dataBase;
	}

	/**
	 * Cette méthode permet de calculer la fréquence d'un ensemble d'items
	 * @param items est un ensemble d'items, c'est-à-dire des instances de notre classe BooleanVariable
	 * @return la fréquence des items
	 */
	public float frequency(Set<BooleanVariable> items) {
		float compteur=0;
		for(Set<BooleanVariable> bv1 : getBase().getTransactions()) {
			if(bv1.containsAll(items)) {
				compteur++;
			}
		}
		return compteur/getBase().getTransactions().size();
	}
	
	public static final Comparator < BooleanVariable > COMPARATOR =
			( var1 , var2 ) -> var1 . getName (). compareTo ( var2 . getName ());
	
	@Override
	public String toString() {
		return "AbstractItemsetMiner [database=" + this.dataBase + "]";
	}
}
