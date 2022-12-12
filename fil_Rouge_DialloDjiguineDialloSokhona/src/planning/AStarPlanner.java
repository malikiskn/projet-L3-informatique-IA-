package planning;

import block.Optimiseur;
import representation.Variable;

import java.util.*;

/**
 * Cette classe permet de planifier un plan optimal en utilisant l'algorithme de Dijkstra
 */
public class AStarPlanner implements Planner{
    private Map<Variable, Object> etatInitial;
    private Set<Action> actions;
    private Goal goals;
    private Heuristic heuristic;
    private  int sonde;
    private  Optimiseur optimiseur;
    private String name;


    /**
     * Constructeur de la classe AStarPlanner
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     * @param heuristic est l'heuristique
     */
    public AStarPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals, Heuristic heuristic) {
        this.etatInitial = etatInitial;
        this.actions = actions;
        this.goals = goals;
        this.heuristic = heuristic;
        this.sonde = 0;
        this.name = "AStarPlanner";
        this.optimiseur = null;
    }

    /**
     * Constructeur de la classe AStarPlanner
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     * @param heuristic est l'heuristique
     * @param optimiseur est l'optimiseur
     */
    public AStarPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals, Heuristic heuristic, Optimiseur optimiseur) {
        this.etatInitial = etatInitial;
        this.actions = actions;
        this.goals = goals;
        this.heuristic = heuristic;
        this.sonde = 0;
        this.optimiseur = optimiseur;
        this.name = "AStarPlanner";
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.etatInitial;
    }

    @Override
    public Set<Action> getActions() {
        return  this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.goals;
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public List<Action> plan() {
        return astar(this);
    }

    /**
     * Cette methode permet de planifier un plan optimal en utilisant l'algorithme de A*
     * @param problem est le probleme a resoudre
     * @return une liste d'actions
     */
    private List<Action> astar(AStarPlanner problem) {
        Map<Map<Variable, Object>, Action> plan =new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>,Float> value = new HashMap<>();
        father.put(problem.getInitialState(),null);
        distance.put(problem.getInitialState(), 0F);
        value.put(problem.getInitialState(),heuristic.estimate(problem.getInitialState()));
        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(new StateCompar(value));
        open.add(problem.getInitialState());
        while (!open.isEmpty()){
            this.sonde +=1;
            Map<Variable, Object> instance = open.poll();
            if (problem.getGoal().isSatisfiedBy(instance)){
                return BFSPlanner.get_bfs_plan(father,plan,instance);
            }else {
                open.remove(instance);
                Set <Action>  actions = null;
                if (this.optimiseur != null){
                    actions = this.optimiseur.getOptimalAction(instance);
                }else {
                    actions = problem.getActions();
                }
                for (Action action: actions) {
                    if (action.isApplicable(instance)){
                        Map<Variable, Object> next = action.successor(instance);
                        if (distance.get(next)==null) {
                            distance.put(next, Float.MAX_VALUE);
                        }
                        if (distance.get(next)>distance.get(instance)+action.getCost()) {
                            distance.put(next, distance.get(instance) + action.getCost());
                            value.put(next,distance.get(next)+this.heuristic.estimate(next));
                            father.put(next, instance);
                            plan.put(next,action);
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }
    @Override
    public int getSonde() {
        return sonde;
    }
    //setter pour le nom;
    public void setName(String name) {
        this.name = name;
    }
}