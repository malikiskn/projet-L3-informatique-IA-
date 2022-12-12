package csp;

import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

/**
 * Cette classe décrit la base d'implémentation d'un solveur de contraintes
 *
 */
public abstract class AbstractSolver implements Solver {
	protected Set<Variable> variables;
	protected Set<Constraint> constraintes;
	
	/**
	 * crée une instance de la classe
	 * @param variables est un ensemble de variable
	 * @param constraintes est un ensemble de contrainte
	 */
	public AbstractSolver(Set<Variable> variables, Set<Constraint> constraintes) {
		this.variables = variables;
		this.constraintes = constraintes;
	}
	
	/**
	 * Une methode qui prend en argument une affectation partielle des variables et qui returne
	 * un booleen indiquant si cette affectation satisfait toutes les contraintes qui portent sur des
	 * variables instanciées dans l'affectation.
	 * 
	 * @param partialInstantiation une affectation partielle des variables
	 * @return boolean
	 */
	public boolean isConsistent(Map<Variable, Object> partialInstantiation) {
		for (Constraint constraint : constraintes) {
			if (partialInstantiation.keySet().containsAll(constraint.getScope())) {
				if (!constraint.isSatisfiedBy(partialInstantiation)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * la methode d'affichage d'AbstractSolver
	 */
	@Override
	public String toString() {
		return "AbstractSolver [variables=" + variables + ", constraintes=" + constraintes + "]";
	}

	/**
	 * Une methode qui renvoie les variables du solver
	 * 
	 * @return Set<Variable>
	 */
    public Set<Variable> getVariables() {
        return variables;
    }

    /**
     * Une methode qui renvoie les contraintes du solver
     * @return Set<Constraint>
     */
    public Set<Constraint> getConstraintes() {
        return constraintes;
    }
	
}
