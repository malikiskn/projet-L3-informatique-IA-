package csp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

/**
 * Cette classe solveur de contrainte qui verifies l'arc coherence des domaines de
 * variables pour optimiser la recherche de la solution
 */
public class MACSolver extends AbstractSolver {
    private ArcConsistency arcConsistency;

    /**
     * Constructeur de la classe MACSolver
     * @param variables un ensemble de variables
     * @param constraintes un ensemble de contraintes
     */
	public MACSolver(Set<Variable> variables, Set<Constraint> constraintes) {
		super(variables, constraintes);
		this.arcConsistency = new ArcConsistency(constraintes);
	}

	@Override
	public Map<Variable, Object> solve() {
	    Map<Variable, Set<Object>> domaines = new HashMap<Variable, Set<Object>>();
        for (Variable variable : this.variables) {
            domaines.put(variable, variable.getDomain());
        }
		return this.mac(new HashMap<>(), new LinkedList<Variable>(this.variables), domaines);
	}

    @Override
    public String getName() {
        return "Mac Solver";
    }

    /**
     *Cette methode applique l'algorithme de BackTracking avec l'arc consistency
     * il réduit les domaines avant d'entamer la resolution du problème en fixant les
     * domaines des variables.
     *
     * @param partialInstantiation l'affectation partielle
     * @param uninstacifiedVariables liste des variables non instancier
     * @param domaines l'ensemble des domaines
     * @return une solution qui étend la solution partielle si elle existe ou nul s'il n'y pas de solution
     */
    public Map<Variable, Object> mac(Map<Variable, Object> partialInstantiation, LinkedList<Variable> uninstacifiedVariables, Map<Variable, Set<Object>> domaines) {
	    if (uninstacifiedVariables.isEmpty()){
            return partialInstantiation;
        }else {
            ArcConsistency arcConsistency = new ArcConsistency(this.constraintes);
            if (!arcConsistency.ac1(domaines)){
                return  null;
            }
            Variable variable= uninstacifiedVariables.poll();
            for (Object objet: variable.getDomain()) {
                Map<Variable, Object> paritel = new HashMap<>(partialInstantiation);
                paritel.put(variable,objet);
                Map<Variable, Set<Object>> domainetmp = new HashMap<>();
                domainetmp.putAll(domaines);
                domainetmp.put(variable, new HashSet<>(Collections.singleton(objet)));
                if (isConsistent(paritel)){
                    Map<Variable,Object> resultat = mac(paritel,uninstacifiedVariables,domainetmp);
                    if (resultat!=null){
                        return resultat;
                    }
                }
            }
            uninstacifiedVariables.add(variable);
            return null;
        }
	}

    /**
     * cette fonction return l'arc coherence
     * @return ArcConsistency
     */
    public ArcConsistency getArcConsistency() {
        return arcConsistency;
    }

}
