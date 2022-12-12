package csp;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import representation.Constraint;
import representation.Variable;

/**
 * Cette classe permet de définir une arc coherence de nœuds à fin de rendre un csp coherent
 */
public class ArcConsistency {
    private Set<Constraint> constraints;
    private Set<Constraint> unaryConstraint;
    private Set<Constraint> binaryConstraint;
    private Set<Variable> unaryVariable;

    /**
     * crée une instance de la classe
     * @param constraints, un ensemble de contrainte
     */
    public ArcConsistency(Set<Constraint> constraints) {
        this.unaryConstraint = new HashSet<>();
        this.unaryVariable = new HashSet<>();
        this.binaryConstraint = new HashSet<>();
        for (Constraint constraint: constraints) {
            Set <Variable> scope = constraint. getScope ();
            if ( scope.size() == 1) {
                Variable var = scope.iterator().next ();
                this.unaryVariable.add(var);
                unaryConstraint.add(constraint);
            } else if ( scope . size ()==2) {
                Iterator< Variable > it = scope.iterator();
                Variable var1 = it.next();
                Variable var2 = it.next();
                this.binaryConstraint.add(constraint);
            } else {
                throw new IllegalArgumentException("Ni unaire ni binaire ");
            }
            this.constraints = constraints;
        }
    }

    /**
     * Une methode qui supprime les valeurs des domaines pour les quelles il existe une contrainte unaire
     * non satisfaite par la valeur. Elle retourne faux si au moins un domaine a ete vide sinon vrai
     * 
     * @param domaines c'est l'ensemble de domaines a traite
     * @return boolean
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domaines) {
        Map<Variable, Set<Object>> satisfiedDomaine = new HashMap<>();
        satisfiedDomaine.putAll(domaines);
        for (Variable variable: this.unaryVariable){
            for (Object object: domaines.get(variable)) {
                for (Constraint constraint: this.unaryConstraint) {
                    Map<Variable, Object> mapEssaie = Collections.singletonMap(variable, object);
                    if (constraint.getScope().contains(variable)){
                        if (!constraint.isSatisfiedBy(mapEssaie)) {
                            Set<Object> domaineReduit = new HashSet<>(satisfiedDomaine.get(variable));
                            domaineReduit.remove(object);
                            satisfiedDomaine.put(variable, domaineReduit);
                        }
                    }
                }
            }
        }
        for (Variable variable: domaines.keySet()){
            domaines.put(variable, satisfiedDomaine.get(variable));
        }
        for (Map.Entry<Variable, Set<Object>> entry : domaines.entrySet()) {
            Set<Object> domaine = entry.getValue();
            if (domaine.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cette methode supprime toutes les valeurs de la variable (variable1) de domaine (domaines1)
     * pour lesquels il n'existe aucune valeur de la variable (variable2) de domaine (domaines2)
     * 
     * @param variable1 represente la premiere variable
     * @param domaines1 est le domaine de la premiere variable (variable1)
     * @param variable2 represente la deuxieme variable
     * @param domaines2 est le domaine de la deuxieme variable (variable2)
     * @return boolean vrai si au moins une valeur a ete supprime du domaine (domaines1) sinon faux
     */
    public  boolean revise(Variable variable1, Set<Object> domaines1, Variable variable2, Set<Object> domaines2){
        boolean del = false;
        Set<Object> domainToRemoves = new HashSet<>();
        Map<Variable,Object> partialInstantiation = new HashMap<>();

        for (Object domaine1: domaines1) {
            boolean viable = false;
            for (Object domaine2 : domaines2) {
                boolean allSatisfied = true;
                for (Constraint binarayConstraint : binaryConstraint) {
                    if (binarayConstraint.getScope().contains(variable1) && binarayConstraint.getScope().contains(variable2)) {
                        partialInstantiation.put(variable1, domaine1);
                        partialInstantiation.put(variable2, domaine2);
                        if (!binarayConstraint.isSatisfiedBy(partialInstantiation)) {
                            allSatisfied = false;
                            break;
                        }
                    }
                }
                if (allSatisfied){
                    viable = true;
                    break;
                }
            }
            if (!viable){
                domainToRemoves.add(domaine1);
                del = true;
            }
        }
        domaines1.removeAll(domainToRemoves);
        return del;
    }

    /**
     * Cette methode tous les domaines jusqu'à stabilité
     * @param domaines affectation variables domaines
     * @return boolean faux si au moins un domaine a ete vide sinon vrai
     */
    public boolean ac1(Map<Variable, Set<Object>> domaines) {
        boolean change= false;
        if (!enforceNodeConsistency(domaines)) {
            return false;
        }
        do {
            change = false;
            for (Variable variable1 : domaines.keySet()) {
                Set<Object> domain = new HashSet<>(domaines.get(variable1));
                for (Variable variable2 : domaines.keySet()) {
                    if (!variable2.equals(variable1) && revise(variable1,domain,variable2,domaines.get(variable2))){
                        change = true;
                    }
                }
                domaines.put(variable1, domain);
            }
        }while (change);

        for (Variable variable: domaines.keySet()){
            if (domaines.get(variable).isEmpty() ){
                return false;
            }
        }
        return true;
    }

    /**
     * La methode renvoie toutes les contraintes
     * @return Set<Constraint>
     */
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    /**
     * La methode renvoie les contraintes portant sur une seule variable
     * @return Set<Constraint>
     */
    public Set<Constraint> getUnaryConstraint() {
        return unaryConstraint;
    }

    /**
     * La methode renvoie les contraintes portant sur l'implication de deux variables
     * @return Set<Constraint>
     */
    public Set<Constraint> getBinaryConstraint() {
        return binaryConstraint;
    }

    /**
     * La methode renvoie les variables qui interviennent dans les contraintes unaires
     * @return Set<Variable>
     */
    public Set<Variable> getUnaryVariable() {
        return unaryVariable;
    }
}
