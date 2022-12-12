package representation;

import java.util.Map;
import java.util.Set;

public interface Constraint {
    
    /**
     * 
     * @return l'ensemble des variables concernées par une contrainte
     */
	Set<Variable> getScope();

	/**
	 * Cette méthode vérifie la satisfaibilité d'une contrainte sur une instanciation donnee
	 * @param instantiation est une liste d'association de variable a une valeur de son domain
	 * @return true ou false selon que la contrainte est satisfaite ou pas 
	 */
	boolean isSatisfiedBy(Map<Variable, Object> instantiation);
}
