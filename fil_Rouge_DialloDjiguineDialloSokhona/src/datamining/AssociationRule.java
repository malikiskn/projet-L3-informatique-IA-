package datamining;

import java.util.Set;

import representation.BooleanVariable;

/**
 * Cette classe permet de definir des règles d'associations.
 */
public class AssociationRule {
	private Set<BooleanVariable> premise;
	private Set<BooleanVariable> conclusion;
	private float frequence,confidence;
	
	/**
	 * Construit une instance 
	 * @param premisse représente les premisses
	 * @param conclusion représente la conclusion
	 * @param frequence représente la frequence 
	 * @param confiance représente la confiance 
	 */
	public AssociationRule(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, float frequence, float confiance) {
		this.premise = premisse;
		this.conclusion = conclusion;
		this.frequence = frequence;
		this.confidence = confiance;
	}

	/**
	 * Est un accessseur sur les premisses (qui est une propsition de règle d'association)
	 * @return l'ensemble des premisses
	 */
	public Set<BooleanVariable> getPremise() {
		return premise;
	}

	/**
	 * Est un acceseur sur la conclusion qui, etant le resultat, va dependre de la valité des premisses
	 * @return la conclusion
	 */
	public Set<BooleanVariable> getConclusion() {
		return conclusion;
	}

	/**
	 * Est un accesseur sur la frequence qui, represente le poids de representativité d'une règle.
	 * @return la frequence de la regle
	 */
	public float getFrequency() {
		return frequence;
	}

	/**
	 * Est un accesseur sur la confiance qui fait les liens entre les premisses et conclusion
	 * la confiance represente la probabilité de chance que la règle 
	 * apparaisse dans la transaction
	 * @return la confiance
	 */
	public float getConfidence() {
		return confidence;
	}

	@Override
	public String toString() {
		return "AssociationRule [premisse=" + premise + ", conclusion=" + conclusion + ", frequence=" + frequence
				+ ", confiance=" + confidence + "]";
	}
}
