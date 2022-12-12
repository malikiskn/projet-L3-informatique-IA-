package test;

import block.BlockRegularWord;
import block.BlockWord;
import representation.Constraint;
import representation.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Cette classe permet de tester la regularité d'un monde de blocs
 */
public class BWRegularTest {
    public static void main(String[] args) {
        System.out.println("Teste de regularité des mondes de blocs");
        int nbBlock = 4;
        int nbPile = 2;
        BlockRegularWord model = new BlockRegularWord(nbBlock, nbPile);
        //creation de configuration
        Map<Variable, Object> configA = new HashMap<>();
        configA.put(model.getRegisters().get(model.ON + '0'), -1);
        configA.put(model.getRegisters().get(model.ON + '1'), 0);
        configA.put(model.getRegisters().get(model.ON + '2'), 1);
        configA.put(model.getRegisters().get(model.ON + '3'), -2);
        configA.put(model.getRegisters().get(model.FIXED + '0'), true);
        configA.put(model.getRegisters().get(model.FIXED + '1'), true);
        configA.put(model.getRegisters().get(model.FIXED + '2'), false);
        configA.put(model.getRegisters().get(model.FIXED + '3'), false);
        configA.put(model.getRegisters().get(model.FREE + "-1"), false);
        configA.put(model.getRegisters().get(model.FREE + "-2"), false);
        System.out.println("configA: " + configA);
        //creation de configuration B
        Map<Variable, Object> configB = new HashMap<>();
        configB.put(model.getRegisters().get(model.ON + '0'), -1);
        configB.put(model.getRegisters().get(model.ON + '2'), 0);
        configB.put(model.getRegisters().get(model.ON + '1'), 2);
        configB.put(model.getRegisters().get(model.ON + '3'), -2);
        configB.put(model.getRegisters().get(model.FIXED + '0'), true);
        configB.put(model.getRegisters().get(model.FIXED + '1'), true);
        configB.put(model.getRegisters().get(model.FIXED + '2'), true);
        configB.put(model.getRegisters().get(model.FIXED + '3'), false);
        configB.put(model.getRegisters().get(model.FREE + "-1"), false);
        configB.put(model.getRegisters().get(model.FREE + "-2"), false);
        System.out.println("\nconfigB: " + configB);
        //creation de configuration C
        Map<Variable, Object> configC = new HashMap<>();
        configC.put(model.getRegisters().get(model.ON + '0'), 1);
        configC.put(model.getRegisters().get(model.ON + '1'), 2);
        configC.put(model.getRegisters().get(model.ON + '2'), 3);
        configC.put(model.getRegisters().get(model.ON + '3'), -2);
        configC.put(model.getRegisters().get(model.FIXED + '0'), false);
        configC.put(model.getRegisters().get(model.FIXED + '1'), true);
        configC.put(model.getRegisters().get(model.FIXED + '2'), true);
        configC.put(model.getRegisters().get(model.FIXED + '3'), true);
        configC.put(model.getRegisters().get(model.FREE + "-1"), true);
        configC.put(model.getRegisters().get(model.FREE + "-2"), false);
        System.out.println("\nconfigC: " + configC);
        //test de regularité
        // creation d'un map de configurations pour tester la regularité
        // on utilise un map pour avoir les nom des configurations dans le test
        Map<Map<Variable, Object>,String> configs = new HashMap<>();
        configs.put(configA,"configA");
        configs.put(configB,"configB");
        configs.put(configC,"configC");
        System.out.println("\n\n");
        for(Map <Variable,Object> config : configs.keySet()){
            Boolean regular = true;
            for (Constraint constraint : model.getImplicationConstraints()) {
                if (!constraint.isSatisfiedBy(config)) {
                    regular = false;
                    System.out.println("La configuration " + configs.get(config) + " n'est pas régulière");
                    System.out.println("La contrainte " + constraint + " n'est pas satisfaite");
                    break;
                }
            }
            if (regular) {
                System.out.println("config: " + configs.get(config) + " est regular");
            } else {
                System.out.println("config: " + configs.get(config) + " n'est pas regular");
            }
        }
    }
}
