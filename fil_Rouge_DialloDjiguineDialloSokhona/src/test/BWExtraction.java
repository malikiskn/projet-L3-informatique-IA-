package test;

import block.BWBooleanDatabase;
import bwgeneratordemo.Demo;
import datamining.AssociationRule;
import datamining.BooleanDatabase;
import datamining.BruteForceAssociationRuleMiner;
import representation.BooleanVariable;
import java.util.List;
import java.util.Random;
import java.util.Set;
import bwgenerator.BWGenerator;

/**
 * cette classe permet de tester le generateur de base de donnees booleennes
 */
public class BWExtraction {
    /**
     * methode main de la classe
     * @param args arguments de la methode main
     */
    public static void main(String[] args) {
        System.out.println("Teste d'extration de monde des blocs");
        int nbBlock = 5;
        int nbPile = 5;
        BWBooleanDatabase model = new BWBooleanDatabase(nbBlock, nbPile);
        BooleanDatabase booleanDatabase = new BooleanDatabase(model.getAllVariables());
        Random random= new Random();
        BWGenerator bwGenerator = new BWGenerator(nbBlock,nbPile);
        int n = 10_000;
        for ( int i = 0; i < n ; i ++) {
            // Drawing a state at random
            List <List< Integer >> state = Demo.getState(random);
            Set <BooleanVariable> instance = model.extractTrueVariable(state);
            booleanDatabase. add ( instance );
        }
        //recherche des regles d'association
        BruteForceAssociationRuleMiner miner = new BruteForceAssociationRuleMiner(booleanDatabase);
        Set<AssociationRule> associationRules = miner.extract(2/3f,95/100f);
        System.out.println("\nAssociation rules found :"+associationRules);
    }
}
