package planning;

import block.Optimiseur;
import representation.Variable;

import java.util.*;

/**
 * cette classe permet de construire un plan optimal en utilisant l'algorithme de deep first search
 */
public class DFSPlanner implements Planner{
    private  Optimiseur optimiseur;
    private Map<Variable, Object> etatInitial;
    private Set<Action> actions;
    private Goal goals;
    public int sonde;
    private String name;


    /**
     * Constructeur de la classe DFSPlanner
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     */
    public DFSPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals) {
        this.etatInitial = etatInitial;
        this.actions = actions;
        this.goals = goals;
        this.sonde =0;
        this.optimiseur = null;
    }
    /**
     * Constructeur de la classe DFSPlanner
     * @param etatInitial est l'etat initial
     * @param actions est la liste des actions
     * @param goals est le but
     * @param optimiseur est l'optimiseur
     */
    public DFSPlanner(Map<Variable, Object> etatInitial, Set<Action> actions, Goal goals, Optimiseur optimiseur) {
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
        return "DFS Planner";
    }

    @Override
    public List<Action> plan() {
        return dfs(this,this.getInitialState(),new Stack<>(),new ArrayList<>());
    }


    /**
     * Un algorithme de recherche en profondeur
     * @param isntationInitial est l'état initial
     * @param plan est un ensemble d’actions
     * @param closed les fermes
     * @return la liste d'action
     */
    private List<Action> dfs(Planner problem, Map<Variable, Object> isntationInitial, Stack<Action> plan, List<Map<Variable, Object>> closed){
        if (problem.getGoal().isSatisfiedBy(isntationInitial)){
            return plan;
        }else {
            Set <Action>  actions = null;
            if (this.optimiseur != null){
                actions = this.optimiseur.getOptimalAction(isntationInitial);
            }else {
                actions = problem.getActions();
            }
            for (Action action: actions) {
                this.sonde +=1;
                if (action.isApplicable(isntationInitial)){
                    Map<Variable,Object> next =action.successor(isntationInitial);
                    if (!closed.contains(next)){
                        plan.push(action);
                        closed.add(next);
                        List<Action> subplan =dfs(problem,next,plan,closed);
                        if (subplan!=null){
                            return subplan;
                        }else {
                            plan.pop();
                        }
                    }
                }
            }
            return null;
        }
    }

    @Override
    public int getSonde() {
        return sonde;
    }
}
