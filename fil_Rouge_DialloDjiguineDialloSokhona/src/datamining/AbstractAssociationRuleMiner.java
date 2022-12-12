package datamining;

import java.util.HashSet;
import java.util.Set;

import representation.BooleanVariable;

/**
 * Classe abstraite représente des extracteurs de regles.
 */
public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    protected  BooleanDatabase database;

    /**
     * Construit une instance
     * @param database represente la base de données
     */
    public AbstractAssociationRuleMiner( BooleanDatabase database) {
        this.database = database;
    }

    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }
    /**
     * Cette méthode permet de retouner la frequence d'un ensemble d'items selon que ce dernier est stocké
     * ou non dans l'ensemble des itemsets.
     * @param items represente un ensemble d'items
     * @param itemsets reprsente un ensemble d'itemsets
     * @return la frequence de cet ensemble d'items
     */
    public static  float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for (Itemset transactions: itemsets) {
            if (items.equals(transactions.getItems())) {
                // on prend que si c'est exactement égal
                return transactions.getFrequency();
            }
        }
        throw new IllegalArgumentException("l'items n'existe pas dans la liste des itemsets");
    }

    /**
     * Cette méthode permet de déterminer la confiance de la règle d’association de prémisse et conclusion données,
     * les fréquences d’itemsets nécessaires aux calculs étant données dans l’ensemble en argument.
     * @param premise représente les premisses
     * @param conclusion représente la conclusion
     * @param itemsets reprsente un ensemble d'itemsets
     * @return la valeur de la confiance 
     */
    public static  float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        float premiseFre = 0, ruleFr = 0;
        for (Itemset items : itemsets) {
            Set<BooleanVariable> setPermise = new HashSet<>(premise);
            setPermise.addAll(conclusion);
            if (items.getItems().equals(premise)) {
                premiseFre = items.getFrequency();
            }
            if (items.getItems().equals(setPermise)) {
                ruleFr = items.getFrequency();
            }
        }
        return ruleFr / premiseFre;
    }
}