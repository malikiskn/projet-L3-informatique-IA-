package block;

import representation.BooleanVariable;
import representation.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * cette classe permet de construire l'ensemble de variables regissant le probleme de block world
 * ainsi que que des methodes caractérisant la manipulation du probleme
 */
public class BlockModel {
    private int nbBlock;
    private int nbPile;
    protected Set<Variable> allVariables;
    private Set<Variable> onBlocks;
    private Set<Variable> freePiles;
    private Set<Variable> fixeBlocks;
    private Map<String ,Variable> registers;
    public final  String ON = "on";
    public final  String FREE = "free";
    public final  String FIXED = "fixed";

    /**
     * constructeur de la classe
     * @param nbBlock nombre de block
     * @param nbPile nombre de pile
     */
    public BlockModel(int nbBlock, int nbPile) {
        this.nbBlock = nbBlock;
        this.nbPile = nbPile;
        this.registers=new HashMap<>();
        onBlocks = buldOnBlock();
        freePiles = buildFreePiles();
        fixeBlocks = buldFixeBlocks();
        allVariables = this.buldVariables();
        this.resisting();
    }

    /**
     * cette methode permet de construire l'ensemble des variables regissant ce modele
     * @return l'ensemble des variables caractérisant le probleme
     */
    protected Set<Variable> buldVariables (){
        Set<Variable> allVariables=new HashSet<>();
        allVariables.addAll(this.onBlocks);
        allVariables.addAll(this.fixeBlocks);
        allVariables.addAll(this.freePiles);
        return allVariables;
    }

    /**
     * cette methode permet de construire l'ensemble des variables definissant la disposition des block
     * on(i) = j signifie que le block i est sur le block j ou pile j (j est un entier)
     * @return l'ensemble des variables definissant la disposition des block
     */
    private Set<Variable> buldOnBlock(){
        Set<Variable> onVariables=new HashSet<>();
        Set<Object> domaine=new HashSet<>();

        for (int i = -nbPile; i < nbBlock; i++) {
            domaine.add(i);
        }
        for (int i = 0; i < nbBlock; i++) {
            Set<Object> domaineI=new HashSet<>(domaine);
            domaineI.remove(i);
            onVariables.add(new Variable(ON+i,domaineI));
        }
        return onVariables;
    }

    /**
     * cette methode permet de construire l'ensemble des variables definissant l'etat de (liberté de mouvement)  des block (fixed si un block
     * est sur lui sinon fixed est faux)
     * @return l'ensemble des variables definissant l'etat (liberté de mouvement) des block
     */
    private Set<Variable> buldFixeBlocks(){
        Set<Variable> fixedVariables=new HashSet<>();
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
    private Set<Variable> buildFreePiles(){
        Set<Variable> freeVariables=new HashSet<>();
        for (int i = -1; i >= -nbPile; i--) {
            freeVariables.add(new BooleanVariable(FREE+i));
        }
        return freeVariables;
    }

    /**
     * cette methode permet de construire un registre permettant de retrouver une variable a partir de son nom
     */
    private void resisting(){
        for (Variable variable: this.allVariables){
            registers.put(variable.getName(), variable);
        }
    }

    /**
     * cette methode permet de retrouver l'indice  d'un block a partir d'un des variables definissant son etat
     * apartir de la quelle on poura facilement retrouver une autre variable definissant letat du block via le registre
     * fixed(i) ou on(i).. , i est l'indice du block
     * @param variable la variable definissant l'etat du block
     * @return l'indice du block
     */
    public  int getIndice(Variable variable){
        String[] nom =variable.getName().split("");
        if (nom[1].equals("r")){
            return Integer.parseInt(variable.getName().substring(4));
        }
        if (nom[1].equals("i")){
            return Integer.parseInt(variable.getName().substring(5));
        }
        return Integer.parseInt(variable.getName().substring(2));
    }

    /**
     * cette methode permet de retrouver tous les variables definissant l'etat du mondel
     * @return l'ensemble des variables definissant l'etat du mondel
     */
    public Set<Variable> getAllVariables() {
        return allVariables;
    }

    /**
     * cette methode permet de retourner le registre permettant de retrouver une variable a partir de son nom
     * on(i) ou fixed(i) ou free(i) , i est l'indice du block
     * @return le registre permettant de retrouver une variable a partir de son nom
     */
    public Map<String, Variable> getRegisters() {
        return registers;
    }

    /**
     * cette methode permet de retourner l'ensemble des variables definissant l'etat des piles
     * @return l'ensemble des variables definissant l'etat des piles
     */
    public Set<Variable> getFreePiles() {
        return freePiles;
    }

    /**
     * cette methode permet de retourner l'ensemble des variables definissant l'etat (disposition) des block
     * on(i) = j , i est l'indice du block et j est l'indice de la pile, block ou le block(i) est posé
     * @return l'ensemble des variables definissant l'etat des block
     */
    public Set<Variable> getOnBlocks() {
        return onBlocks;
    }

    /**
     * cette methode permet de retourner l'ensemble des variables definissant l'etat (liberté de mouvement) des block
     * fixed(i) = true si le block(i) est sous un autre block sinon fixed(i) = false
     * @return l'ensemble des variables definissant l'etat (liberté de mouvement) des block
     */
    public Set<Variable> getFixeBlocks() {
        return fixeBlocks;
    }

    /**
     * cette methode permet de retourner le nombre de block du modele
     * @return le nombre de block du modele
     */
    public int getNbBlock() {
        return nbBlock;
    }

    /**
     * cette methode permet de retourner le nombre de pile du modele
     * @return le nombre de pile du modele
     */
    public int getNbPile() {
        return nbPile;
    }
}
