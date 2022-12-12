package block;

import representation.BooleanVariable;
import java.util.*;

/**
 * cette classe permet de construire une representation de l'etat d'un block world
 */
public class BWBooleanDatabase
{
    private Set<BooleanVariable> onTables;
    private Set<BooleanVariable> onBB;
    private Set<BooleanVariable> fixeBlocks;
    private Set<BooleanVariable> freePiles;
    private int nbBlock;
    private int nbPile;
    protected Set<BooleanVariable> allVariables;
    private Map<String ,BooleanVariable> registers;
    public static final String ONTABLE = "onTable";
    public static final String ONBB = "onBB";
    public static final String FIXED = "fixed";
    public static final String FREE = "free";

    /**
     * constructeur de la classe
     * @param nbBlock nombre de block
     * @param nbPile nombre de pile
     */
    public BWBooleanDatabase(int nbBlock, int nbPile) {
        this.nbPile = nbPile;
        this.nbBlock = nbBlock;
        this.onTables =buildOnTables();
        this.onBB =buildOnBB();
        this.freePiles = buildFreePiles();
        this.fixeBlocks = buldFixeBlocks();
        allVariables = this.buldVariables();
        this.registers=new HashMap<>();
        this.resisting();
    }

    /**
     * cette methode permet de construire l'ensemble des variables regissant ce modele
     * @return l'ensemble des variables caractérisant le probleme
     */
    protected Set<BooleanVariable> buldVariables (){
        Set<BooleanVariable> allVariables=new HashSet<>();
        allVariables.addAll(this.freePiles);
        allVariables.addAll(this.fixeBlocks);
        allVariables.addAll(this.onBB);
        allVariables.addAll(this.onTables);
        return allVariables;
    }

    /**
     * cette methode permet de construire l'ensemble des variables definissant la disposition des block
     * on(i) = j signifie que le block i est sur le block j ou pile j
     * @return l'ensemble des variables definissant la disposition des block
     */
    private Set<BooleanVariable> buldFixeBlocks(){
        Set<BooleanVariable> fixedVariables=new HashSet<>();
        for (int i = 0; i < nbBlock; i++) {
            fixedVariables.add(new BooleanVariable(FIXED+i));
        }
        return fixedVariables;
    }

    /**
     * cette methode permet de construire l'ensemble des variables definissant l'etat  des piles (free si une pile
     * est vide sinon free est faux)
     * free1, free2....
     * @return l'ensemble des variables definissant l'etat des piles
     */
    private Set<BooleanVariable> buildFreePiles(){
        Set<BooleanVariable> freeVariables=new HashSet<>();
        for (int i = -1; i >= -nbPile; i--) {
            freeVariables.add(new BooleanVariable(FREE+i));
        }
        return freeVariables;
    }

    /**
     * cette methode permet de construire l'ensemble des variables definissant les liens direct entre un block et une pile
     * ontable(i)-j si le block i est sur la table j sinon ontable(i)-j est faux
     * @return l'ensemble des variables definissant les liens direct entre un block et une pile
     */
    private Set<BooleanVariable> buildOnTables(){
        Set<BooleanVariable> onTables=new HashSet<>();
        for (int i = 0; i < nbBlock; i++) {
            for (int j = -1; j >= -nbPile; j--) {
                onTables.add(new BooleanVariable(ONTABLE+i+"-"+j));
            }
        }
        return onTables;
    }

    /**
     * cette methode permet de construire l'ensemble des variables definissant les liens direct entre un block et un block
     * onBB(i)-j si le block i est sur le block j sinon on(i)-j est faux
     * @return l'ensemble des variables definissant les liens direct entre un block et un block
     */
    private Set<BooleanVariable> buildOnBB(){
        Set<BooleanVariable> oneBB=new HashSet<>();
        for (int i = 0; i < nbBlock; i++) {
            for (int j = 0; j < nbBlock; j++) {
                if (i!=j){
                oneBB.add(new BooleanVariable(ONBB+i+"-"+j));
                }
            }
        }
        return oneBB;
    }

    /** cette methode permet de construire un registre permettant de retrouver une variable a partir de son nom */
    private void resisting(){
        for (BooleanVariable variable: this.allVariables){
            registers.put(variable.getName(), variable);
        }
    }

    /**
     * Cette methode permet d'extraire les variable definissant l'etat correspondant à un état donné (de type List<List<Integer>>)
     * @param state l'etat a partir duquel on veut extraire les variables Booleennes
     * @return l'ensemble des variables definissant l'etat
     */
    public Set<BooleanVariable> extractTrueVariable(List<List<Integer>> state){
        Set<BooleanVariable> trueVariables = new HashSet<>();
        int p = -state.size();
        for (List<Integer> list : state) {
            if (list.isEmpty()){
                trueVariables.add(registers.get(FREE+p));
            }
            else {
                if (list.size() >1){
                    trueVariables.add(registers.get(FIXED+list.get(0)));
                    trueVariables.add(registers.get(ONBB+list.get(list.size()-1)+"-"+list.get(list.size()-2)));
                }
                trueVariables.add(registers.get(ONTABLE+list.get(0)+"-"+p));
                for (int i = 1; i <= list.size()-2; i++) {
                    trueVariables.add(registers.get(FIXED+list.get(i)));
                    trueVariables.add(registers.get(ONBB+list.get(i)+"-"+list.get(i-1)));
                }
            }
            p++;
        }
        return trueVariables;
    }

    /**
     * cette methode renvoie l'ensemble des variables onTable(i) definissant les liens direct entre un block et une pile
     * @return l'ensemble des variables onTable(i)
     */
    public Set<BooleanVariable> getOnTables() {
        return onTables;
    }

    /**
     * cette methode renvoie l'ensemble des variables onBB(i) definissant les liens direct entre un block et un block
     * @return l'ensemble des variables onBB(i)
     */
    public Set<BooleanVariable> getOnBB() {
        return onBB;
    }

    /**
     * cette methode permet de recuperer l’ensemble de toutes les variables booléennes (de type BooleanVariable) correspondant
     * a ce problème (nombe de block, nombre de pile)
     * @return l'ensemble des variables definissant les liens direct entre un block et une pile
     */
    public Set<BooleanVariable> getAllVariables() {
        return allVariables;
    }
}
