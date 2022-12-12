package vue;

import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;
import planning.Action;
import representation.Variable;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Cette classe permet de construire une interface graphique representant l'execution d'un plan
 */
public class VuePlan  extends Thread{
    private final int sonde;
    private int numberOfBlock;
    private int numberOfPile;
    private Map<String,Variable> resistre;
    private Map<Variable,Object> initialState;
    private List<Action> result;
    private String name;
    private float time;

    /**
     * Constructeur de la classe VuePlan
     * @param sonde est le nombre de noeud visité
     * @param numberOfBlock est le nombre de blocs
     * @param numberOfPile est le nombre de piles
     * @param initialState est l'etat initial
     * @param result est le resultat
     * @param name est le nom de l'algorithme
     * @param time est le temps d'execution
     */
    public VuePlan(String name, int numberOfBlock, int numberOfPile, Map<Variable, Object> initialState, List<Action> result,Map<String,Variable> resistre,float time,int sonde) {
        this.numberOfBlock = numberOfBlock;
        this.numberOfPile = numberOfPile;
        this.initialState = initialState;
        this.result = result;
        this.resistre = resistre;
        this.name = name;
        this.time = time;
        this.sonde = sonde;

    }

    /**
     * Methode qui permet de lancer l'interface graphique
     */
    public void run() {
        BWStateBuilder< Integer > builder = BWStateBuilder . makeBuilder ( numberOfBlock);
        for ( int b = 0; b < numberOfBlock ; b ++) {
            Variable onB = resistre.get("on"+b); // get instance of Variable for " on_b "
            int under = ( int )initialState.get( onB );
            if ( under >= 0) { // if the value is a block ( as opposed to a stack )
                builder . setOn (b , under );
            }
        }
        BWIntegerGUI gui = new BWIntegerGUI ( numberOfBlock );
        JFrame frame = new JFrame ( this.name );
        BWState< Integer > bwState = builder.getState();
        BWComponent< Integer > component = gui . getComponent ( bwState );
        frame.setPreferredSize(new Dimension(300, 400));
        JLabel score = new JLabel("Temps de recherche : "+time +" seconde");
        JLabel solutionSize = new JLabel("Nombre d'action de la solution : "+result.size());
        JLabel neoudVisited = new JLabel("Nombre noeud explorer : "+this.sonde);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(score,BorderLayout.NORTH);
        panel.add(neoudVisited,BorderLayout.CENTER);
        panel.add(solutionSize,BorderLayout.SOUTH);
        frame.setLayout(new BorderLayout());
        frame.add(panel,"North");
        frame . add ( component ,"South");
        int posX = (int) (Math.random() * ( 800 - 0 ));
        int posy = (int) (Math.random() * ( 600 - 0 ));
        frame.setLocation(posX, posy);
        frame . pack ();
        frame . setVisible ( true );
        frame . setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Map<Variable,Object> state = initialState;
        if (result!=null){
            for ( Action a : result ) {
                try { Thread . sleep (1000 ); }
                catch ( InterruptedException e) { e . printStackTrace (); }
                state = a . successor ( state );

                //reconstruction d'etat
                BWStateBuilder< Integer > builderState = BWStateBuilder . makeBuilder ( numberOfBlock);
                for ( int b = 0; b < numberOfBlock ; b ++) {
                    Variable onB = resistre.get("on"+b); // get instance of Variable for " on_b "
                    int under = ( int )state.get( onB );
                    if ( under >= 0) { // if the value is a block ( as opposed to a stack )
                        builderState . setOn (b , under );
                    }
                    try {
                        sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                component . setState ( builderState.getState());
            }
            System . out . println ( " Simulation ␣ of ␣ plan : ␣ done . " );
        }
    }
}
