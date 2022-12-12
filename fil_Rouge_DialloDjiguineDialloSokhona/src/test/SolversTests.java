package test;

import block.BlockRegularWord;
import csp.*;
import representation.Constraint;
import representation.Variable;
import vue.VueSolver;
import java.util.*;

/**
 * cette classe permet de tester le solveur de CSP
 */
class SolversTests {
    /**
     * cette methode permet de tester le solveur de CSP
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        //definition du probleme
        int nbBlock=10;
        int nbPile=1;
        // contruction du probleme
        BlockRegularWord model = new BlockRegularWord(nbBlock,nbPile);
       /* System.out.println("Variables   :   "+model.getAllVariables());
        System.out.println("Constraints :   "+model.getImplicationConstraints());*/
        Set<Constraint> constraints = new HashSet<>(model.getConstraints());
        constraints.addAll(model.getImplicationConstraints());
        Map<Variable,Object> solution;
        List<Solver> solverList= new ArrayList<>();
        //creations des solveurs
        BacktrackSolver backtrackSolver=new BacktrackSolver(model.getAllVariables(),constraints);
        MACSolver macSolver = new MACSolver(model.getAllVariables(),constraints);
        HeuristicMACSolver heuristicMACSolverSolver = new HeuristicMACSolver(model.getAllVariables(),constraints,new DomainSizeVariableHeuristic(true),new RandomValueHeuristic(new Random()));
        // ajout des solveurs dans la liste des solveurs
        solverList.add(backtrackSolver);
        solverList.add(macSolver);
        solverList.add(heuristicMACSolverSolver);
        Set <VueSolver> vueSolvers =new HashSet<>();
        for (Solver solver: solverList){
            long start = System.currentTimeMillis();
            solution = solver.solve();
            long end = System.currentTimeMillis();
            float time = (end-start);
            System.out.println(solver.getName()+" time "+time);
            // construction des solutions
            vueSolvers.add (new VueSolver(solver.getName()+"",nbBlock,nbPile,solution,model.getRegisters(),time));
        }
        // affichage des solutions
        // ces solutions sont affichées dans une fenetre en parallèle
        for (VueSolver vue:vueSolvers){
            vue.showSolve();
        }

    }
}
