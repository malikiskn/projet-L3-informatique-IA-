package vue;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWIntegerGUI;
import representation.Variable;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Cette classe permet de construire une interface graphique representant l'execution d'un solveur
 */
public class VueSolver extends Thread {
    private final float time;
    private int numberOfBlock;
    private int numberOfPile;
    private Map<Variable,Object> result;
    private Map<String,Variable> resistre;
    private  String name;

    /**
     * Constructeur de la classe VueSolver
     * @param numberOfBlock est le nombre de blocs
     * @param numberOfPile est le nombre de piles
     * @param result est le resultat
     * @param name est le nom de l'algorithme
     * @param time est le temps d'execution
     */
    public VueSolver(String name,int numberOfBlock, int numberOfPile, Map<Variable, Object> result, Map<String, Variable> resistre,float time) {
        this.numberOfBlock = numberOfBlock;
        this.numberOfPile = numberOfPile;
        this.result = result;
        this.resistre = resistre;
        this.name = name;
        this.time = time;
    }

    /**
     * Methode qui permet de lancer l'interface graphique
     */
    public int showSolve(){
        if (result == null){
            return 0;
        }
        // Building state
        BWStateBuilder< Integer > builder = BWStateBuilder . makeBuilder ( numberOfBlock);
        for ( int b = 0; b < numberOfBlock ; b ++) {
            Variable onB =resistre.get("on"+b); // get instance of Variable for " on_b "
            int under = ( int )result.get( onB );
            if ( under >= 0) { // if the value is a block ( as opposed to a stack )
                builder . setOn (b , under );
            }
        }
        BWState< Integer > state = builder . getState ();
        // Displaying
        BWIntegerGUI gui = new BWIntegerGUI ( numberOfBlock );
        JFrame frame = new JFrame ( this.name );
        JLabel score = new JLabel("Temps d'execution : "+time +" ms");
        JPanel panel = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.add(score,"North");
        frame . add ( gui . getComponent ( state ),"South");
        //frame.add(score);
        frame.setPreferredSize(new Dimension(500, 350));
        int posX = (int) (Math.random() * ( 800 - 0 ));
        int posy = (int) (Math.random() * ( 600 - 0 ));
        frame.setLocation(posX, posy);
        frame . pack ();
        frame . setVisible ( true );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return  1;

    }

}