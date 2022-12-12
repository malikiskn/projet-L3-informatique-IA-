package block;

import representation.Implication;
import representation.Variable;

import java.util.HashSet;
import java.util.Set;

/**
 * cette classe permet de construire un block world reguilier
 * elle dispose d'une methode permettant de construire l'ensemble des contraintes regissant un block world reguilier
 */
public class BlockRegularWord extends BlockWord{
    private Set<Implication> implicationConstraints;

    /**
     * constructeur de la classe
     * @param nbBlock nombre de block
     * @param nbPile nombre de pile
     */
    public BlockRegularWord(int nbBlock, int nbPile) {
        super(nbBlock, nbPile);
        this.implicationConstraints = buildImplicationsConstraints();
    }

    /**
     * cette methode permet de construire l'ensemble des contraintes regissant un block world reguilier
     * @return l'ensemble des contraintes regissant un block world reguilier
     */
    private Set<Implication> buildImplicationsConstraints(){
        Set<Implication> implicationConstraints = new HashSet<>();
        Set<Object> pillDomaine= new HashSet<>();
        //on se munis du domaine des piles
        for (int pile=-this.getNbPile();pile<0;pile++) {
            pillDomaine.add(pile);
        }
        //pour chaque block on construit les contraintes
        for (Variable blockOn : this.getOnBlocks()) {
            //pour chaque position possible du block
            for (Object valeur: blockOn.getDomain()){
                // s'il est sur un block
                if ((int)valeur>=0){
                    Set<Object> domain1= new HashSet<>();
                    domain1.add(valeur);
                    // on exclu le block
                    if (getIndice(blockOn)!=(int) valeur){
                        Variable variable2 = this.getRegisters().get(ON+valeur);
                        Set<Object> domain2= new HashSet<>();
                        // il faut que le block sur le quel il est soit posé soit sur une pile
                        //soit sur un autre block d'inidice satisfaisant la raison (q) qui lui relit a cella (i-j = j-k, bi sur bj,bj sur bk)
                        domain2.addAll(pillDomaine);
                        int indiceLogique = 2*(int)valeur-getIndice(blockOn);

                        if (indiceLogique<getNbBlock() && indiceLogique>=-getNbBlock()){
                            domain2.add(indiceLogique);
                        }
                        //on ajoute la contrainte a l'ensemble des contraintes
                        implicationConstraints.add(new Implication(blockOn,domain1,variable2,domain2));
                    }
                }
            }
        }
        return implicationConstraints;
    }

    /**
     * cette methode permet de retourner l'ensemble des contraintes regissant un block world reguilier
     * @return l'ensemble des contraintes regissant un block world reguilier
     */
    public Set<Implication> getImplicationConstraints() {
        return implicationConstraints;
    }
}
