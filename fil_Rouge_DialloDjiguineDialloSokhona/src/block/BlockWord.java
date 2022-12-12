package block;

import representation.Constraint;
import representation.DifferenceConstraint;
import representation.Implication;
import representation.Variable;

import java.util.*;


/**
 * Cette classe permet de construire un probleme de type BlockWorld avec les contraintes qui lui sont associées
 * elle dispose d'une methode permettant de construire l'ensemble des contraintes regissant un block world
 */
public class BlockWord extends BlockModel{
    private Set<Constraint> constraints;

    /**
     * constructeur de la classe
     * @param nbBlock nombre de block
     * @param nbPile nombre de pile
     */
    public BlockWord(int nbBlock, int nbPile) {
        super(nbBlock, nbPile);
        constraints =this.buildConstraints();
    }

    /**
     * cette methode permet de construire l'ensemble des contraintes regissant un block world
     * @return l'ensemble des contraintes regissant un block world
     */
    private Set<Constraint> buildConstraints() {
        /**
         * difference constraint on Block position
         * deux block ne peuvent pas avoir la meme position
         */
        Set<Constraint> constraints =new HashSet<>();
        for (Variable variable1 : this.getOnBlocks()) {
            for (Variable variable2 : this.getOnBlocks()) {
                if (!variable1.equals(variable2)){
                    constraints.add(new DifferenceConstraint(variable1,variable2));
                }
            }
        }

        /**
         * implication Constraint on State variation of a block or pile
         * A block that serves as a support for another block is fixed
         * A battery that consists of block has not been free
         */
        for (Variable variable : this.getOnBlocks()) {
            for (Object valeur: variable.getDomain()) {
                Set<Object> domain1= new HashSet<>();
                domain1.add(valeur);
                if((int)valeur>=0) {
                    Variable variable2 = this.getRegisters().get(FIXED + valeur);
                    Set<Object> domain2 = new HashSet<>();
                    domain2.add(true);
                    constraints.add(new Implication(variable, domain1, variable2, domain2));
                }else {
                    Variable variable2 = this.getRegisters().get(FREE + valeur);
                    Set<Object> domain2 = new HashSet<>();
                    domain2.add(false);
                    constraints.add(new Implication(variable, domain1, variable2, domain2));
                }
            }
        }

        /**
         * add implication constraint between circular configuration
         * prohibited to have an circular config (A-->B and A --> A)
         */
        for (Variable variable : this.getOnBlocks()) {
            for (Object valeur: variable.getDomain()) {
                Set<Object> domain1 = new HashSet<>();
                domain1.add(valeur);
                if ((int) valeur >= 0) {
                    Variable variable2 = this.getRegisters().get(ON + valeur);
                    Set<Object> domain2 = new HashSet<>(variable2.getDomain());
                    domain2.remove(getIndice(variable));
                    constraints.add(new Implication(variable, domain1, variable2, domain2));
                }
            }
        }
        return constraints;
    }

    /**
     * this function return the list of constraint
     * @return list of constraint
     */
    public Set<Constraint> getConstraints(){
        return this.constraints;
    }

    /**
     * this function return the list of variable
     * @return list of variable
     */
}
