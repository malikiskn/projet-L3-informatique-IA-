package representation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe de définition de contrainte unaire
 *
 */
public class UnaryConstraint implements Constraint {
	Variable variable;
	Set<Object> domaine;

	/**
	 * Construit une nouvelle instance
	 * @param variable le nom de la variable 
	 * @param domaine est un sous ensemble du domain de la variable
	 */
	public UnaryConstraint(Variable variable, Set<Object> domaine) {
		this.variable = variable;
		this.domaine = domaine;
	}

	@Override
	public Set<Variable> getScope() {
		Set<Variable> value = new HashSet<Variable>();
		value.add(this.variable);
		return value;
	}

	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
		if (instantiation.get(this.variable) == null)
			throw new IllegalArgumentException();
		if (domaine.contains(instantiation.get(this.variable)))
			return true;
		return false;
	}

}
