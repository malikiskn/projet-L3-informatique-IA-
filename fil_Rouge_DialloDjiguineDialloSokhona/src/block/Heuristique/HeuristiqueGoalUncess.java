package block.Heuristique;

import block.BlockWord;
import planning.Goal;
import planning.Heuristic;
import representation.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  cette classe permet de calculer l'heuristique de l'objectif en fonction des buts du nombre de block non placé
 *  en tenant compte de l'obligation de le bouger ou pas pour atteindre l'objectif
 *  le principe es : un block placé au dessus d'un bloc mal placé est mal placé peut importe son etat
 */
public class HeuristiqueGoalUncess implements Heuristic {
    private final BlockWord model;
    private Goal goal;

    /**
     * constructeur de la classe
     * @param goal objectif
     */
    public HeuristiqueGoalUncess(Goal goal, BlockWord model) {
        this.goal = goal;
        this.model = model;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        int estimation = 0;
        List<List<Integer>> stateTraduit = etatMapToEtatListe(state);
        //on parcour la liste et compte le nombre de block mal placé
        for (int i = 0; i < stateTraduit.size(); i++) {
            List<Integer> pile = stateTraduit.get(i);
            for (int j = 0; j < pile.size(); j++) {
                Variable on = this.model.getRegisters().get("on"+pile.get(j));
                if (goal.getInstanciation().get(on)!=null && !goal.getInstanciation().get(on).equals(state.get(on))) {
                    estimation += pile.size()-j ;
                    break;
                }
            }
        }
        return estimation;
    }


    /**
     * cette fonction transforme un etat sous forme de map qui associe a chaque variable son etat
     * en un etat sous forme de liste de pile contenant une liste de block
     * @param state
     * @return
     */
    public List<List<Integer>> etatMapToEtatListe(Map<Variable, Object> state) {
        List<List<Integer>> stateTraduit = new ArrayList<>();
        //on construit les piles vides
        for (Map.Entry<Variable, Object> entry : state.entrySet()) {
            Variable variable = entry.getKey();
            if (variable.getName().contains(model.FREE)) {
                stateTraduit.add(new ArrayList<>());
            }
        }
        //on parcour les piles
        int p = -stateTraduit.size();
        for (List<Integer> pile : stateTraduit){
            List<Variable> variables = getKeys(state, p).stream().toList();
            Variable variableUnder = null;
            if (!variables.isEmpty()){
                variableUnder = variables.get(0);
            }
            // on ajoute toute les block de la pile dans la liste
            while (variableUnder!= null){
                int domaine = Integer.parseInt(variableUnder.getName().split("")[2]);
                pile.add(domaine);
                variables = getKeys(state, domaine).stream().toList();
                if (!variables.isEmpty()){
                    variableUnder = variables.get(0);
                }else {
                    variableUnder = null;
                }
            }
            p++;
        }
        return stateTraduit;
    }

    /**
     * cette fonction permet de recuperer les variables qui ont pour valeur la valeur value
     * @param map la map dans laquelle on cherche
     * @param value la valeur que l'on cherche
     * @return la liste des variables qui ont pour valeur value
     */
    public static <K, V> Set<K> getKeys(Map<K, V> map, V value)
    {
        return map.keySet().stream()
                .filter(key -> value.equals(map.get(key)))
                .collect(Collectors.toSet());
    }

    public String toString(){
        return "HeuristiqueGoalUncess";
    }
}
