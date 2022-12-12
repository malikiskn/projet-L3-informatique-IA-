package datamining;

import java.util.Set;

/**
 * cette interface definie un extrateur de motif suivant une frequence
 */
public interface ItemsetMiner {

	/**
	 * Cette méthode est un accesseur à la base de données transactionnelle (BooleanDatabase)
	 * @return une instance de BooleanDatbase
	 */
	public BooleanDatabase getDatabase();

	/**
	 * Cette méthode prend une frequence minimale et determine les itemsets (Ensemble constitué d'un ensemble d'items et sa fréquence)
	 * de frequence superieur ou egal a cette frequence.
	 * @param minFrequency est la frequence minimale 
	 * @return une liste d'itemsets 
	 */
	public Set<Itemset> extract(float minFrequency);
}
