package representation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe de définition de constraintes de type difference
 *
 */
public class DifferenceConstraint implements Constraint {
	Variable variable1;
	Variable variable2;

	/**
	 * Construit une nouvelle  instance
	 * @param variable1 est la première varable
	 * @param variable2 est la seconde variable
	 */
	public DifferenceConstraint(Variable variable1, Variable variable2) {
		this.variable1 = variable1;
		this.variable2 = variable2;
	}

	@Override
	public Set<Variable> getScope() {
		Set<Variable> value = new HashSet<Variable>();
		value.add(this.variable1);
		value.add(this.variable2);
		return value;
	}

	@Override
	public boolean isSatisfiedBy(Map<Variable, Object> instantiation) {
		if (instantiation.get(this.variable1) == null || instantiation.get(this.variable2) == null)
			throw new IllegalArgumentException();
		return instantiation.get(this.variable1) != instantiation.get(this.variable2);
	}
}
