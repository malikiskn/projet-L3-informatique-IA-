package test;

import block.BlockActionablWord;
import block.Heuristique.HeuristiqueGoalSucces;
import block.Heuristique.HeuristiqueGoalUncess;
import block.Optimiseur;
import planning.*;
import representation.Variable;
import vue.VuePlan;

import java.io.IOException;
import java.util.*;

/**
 * cette classe permet de tester le solveur de CSP
 */
public class PlannersTests {
    /**
     * cette fonction transforme un etat sous forme de liste de pile contenant une liste de block en un etat sous forme de map
     * qui associe a chaque variable son etat renvoie une instanciation partielle et le monde correspondant
     *  @param state etat sous forme de liste de pile contenant une liste de block
     * @return une instanciation partielle et le monde correspondant
     */
    public static Map <Map<Variable, Object>,BlockActionablWord> etatListToMap(List<List<Integer>> state) {
        int nbPile = state.size();
        int nbBlock = 0;
        for (List<Integer> pile : state) {
            nbBlock += pile.size();
        }
        System.out.println("nbBlock: " + nbBlock);
        System.out.println("nbPile: " + nbPile);
        BlockActionablWord model = new BlockActionablWord(nbBlock, nbPile);
        Map<Variable, Object> stateTraduit = new HashMap<>();
        int p = -state.size();
        for (List<Integer> list : state) {
            if (list.isEmpty()){
                stateTraduit.put(model.getRegisters().get(model.FREE + p), true);
            }
            else {
                stateTraduit.put(model.getRegisters().get(model.FREE + p), false);
                if (list.size() >1){
                    stateTraduit.put(model.getRegisters().get(model.FIXED+list.get(0)), true);
                }
                stateTraduit.put(model.getRegisters().get(model.ON+list.get(0)),p);
                for (int i = 1; i <= list.size()-2; i++) {
                    stateTraduit.put(model.getRegisters().get(model.FIXED+list.get(i)), true);
                    stateTraduit.put(model.getRegisters().get(model.ON+list.get(i)),list.get(i-1));
                }
                if (list.size()>1){
                    stateTraduit.put(model.getRegisters().get(model.ON+list.get(list.size()-1)),list.get(list.size()-2));
                }
                stateTraduit.put(model.getRegisters().get(model.FIXED+list.get(list.size()-1)), false);
            }
            p++;
        }
        return Map.of(stateTraduit,model);
    }

    /**
     * cette methode permet de tester le solveur de CSP
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) throws IOException {
        //creation de l'etat initial
        List<List<Integer>> state = List.of(List.of(0, 1,2,3,4,5), List.of(),List.of());
        Map<Map<Variable, Object>, BlockActionablWord> build = etatListToMap(state);
        Map<Variable, Object> stateTraduit = build.keySet().iterator().next();
        BlockActionablWord BWmodel = build.get(stateTraduit);
        System.out.println("state: " + state);
        System.out.println("state: " + stateTraduit);
        //construction des goals
        //creation goal1
        HashMap instationGoal = new HashMap<>();
        instationGoal.put(BWmodel.getRegisters().get("on3"), -1);
        instationGoal.put(BWmodel.getRegisters().get("on1"), 0);
        instationGoal.put(BWmodel.getRegisters().get("on2"), 4);
        Goal goal1 = new BasicGoal(instationGoal," goal1");
        Heuristic heuristic1 = new HeuristiqueGoalUncess(goal1,BWmodel);
        heuristic1.estimate(stateTraduit);
        //creation goal2
        HashMap instationGol2 = new HashMap<>();
        instationGol2.put(BWmodel.getRegisters().get("on4"), 0);
        instationGol2.put(BWmodel.getRegisters().get("on3"), -3);
        instationGol2.put(BWmodel.getRegisters().get("on2"), 3);
        instationGol2.put(BWmodel.getRegisters().get("on1"), 5);
        instationGol2.put(BWmodel.getRegisters().get("on0"), 1);
        instationGol2.put(BWmodel.getRegisters().get("on5"), -2);
        Goal goal2 = new BasicGoal(instationGol2," goal2");
        //creation des planificateurs
        BFSPlanner bfsPlanner = new BFSPlanner(stateTraduit, BWmodel.getAction(), goal1,new Optimiseur(BWmodel));
        BFSPlanner bfsPlanner2 = new BFSPlanner(stateTraduit, BWmodel.getAction(), goal2,new Optimiseur(BWmodel));
        DFSPlanner dfsPlanner = new DFSPlanner(stateTraduit, BWmodel.getAction(), goal1);
        DFSPlanner dfsPlanner2 = new DFSPlanner(stateTraduit, BWmodel.getAction(), new BasicGoal(instationGoal,"goal1 avec optimiseur"),new Optimiseur(BWmodel));
        DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(stateTraduit, BWmodel.getAction(), goal1,new Optimiseur(BWmodel));
        DijkstraPlanner dijkstraPlanner2 = new DijkstraPlanner(stateTraduit, BWmodel.getAction(), goal2,new Optimiseur(BWmodel));

        AStarPlanner astarPlanner = new AStarPlanner(stateTraduit, BWmodel.getAction(), goal1,new HeuristiqueGoalSucces(goal1));
        AStarPlanner astarPlanner2 = new AStarPlanner(stateTraduit, BWmodel.getAction(), goal2,new HeuristiqueGoalSucces(goal1));
        AStarPlanner astarPlanner3 = new AStarPlanner(stateTraduit, BWmodel.getAction(), goal2,new HeuristiqueGoalUncess(goal1,BWmodel), new Optimiseur(BWmodel));
        astarPlanner.setName(astarPlanner.getName()+" Heuristic heuristicGoalSucces");
        astarPlanner2.setName(astarPlanner2.getName()+" Heuristique heuristicGoalSucces");
        astarPlanner3.setName(astarPlanner3.getName()+" Heuristique heuristicGoalUnsucces");


        // ajout des planificateurs dans la liste des planificateurs
        List<Planner> planerList = new ArrayList<>();
        planerList.add(astarPlanner);
        planerList.add(bfsPlanner);
        planerList.add(dijkstraPlanner);
        planerList.add(astarPlanner2);
        planerList.add(bfsPlanner2);
        planerList.add(dijkstraPlanner2);
        planerList.add(astarPlanner3);
        //veuillez decomentez les deux planificateurs suivant pour les tester df qui peut parfois lever une exception
        planerList.add(dfsPlanner);
        planerList.add(dfsPlanner2);
        Set<VuePlan> vuePlan= new HashSet<>();
        for (Planner planner: planerList){
            long start = System.currentTimeMillis();
            List<Action> resultat = planner.plan();
            long end = System.currentTimeMillis();
            float time =(end-start)/60f;
            int poidSolution = 0;
            if (resultat != null) {
                for (Action action : resultat) {
                    poidSolution += action.getCost();
                }
            }
            // affichage des statistiques
            System.out.println("\n["+planner.getName()+planner.getGoal().getName()+"] noud visité =          " + planner.getSonde());
            System.out.println("["+planner.getName()+planner.getGoal().getName()+"] time of execution=       " + time);
            System.out.println("["+planner.getName()+planner.getGoal().getName()+"]chemin size =            " + (resultat != null ? resultat.size() : 0));
            System.out.println("["+planner.getName()+planner.getGoal().getName()+"] poid de la solution=     " + poidSolution);
            //construction de la vue
            if (planner.getName().equals("A*")){
                vuePlan.add( new VuePlan(planner.getName()+planner.getGoal().getName(),BWmodel.getNbBlock(), BWmodel.getNbPile(), stateTraduit, resultat, BWmodel.getRegisters(),time,planner.getSonde()));
            }else{
                vuePlan.add( new VuePlan(planner.getName()+planner.getGoal().getName(),BWmodel.getNbBlock(), BWmodel.getNbPile(), stateTraduit, resultat, BWmodel.getRegisters(),time,planner.getSonde()));
            }
        }
        //affichage des vues des planificateurs
        //ces vues s'exécute en parallèle
        for (VuePlan vue: vuePlan){
            vue.start ();
        }
    }
}
