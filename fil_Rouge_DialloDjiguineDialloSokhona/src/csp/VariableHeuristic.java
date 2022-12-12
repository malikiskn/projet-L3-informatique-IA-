package csp;

import java.util.Map;
import java.util.Set;

import representation.Variable;

/**
 * Cette interface définie un type d'heuristique qui choisi un meilleur element d'un ensemble suivant un critère
 */
public interface VariableHeuristic {
    /**
     * La methode prend un ensemble de variable et un ensemble de domaines et retourne la meilleure variable
     * selon l'heuristique
     * 
     * @param variables un ensemble de variables
     * @param domaines un ensemble de variable
     * @return Variable retourne la meilleure variable de (variables)
     */
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines);
}
