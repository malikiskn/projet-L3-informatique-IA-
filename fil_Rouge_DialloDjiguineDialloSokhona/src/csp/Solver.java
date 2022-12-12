package csp;

import java.util.Map;

import representation.Variable;

/**
 * Cette interface définie un Solver
 */
public interface Solver {
    
    /**
     * solve une methode qui résout un problème et qui propose une solution ou null s'il n'y a pas
     * @return Map<Variable, Object>
     */
	public Map<Variable, Object> solve();
    public String getName();
}
