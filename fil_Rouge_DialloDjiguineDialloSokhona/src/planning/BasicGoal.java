package planning;

import representation.Variable;

import java.util.Map;

/**
 * cette classe permet de definir un but
 */
public class BasicGoal implements Goal {
    private Map<Variable,Object> instanciation;
    private String name;

    /**
     * constructeur de la classe
     * @param instanciation est l'instanciation partielle
     */
    public BasicGoal(Map<Variable, Object> instanciation) {
        this.instanciation = instanciation;
        this.name = "BasicGoal";
    }


    public BasicGoal(Map<Variable, Object> instanciation, String name) {
        this.instanciation = instanciation;
        this.name = name;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> etat) {
        for(Map.Entry m: instanciation.entrySet()){
            if (etat.get(m.getKey())!=(m.getValue())){
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Variable, Object> getInstanciation() {
        return instanciation;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "BasicGoal{" +
                "instanciation=" + instanciation +
                '}';
    }
}
