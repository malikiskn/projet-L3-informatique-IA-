package block;

import planning.Action;
import planning.BasicAction;
import representation.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * cette classe permet de creer un optimiseur de plan
 */
public class Optimiseur{
    private BlockActionablWord model;
    public int compt = 0;

    /**
     * constructor
     * @param model le modele de block world a optimiser
     */
    public Optimiseur(BlockActionablWord model) {
        this.model = model;
    }

    /**
     * cette methode permet d'optimiser le modele de block world
     * elle fais une reconstruction des actions suivant l'etat (donc sans les actions impossibles)
     * @return les actions possibles pour l'etat
     */
    public Set<Action> getOptimalAction(Map<Variable, Object> etat) {
        Set<Action> action = new HashSet<>();
        Set<Variable> freeList= new HashSet<>();
        // on captur les piles libres
        for (Variable freePile: model.getFreePiles()){
            compt++;
            if ((Boolean)etat.get(freePile)){
                freeList.add(freePile);
            }
        }
        //on capture les blocks  libres
        Set<Variable> notFixedList= new HashSet<>();
        for (Variable fixedBloc: model.getFixeBlocks()){
            compt++;
            if (!(Boolean)etat.get(fixedBloc)){
                notFixedList.add(fixedBloc);
            }
        };
        // pour chaque block libre on construit les actions possibles vers les piles libres et les blocks non fixe
        for (Variable fixed: notFixedList){
            Variable onBlock = model.getRegisters().get(model.ON+model.getIndice(fixed));
            for (Variable notFixed: notFixedList) {
                compt++;
                Map<Variable,Object> precondiotons = new HashMap<>();
                Map<Variable,Object> effects = new HashMap<>();
                if (!notFixed.equals(fixed)){
                    //on definie les preconditions
                    precondiotons.put(onBlock, etat.get(onBlock));
                    precondiotons.put(notFixed, false);
                    precondiotons.put(fixed, false);
                    //on definie les effets
                    effects.put(onBlock, model.getIndice(notFixed));
                    if ((int)etat.get(onBlock)>=0) {
                        effects.put(model.getRegisters().get(model.FIXED + etat.get(onBlock)), false);
                    }else {
                        effects.put(model.getRegisters().get(model.FREE + etat.get(onBlock)), true);
                    }
                    effects.put(notFixed,true);
                    //on ajoute l'action a l'ensemble des actions
                    action.add(new BasicAction(precondiotons,effects,1));
                }
            }
        }
        for (Variable fixed: notFixedList){
            Variable onBlock = model.getRegisters().get(model.ON+model.getIndice(fixed));
            for (Variable frePile: freeList) {
                compt++;
                Map<Variable,Object> precondiotons = new HashMap<>();
                Map<Variable,Object> effects = new HashMap<>();
                //on definie les preconditions
                precondiotons.put(onBlock, etat.get(onBlock));
                precondiotons.put(frePile, true);
                precondiotons.put(fixed, false);
                //on definie les effets
                effects.put(onBlock, model.getIndice(frePile));
                if ((int)etat.get(onBlock)>=0) {
                    effects.put(model.getRegisters().get(model.FIXED + etat.get(onBlock)), false);
                }else {
                    effects.put(model.getRegisters().get(model.FREE + etat.get(onBlock)), true);
                }
                effects.put(frePile,false);
                action.add(new BasicAction(precondiotons,effects,0));
            }
        }
        return action;
    }
}
