package csp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

/**
 * La classe BacktrackSolver hérite de la classe AbstractSolver
 * et qui teste systématiquement l'ensemble des affectations potentielles du problème
 */
public class BacktrackSolver extends AbstractSolver {

    /**
     * creer une instance de la classe
     * @param variables est un ensemble de variable
     * @param constraintes est un ensemble de contrainte
     */
	public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraintes) {
		super(variables, constraintes);
	}

	/**
	 * Cette methode utilise l'algo de backtracking pour proposer une solution au problème
	 */
	@Override
	public Map<Variable, Object> solve() {
		return this.backTrack(new HashMap<>(), new LinkedList<Variable>(this.variables));
	}

	@Override
	public String getName() {
		return "Backstrack Solver";
	}

	/**
	 * Cette methode teste systématiquement l'ensemble des affectations potentielles du problème
	 * 
	 * @param partialInstantiation une affectation partielle
	 * @param uninstancifiedVariables la liste des variables non instancier
	 * @return
	 */
	public Map<Variable, Object> backTrack(Map<Variable, Object> partialInstantiation, LinkedList<Variable> uninstancifiedVariables) {
		if (uninstancifiedVariables.isEmpty()) {
			return partialInstantiation;
		}
		Variable x = uninstancifiedVariables.poll();
		for (Object v : x.getDomain()) {
			Map<Variable, Object> newPartial = new HashMap<>(partialInstantiation);
			newPartial.put(x, v);
			if (this.isConsistent(newPartial)) {
				Map<Variable, Object> newGoal = backTrack(newPartial, uninstancifiedVariables);
				if (newGoal != null) {
					return newGoal;
				}
			}
		}
		uninstancifiedVariables.add(x);
		return null;
	}
}
