package csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import representation.Variable;

/**
 * Cette classe définie une heuristics qui range aléatoirement des domaines suivant
 * un générateur aléatoire
 */
public class RandomValueHeuristic implements ValueHeuristic {
    private Random random;

    /**
     * Constructeur de la class RandomValueHeuristic
     * @param random générateur aléatoire
     */
    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domaine) {
        List<Object> listDomain = new ArrayList<Object>(domaine);
        Collections.shuffle(listDomain, random);
        return new ArrayList<>(listDomain);
    }

    /**
     * Cette methode renvoi le générateur aléatoire
     * @return un générateur aléatoire
     */
    public Random getRandom() {
        return random;
    }

}
