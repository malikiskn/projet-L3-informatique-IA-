package planning;

import java.util.List;
import java.util.Map;
import java.util.Set;

import representation.Variable;

public interface Planner {
    
    /**
     * Cette méthode construit une liste d'action permettant de passer d'un etat initial à un etat qui satisfait le but.
     * @return une liste d'action ou null s’il n’y en a pas pour l’instance considérée
     */
	List<Action> plan();

	/**
	 * Cette methode renvoie l'etat initial
	 * @return un etat initial
	 */
	Map<Variable, Object> getInitialState();

	/**
	 * Cette methode renvoie la liste des actions
	 * @return une liste d'action 
	 */
	Set<Action> getActions();

	/**
	 * Cette methode renvoie le but
	 * @return un but
	 */
	Goal getGoal();

    /**
     * Cette methode renvoie le nom du planneur
     * @return le nom du planneur
     */
    public String getName();

	/**
	 * Cette methode renvoie le nombre de sondes
	 * @return le nombre de sondes
	 */
	int getSonde();
}
