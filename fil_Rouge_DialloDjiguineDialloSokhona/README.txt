
Rendu du TP: Intélligence Artficielle

File rouge

Membres Du Groupe :

DIALLO Abdoulaye Djibril	22112788
DJIGUINE Mamady 			22110369
DIALLO Elhadj Alseiny 		22011830
SOKHONA	Malick 				21910279

Prérequis: les librairies de test "tests.jar", "blocksworld.jar" et "bwgenerator.jar" doivent etre ajoutées dans le dossier lib du projet.
Package test: contient les classes de test vues en tp et celles du fil rouge.
              Demo* : contient les classes de test vues en tp.
              les autres classes sont des classes de test du fil rouge.

Teste de regularité : dans la classe BWRegularTest on a simulé trois (3) etats dont deux (2) reguliés et 1 irregulié en testant les trois (3) etats.
Teste des planificateurs de recherche: dans la classe PlanificateurTest, on a un etat instantié sous forme de Liste de piles contenant des listes de
                                        blocks sur lesquelles on exécute les planificateurs de recherche pour atteindre de deux but (goal1 un peut simple et goal2 un peut complexe qui suffise pour
                                        montrer la puissance de resolution des solvers sur des petits et gros problèmes).
                                        Pour le choix de l'etat on a pris un compromis entre la complexité de l'etat et la rapidité de la resolution du problème dans l'ensemble.
                                        les Solvers, comme A* et bfs pourait etre poussé un peu plus loin.


      Une classe d'optimisation de probleme de planification qui, renvoie  les actions possibles suivant un etat  pour eviter de tester une serie d'actions inutile à été implementer;
      Grace au quel on a pu joindre bfs au parcourts afin d'éviter les execptions sur les piles d'appel de type (java.lang.StackOverflowError) ,on a instancié deux bfs sur le premier goal avec et sans optimisateur
      et on  a constaté une forte baisse du nombre de noeuds visités (on est passé de 63 178 a 668 )
      mais vu son temps de calcule à chaque changement d'etat, il n'est parfois pas utile de l'utiliser sur des problemes ou les piles sont très dispersées voir beaucoup d'actions possibles.
      pour l'utiliser on a muni tous les planificatuers d'un deuxième constructeur prenent en parametre un objet de type Optimisation en plus.

      Heurisique: on a implementé une deuxieme heuristique et bien testé qui permet de calculer le nombre de blocs mal placés (HeuristicUnsuccesGoal) tout les block au
      dessus d'un block mal placé sont mal placé et un pour le teste un AstarPlanner3 est muni de cette heuristic.

      La classe de teste sur les solvers est muni d'une fonction transformant un etat sous forme de listes de piles en etat sous forme affectation(map)

Teste des solvers : on a choisi un probleme de 10 block 1 pile c'est pas toujours évident de voir leur diversité vu que l'heuristic use de l'aléatoire.
                    mais on constate quand meme une certaine diversité niveau performance des solvers.
                   NB: vous avez une possibilité de faire differents choix en indiquant le nombre de blocks et de piles sur les premieres lignes de testes.
Les Vue: les classes de vu (VuePlan, VueSolver) sont des thread s'executant en parallele pour eviter l'attente de la construction Graphique sur les Planner en particulier
         avec quelque edition de la bibliotheque on a rajouté les information d'execution des solvers et planners dans leur vue respective.
Test d'Extraction : une classe executable BWExtraction permet de similer différente configuration dont un par defaut suggeré par (@Mr  Bruno Zanuttini) 5 block 5 pile:
                    sur la quel on peut extraire des regles de premisse importante jusqu'a 100%,(si onBB 1-2 alors fixed2 ...).
                    Possibilité de changer le nombre de block et de pile en modifiant les premieres lignes de la classe.

+++++++++++++ Pour l'exécution du projet: +++++++++++++++++++++++++++
Rassurez vous d'etre à la racine du projet et tapez les commandes suivantes.

    -Compilation: javac -d ./bin -classpath "lib/*" $(find src -name "*.java")



    -Exécutions:

    plannerTests: java -cp ./bin:./lib/blocksworld.jar test.PlannersTests
    solverTests: java -cp ./bin:./lib/blocksworld.jar test.SolversTests
    BWRegularTests: java -cp ./bin:./lib/blocksworld.jar test.BWRegularTest
    BWExtraction: java -cp ./bin:./lib/bwgenerator.jar test.BWExtraction
    ---------------------------------------------------------------
    Pour tester les class avec les tests.jar
    reprÃ©sentation: java -cp ./bin:./lib/tests.jar test.tests
    Experimentation de sonde:  java -cp ./bin:./lib/tests.jar planning.ComparePlanner


    Remarque :

    Suivant les resultats de sonde pour nos parcourts on remarque que: le parcourt en profondeur trouve bien une solution mais non optimale ,tandisque le parcourt en largeur retourne le plus court plan sans tenir compte du cout mais peut explorer unitulement des noeuds et Dijkstra retourne un plan de coût minimal .




