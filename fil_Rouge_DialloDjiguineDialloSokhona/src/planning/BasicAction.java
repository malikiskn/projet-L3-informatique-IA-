package planning;

import representation.Variable;
import java.util.HashMap;
import java.util.Map;

public class BasicAction implements  Action{
    private Map<Variable, Object> precondition,effet;
    private int cout;
    public static int compte=0;


    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effet, int cout) {
        this.precondition = precondition;
        this.effet = effet;
        this.cout = cout;
    }

    @Override
    public boolean isApplicable(Map<Variable, Object> map) {
        for(Map.Entry m: precondition.entrySet()){
            compte ++;
            if (map.get(m.getKey()) == null || (!map.get(m.getKey()).equals(m.getValue()))){
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> etat) {
        Map<Variable, Object> newEtat = new HashMap<>(etat);
        for(Map.Entry m: effet.entrySet()){
            newEtat.put((Variable) m.getKey(),m.getValue());
        }
        return newEtat;
    }

    @Override
    public int getCost() {
        return cout;
    }

    @Override
    public String toString() {
        return "BasicAction{" +
                "precondition=  " + precondition +
                "   ||  effet=  " + effet +
                "   ||  cout=   " + cout +
                "}-----------\n";
    }
}
