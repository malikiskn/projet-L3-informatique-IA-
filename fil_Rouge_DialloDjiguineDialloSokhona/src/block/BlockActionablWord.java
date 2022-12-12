package block;

import planning.Action;
import planning.BasicAction;
import representation.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * cette classe permet de rendre le probleme de block world actionnable
 * elle permet de construire l'ensemble des actions possibles
 * et de les stocker dans un ensemble
 */
public class BlockActionablWord extends BlockWord{
    private Set<Action> action;
    /**
     * constructeur de la classe
     * @param nbBlock nombre de block
     * @param nbPile nombre de pile
     */
    public BlockActionablWord(int nbBlock, int nbPile) {
        super(nbBlock, nbPile);
        this.action = buildActions();

    }

    /**
     * cette methode permet de construire l'ensemble des actions possibles
     * @return l'ensemble des actions possibles
     */
    private Set<Action> buildActions() {
        Set<Action> actions = new HashSet<>();
        //on parcourt l'ensemble des variables definissant la disposition des block
        // block a deplacer
        for (Variable on : getOnBlocks()) {
            //pour chaque valuation dans son domaine(on(i) = j) i est le block et j un autre block
            //block initial position
            for (Object value: on.getDomain()){
                /** creation d'action deplaçant  un block du somet d'un block vers  ⇨ un autre blok */
                // on commence par les positions de block libre (indice plus grand que le 0)
                if ((int)value>=0) {
                    //pour tout blocks libre
                    //block destination
                    for (Variable fixed : getFixeBlocks()) {
                        //on exclu le block a deplacer
                        if (getIndice(fixed)!= getIndice(on) && (int)value != getIndice(fixed)){
                            Map<Variable,Object> precondiotons = new HashMap<>();
                            Map<Variable,Object> effects = new HashMap<>();
                            //on definie les preconditions
                            precondiotons.put(on, value);
                            precondiotons.put(this.getRegisters().get(FIXED + getIndice(on)), false);
                            precondiotons.put(fixed, false);
                            //on definie les effets
                            effects.put(on, getIndice(fixed));
                            effects.put(fixed, true);
                            effects.put(getRegisters().get(FIXED + value), false);
                            //on construit et  ajoute l'action a l'ensemble des actions avec un coup de 1
                            actions.add(new BasicAction(precondiotons, effects, 1));
                        }
                    }
                    /** creation d'action deplaçant  un block du somet d'un block vers  ⇨ une pile libre */
                    //pour tout pile libre
                    //pile destination
                    for (Variable free : getFreePiles()) {
                        Map<Variable,Object> precondiotons = new HashMap<>();
                        Map<Variable,Object> effects = new HashMap<>();
                        //on definie les preconditions
                        precondiotons.put(on, value);
                        precondiotons.put(this.getRegisters().get(FIXED + getIndice(on)), false);
                        precondiotons.put(free, true);
                        //on definie les effets
                        effects.put(on, getIndice(free));
                        effects.put(this.getRegisters().get(FIXED + value), false);
                        effects.put(free, false);
                        //on construit et  ajoute l'action a l'ensemble des actions avec un coup de 1
                        actions.add(new BasicAction(precondiotons, effects, 2));
                    }
                }else {
                    /**  creation d'action deplaçant  un block depuis la base d'une pile vers  ⇨ un autre blok libre*/
                    //pour tout blocks libre
                    //block destination
                    for (Variable fixed : getFixeBlocks()) {
                        if (getIndice(fixed)!= getIndice(on)){
                            Map<Variable,Object> precondiotons = new HashMap<>();
                            Map<Variable,Object> effects = new HashMap<>();
                            //on definie les preconditions
                            precondiotons.put(on,value);
                            precondiotons.put(this.getRegisters().get(FIXED+getIndice(on)), false);
                            precondiotons.put(fixed,false);
                            //build effect
                            effects.put(on,getIndice(fixed));
                            effects.put(fixed,true);
                            effects.put(getRegisters().get(FREE+value),true);
                            //on construit et  ajoute l'action a l'ensemble des actions avec un coup de 1
                            actions.add(new BasicAction(precondiotons,effects,1));
                        }
                    }
                    /** creation d'action deplaçant  un block depuis la base d'une pile vers  ⇨ une pile libre */
                    //pour tout pile libre
                    //pile destination
                    for (Variable free : getFreePiles()) {
                        Map<Variable,Object> precondiotons = new HashMap<>();
                        Map<Variable,Object> effects = new HashMap<>();
                        // on exclue la pile de depart
                        if (getIndice(free)!= (int) value){
                            //on definie les preconditions
                            precondiotons.put(on, value);
                            precondiotons.put(this.getRegisters().get(FIXED + getIndice(on)), false);
                            precondiotons.put(free, true);
                            //on definie les effets
                            effects.put(on, getIndice(free));
                            effects.put(this.getRegisters().get(FREE + value), true);
                            effects.put(free, false);
                            //on construit et  ajoute l'action a l'ensemble des actions avec un coup de 1
                            actions.add(new BasicAction(precondiotons, effects, 3));
                        }
                    }
                }
            }
        }
        return actions;
    }

    /**
     * cette methode permet de retourner l'ensemble des actions possibles
     * @return l'ensemble des actions possibles
     */
    public Set<Action> getAction() {
        return action;
    }
}
