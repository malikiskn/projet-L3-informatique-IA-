package planning;

import java.util.Map;

import representation.Variable;

public interface Action {
    
    /**
     * Cette méthode teste si une action est applicable ou non dans un etat
     * @param state est une liste d'association de variables a une valeur de leurs domaines respectifs
     * @return un boolean selon que l'action est applicable ou non
     */
	boolean isApplicable(Map<Variable, Object> state);

	/**
	 * Cette méthode construit le successeur d'un etat (state)
	 * @param state est une liste d'association de variables a une valeur de leurs domaines respectifs
	 * @return une nouvel etat ou null selon l'applicabilite de l'action
	 */
	Map<Variable, Object> successor(Map<Variable, Object> state);

	/**
	 * Cette methode retourne le cout de l'action
	 * @return le cout de type entier
	 */
	int getCost();
}
