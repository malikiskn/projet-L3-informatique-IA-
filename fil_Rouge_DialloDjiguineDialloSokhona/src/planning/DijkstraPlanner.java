package planning;

import block.Optimiseur;
import representation.Variable;

import java.util.*;

import static java.util.Collections.reverse;

/**
 * Cette classe permet de planifier un plan optimal en utilisant l'algorithme de Dijkstra
 */
public class DijkstraPlanner implements Planner{
    private Map<Variable, Object> etatInitial;
    private Set<Action> actions;
    private Goal goals;
    private int sonde;
    private  Optimiseur optimiseur;

    /**
     * Constructeur de la classe DijkstraPlanner
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     */
    public DijkstraPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals) {
        this.etatInitial = etatInitial;
        this.actions = actions;
        this.goals = goals;
        sonde=0;
    }

    /**
     * Constructeur de la classe DijkstraPlanner avec optimiseur
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     * @param optimiseur est l'optimiseur
     */
    public DijkstraPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals, Optimiseur optimiseur) {
        this.etatInitial = etatInitial;
        this.actions = actions;
        this.goals = goals;
        sonde=0;
        this.optimiseur = optimiseur;
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
        return "Dijkstra Planner";
    }

    @Override

    public List<Action> plan() {
        return djikstra(this);
    }

    /**
     * Cette methode permet de planifier un plan optimal en utilisant l'algorithme de Dijkstra
     * @return le plan optimal
     */
    private List<Action> djikstra(DijkstraPlanner problem) {
        Map<Map<Variable, Object>, Action> plan =new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>,Map<Variable, Object>> father = new HashMap<>();
        PriorityQueue <Map<Variable, Object>> goals = new PriorityQueue<>(new StateCompar(distance));
        father.put(problem.getInitialState(),null);
        distance.put(problem.getInitialState(), 0.0F);
        PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(new StateCompar(distance));
        open.add(problem.getInitialState());
         while (!open.isEmpty()){
             this.sonde +=1;
             Map<Variable, Object> instance = open.poll();
            if (problem.getGoal().isSatisfiedBy(instance)){
                goals.add(instance);
            }
            Set <Action>  actions = null;
            if (this.optimiseur != null){
             actions = this.optimiseur.getOptimalAction(instance);
            }else {
             actions = problem.getActions();
            }
            for (Action action: actions) {
                this.sonde++;
                if (action.isApplicable(instance)){
                    Map<Variable, Object>  next = action.successor(instance);
                    if (distance.get(next)==null){
                        distance.put(next,  Float.MAX_VALUE);
                    }
                    if (distance.get(next)>distance.get(instance)+action.getCost()){
                        distance.put(next,distance.get(instance)+action.getCost());
                        father.put(next,instance);
                        plan.put(next,action);
                        open.add(next);
                    }
                }
            }
            }
            if (goals.isEmpty()){
                return null;
            }
            else {
                return get_dijkstra_plan(father , plan, goals, distance);
            }
    }

    /**
     * Cette methode reconstruit le plan
     * @param father c'est une liste contenant des couples (etat, parent)
     * @param plan c'est une liste contenant des couples d'etat et l'action qui nous a mene a cet etat
     * @param goals Etats finaux a atteindre
     * @param distance est une liste des couples (etat, cout)
     * @return
     */
    private List<Action> get_dijkstra_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, PriorityQueue <Map<Variable, Object>> goals, Map<Map<Variable, Object>, Float> distance) {
        List<Action> DIJ_plan=new ArrayList<>();
        Map<Variable, Object> goal= goals.poll();
        while ( goal != null){
            DIJ_plan.add(plan.get(goal));
            goal = father.get(goal);
        }
        DIJ_plan.remove(null);
        reverse(DIJ_plan);
        return DIJ_plan;
    }

    public int getSonde() {
        return this.sonde;
    }


}