package datamining;

import java.util.Set;

/**
 * cette interface définie une règle d'asssociation
 */
public interface AssociationRuleMiner {

	/**
	 * Cette méthode est un accesseur à la base de données transactionnelle (BooleanDatabase)
	 * @return la base 
	 */
	public BooleanDatabase getDatabase();

	/**
	 * Cette méthode prend une frequence minimale et une confiance minimale
	 * construit un ensemble de règle d’association de fréquence et confiance supérieures aux seuils donnés.
	 * @param minFrequency est la frequence minimale 
	 * @param minConfidence represente la confiance minimale
	 * @return une liste de regle d'association 
	 */
	public Set<AssociationRule> extract(float minFrequency,float minConfidence);
}
