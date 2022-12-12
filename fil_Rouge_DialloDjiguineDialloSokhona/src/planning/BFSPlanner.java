package planning;

import block.Optimiseur;
import representation.Variable;

import java.util.*;

import static java.util.Collections.reverse;

/**
 * Cette classe permet de planifier un plan optimal en utilisant l'algorithme de breadth-first search
 */
public class BFSPlanner implements Planner{
    private Map<Variable, Object> etatInitial;
    private Set<Action> actions;
    private Goal goals;
    private int sonde;
    private Optimiseur optimiseur;

    /**
     * Constructeur de la classe BFSPlanner
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     */
    public BFSPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals) {
        this.etatInitial = etatInitial;
        this.actions = actions;
        this.goals = goals;
        this.sonde=0;
    }

    /**
     * Constructeur de la classe BFSPlanner avec optimiseur
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     * @param optimiseur est l'optimiseur
     */
    public BFSPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals, Optimiseur optimiseur) {
        this.etatInitial = etatInitial;
        this.actions = actions;
        this.goals = goals;
        this.sonde=0;
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
        return "BFS Planner";
    }

    @Override
    public List<Action> plan() {
        return  bfs(this);

    }

    /**
     * Cette methode permet de planifier un plan optimal en utilisant l'algorithme de breadth-first search
     * @param problem est le probleme a resoudre
     * @return le plan optimal
     */
    private List<Action> bfs(BFSPlanner problem) {
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map< Map<Variable, Object>,Action> plan = new HashMap<>();
        List<Map<Variable, Object>> closed = new ArrayList<>();
        Queue<Map<Variable, Object>> open=new LinkedList<>();
        open.add(problem.etatInitial);
        father.put(problem.etatInitial,null);
        if (problem.getGoal().isSatisfiedBy(problem.etatInitial)) {
            return new ArrayList<>();
        }
        while (!open.isEmpty()){
            this.sonde += 1;
            Map<Variable, Object> instantiation= open.poll();
            closed.add(instantiation);
            int tour=0;
            Set <Action>  actions = null;
            if (this.optimiseur != null){
                actions = this.optimiseur.getOptimalAction(instantiation);
            }else {
                actions = problem.getActions();
            }
            for (Action action: actions) {
                tour ++;
                if (action.isApplicable(instantiation)){

                    Map<Variable, Object> next = action.successor(instantiation);
                    if ((!closed.contains(next)) && (!open.contains(next))){
                        father.put(next,instantiation);
                        plan.put(next,action);
                        if(problem.getGoal().isSatisfiedBy(next)){
                            return get_bfs_plan(father , plan, next);
                        }else {
                            open.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Cette methode reconstruit le plan
     * @param father c'est une liste contenant des couples (etat, parent)
     * @param plan c'est une liste contenant des couples d'etat et l'action qui nous a mene a cet etat
     * @param goal est l'etat final a atteindre
     * @return la liste d'action
     */
    public static List<Action> get_bfs_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal) {
        List<Action> bfs_plan =new ArrayList<>();
        while (goal != null){
            if (plan.get(goal)!=null  ){
                bfs_plan.add(plan.get(goal));
            }
            goal=father.get(goal);
        }
        reverse(bfs_plan);
        return bfs_plan;
    }
    @Override
    public int getSonde() {
        return sonde;
    }
}