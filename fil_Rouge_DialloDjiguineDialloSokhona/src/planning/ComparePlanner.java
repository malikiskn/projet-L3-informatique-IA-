package planning;

import representation.Variable;

import java.util.*;
public class ComparePlanner {
    /**
     * cette classe permet de sumuler une serie de teste sur les parcour DFS BFS Djikstra
     * le principe et de genererer des serie de senariaux sur des probleme ayant une probabilité 1/3 d'avoir une solution
     * et en console on poura observer les serie de resultat avec les information ci dessous
     *
     * -----------------------------------------
     * [BFS] noud visité =            5
     * [BFS] time of execution=       172490
     * [BFS] chemin size =            2
     * [BFS] poid de la solution=       7
     *
     * [DFS] noud visité =          22
     * [DFS] time of execution=       13987
     * [DFS] chemin size =            5
     * [DFS] poid de la solution=     12
     *
     * [Dijkstra] noud visité =     80
     * [Dijkstra] time of execution=  1058521
     * [Dijkstra] chemin size =       3
     * [Dijkstra] poid de la solution=7
     * ------------------------------------------------
     */
    public static void compare (){
        Map<Variable, Object> etatInitial ;
        Set<Action> actions ;

        Random random = new Random();

        for (int tailleTest = 0; tailleTest <1000;tailleTest++ ) {
            etatInitial=new HashMap<>();
            actions = new HashSet<>();
            BasicGoal goals;


            int nbGoal= random.nextInt(3);
            final int maxi= 10;                                               //defini la taille de letat initial
            int partitionActuin=3;                                             //est un modulo definissant le nombre d'actions souhaité
            boolean solvable=true;
            if (random.nextInt(3)==1){
                solvable = false;                                            //definie la solvabilité du problem
            }
            /**
             * definition des domaines des variables
             */

            Variable[] variables = new Variable[maxi];
            Set<Object> domaine = new HashSet<>();
            for (int i = 0; i < 5; i++) {
                domaine.add(i);
            }
            /**
             * definition des variables
             * les variables ici auron les meme domaine
             */
            for (int nbVariable = 0; nbVariable < maxi; nbVariable++) {
                variables[nbVariable] = new Variable("x" + nbVariable, domaine);
            }
            //definition de l'etat initiale
            /**
             * on affecte au hasard des valutation aux variables
             * tout en tenant compte du domaine des variables
             */
            for (int i = 0; i < maxi; i++) {
                int random_computer_card = random.nextInt(domaine.size());
                etatInitial.put(variables[i], random_computer_card);
            }
            /**
             * definitoin de goal qui dependra de la valeur de nbGol
             */
            Map<Variable,Object> gol = new HashMap<>();
            for (int i = 0; i < nbGoal; i++) {
                int index = random.nextInt(maxi);
                System.out.println(index + "----------------");
                gol.put(variables[index],random.nextInt(maxi));

            }
            goals=new BasicGoal(gol);

            /**
             * definitioin des precondition et des effets,
             * on fera en sorte que les precondition des activité soit en phase avec l'etat initiale
             * ou a un sous etat de cellci pour avoir un (isApplicable -> True)
             * l'effet dependant de la valeur de solvable (true on affectera l'effet de façon à satisfaire la but)
             *
             */
            Map<Variable, Object> precondition = new HashMap<>();
            Map<Variable, Object> effet = new HashMap<>();
            Action[] tablAction = new Action[maxi];
            for (int bnprec = 0; bnprec < maxi; bnprec++) {
                precondition.put(variables[bnprec], etatInitial.get(variables[bnprec]));
                if (solvable && goals.getInstanciation().containsKey(variables[bnprec])) {
                    effet.put(variables[bnprec], goals.getInstanciation().get(variables[bnprec]));
                } else {
                    effet.put(variables[bnprec], bnprec);
                }
                /**
                 * sur une certaine probabilité, on crée une action qui s'applique a l'un des etat fils de l'etat initiale
                 * ainsi créan une multitude de possiblité d'accés au gols et pour bien observers les choix des parcours
                 */
                if (random.nextInt(2)==1) {
                    if (!actions.isEmpty()) {
                        BasicAction basicAction = new BasicAction(tablAction[random.nextInt(bnprec)].successor(etatInitial), effet,tablAction[random.nextInt(bnprec)].getCost()+1);
                        actions.add(basicAction);
                        tablAction[bnprec] = basicAction;
                        precondition = new HashMap<>();
                        effet = new HashMap<>();
                    }
                    else {
                        BasicAction basicAction=new BasicAction(precondition, effet, random.nextInt(3));
                        actions.add(basicAction);
                        tablAction[bnprec] = basicAction;
                        precondition = new HashMap<>();
                        effet = new HashMap<>();
                    }
                } else {

                    BasicAction basicAction=new BasicAction(precondition, effet, random.nextInt(5));
                    actions.add(basicAction);
                    tablAction[bnprec] = basicAction;
                    precondition = new HashMap<>();
                    effet = new HashMap<>();
                }
            }
            /**
             * definition des planner
             * recherche de plan
             * et affichage du resulat
             */
            BFSPlanner bfsPlanner = new BFSPlanner(etatInitial, actions, goals);
            DFSPlanner dfsPlanner = new DFSPlanner(etatInitial, actions, goals);
            DijkstraPlanner dijkstraPlanner = new DijkstraPlanner(etatInitial, actions, goals);

            long startTimeBfs = System.nanoTime();
            List<Action> resultBfs =bfsPlanner.plan();
            long endTimeBfs = System.nanoTime();
            int poidSolution=0;
            if (resultBfs!=null) {

                for (Action action : resultBfs) {
                    poidSolution += action.getCost();
                }
            }
            System.out.println("[BFS] noud visité =            " + bfsPlanner.getSonde());
            System.out.println("[BFS] time of execution=       " + (endTimeBfs-startTimeBfs));
            System.out.println("[BFS] chemin size =            " +(resultBfs!=null ?resultBfs.size():0));
            System.out.println("[BFS] poid de la solution=       " + poidSolution);

            long startTimeDfs = System.nanoTime();
            List<Action> resultDfs =dfsPlanner.plan();
            long endTimeDfs = System.nanoTime();
            poidSolution=0;
            if (resultDfs!=null){
                for (Action action : resultDfs) {
                    poidSolution += action.getCost();
                }
            }
            System.out.println("\n[DFS] noud visité =          " + dfsPlanner.getSonde());
            System.out.println("[DFS] time of execution=       " + (endTimeDfs-startTimeDfs));
            System.out.println("[DFS] chemin size =            " + (resultDfs!=null ?resultDfs.size():0));
            System.out.println("[DFS] poid de la solution=     " + poidSolution);

                long startTimeDjiktra = System.nanoTime();
                List<Action> resultDjiktra= dijkstraPlanner.plan();
                long endTimeDjiktra = System.nanoTime();
            poidSolution=0;
            if(resultDjiktra!=null){
                for (Action action : resultDjiktra) {
                    poidSolution += action.getCost();
                }
            }
            System.out.println("\n[Dijkstra] noud visité =     " + dijkstraPlanner.getSonde());
            System.out.println("[Dijkstra] time of execution=  " + (endTimeDjiktra-startTimeDjiktra));
            System.out.println("[Dijkstra] chemin size =       " + (resultDjiktra!=null ?resultDjiktra.size():0));
            System.out.println("[Dijkstra] poid de la solution=" + poidSolution);
        }

    }
    public static void main(String[] args) {
        compare();
    }
}
