package representation;

import java.util.HashSet;

/**
 * Cette classe qui definie une varible de type boolean
 *
 */
public class BooleanVariable extends Variable {

    /**
     * Construit une nouvelle instance de variable en initilisant son doamin a {true,false}
     * @param name le nom de la variable 
     */
	public BooleanVariable(String name) {
		super(name, new HashSet<Object>());
		this.domain.add(true);
		this.domain.add(false);
	}

}
