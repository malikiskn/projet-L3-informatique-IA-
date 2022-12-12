package planning;

import java.util.Map;

import representation.Variable;

public interface Heuristic {
    
    /**
     * Cette méthode permet d'estimer le cout d'un plan optimal depuis un etat fourni en parametre
     * @param state est l'etat 
     * @return une l'estimation de type float
     */
	float estimate(Map<Variable, Object> state);
}
