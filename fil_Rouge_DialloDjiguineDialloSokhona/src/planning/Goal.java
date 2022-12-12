package planning;

import java.util.Map;

import representation.Variable;

public interface Goal {
    
    /**
     * Cette méthode teste si le but est satisfait ou non par un etat
     * @param state est une instanciation partielle des variable
     * @return true ou false selon que la contrainte est satisfaite ou non
     */
	boolean isSatisfiedBy(Map<Variable, Object> state);

    /**
     * Cette methode renvoie l'affactation du but
     * @return une affectation
     */
    public Map<Variable, Object> getInstanciation();

    /**
     * Cette methode renvoie le nom du but
     * @return le nom du but
     */
        public String getName();
}
