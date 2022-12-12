package block.Heuristique;

import planning.Goal;
import planning.Heuristic;
import representation.Variable;

import java.util.Map;

/**
 *  cette classe permet de calculer l'heuristique de l'objectif en fonction des buts partielle atteintes
 */
public class HeuristiqueGoalSucces implements Heuristic {
    Goal goal;

    /**
     * constructeur de la classe
     * @param goal objectif
     */
    public HeuristiqueGoalSucces(Goal goal) {
        this.goal = goal;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        int goalSucces = 0;
        // on compte le nombre de but atteint
        for (Variable variable: state.keySet()){
            if (state.get(variable).equals(goal.getInstanciation().get(variable))){
                goalSucces++;
            }
        }
        // on retourne le nombre de but atteint multiplié par un certain coefficient
        return state.size()-(goalSucces);
    }

    // on retourne le nom de l'heuristique
    public String toString(){
        return "HeuristiqueGoalSucces";
    }
}
