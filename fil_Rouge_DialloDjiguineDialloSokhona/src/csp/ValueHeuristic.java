package csp;

import java.util.List;
import java.util.Set;

import representation.Variable;

/**
 * Cette interface définie un type d'heuristique qui ordonne un ensemble suivant un critère
 */
public interface ValueHeuristic {
    
    /**
     * Cette methode retourne une liste de valeurs ordonnées selon l'heuristique
     * @param variable un ensemble de variables
     * @param domaine un ensemble de domaines
     * @return List<Object> liste ordonnée des domaines
     */
    public List<Object> ordering(Variable variable, Set<Object> domaine);
}
