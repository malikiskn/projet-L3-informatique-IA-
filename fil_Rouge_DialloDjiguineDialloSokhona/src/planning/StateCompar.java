package planning;

import representation.Variable;

import java.util.Comparator;
import java.util.Map;

/**
 * Cette classe permet de comparer deux etats
 */
public class StateCompar implements Comparator<Map<Variable, Object>> {
    private Map<Map<Variable, Object>, Float> distance;
    /**
     * Constructeur de la classe
     * @param distance est la distance
     */
    public StateCompar(Map<Map<Variable, Object>, Float> distance) {
        this.distance = distance;
    }

    @Override
    public int compare(Map<Variable, Object> o1, Map<Variable, Object> o2) {
        return Float.compare(distance.getOrDefault(o1,Float.MAX_VALUE), distance.getOrDefault(o2,Float.MAX_VALUE));
    }
}
