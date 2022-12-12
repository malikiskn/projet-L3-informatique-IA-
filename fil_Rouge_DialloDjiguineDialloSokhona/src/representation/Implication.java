package representation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe de définition d'une contrainte de type implication
 *
 */
public class Implication implements Constraint {
	Variable variable1;
	Variable variable2;
	Set<Object> domain1;
	Set<Object> domain2;

	/**
	 * Construit une nouvelle instance 
	 * @param variable1 le nom de la prémiere variable
	 * @param domain1 est un sous ensemble du domain de la variable variable1
	 * @param variable2 le nom de la seconde variable
	 * @param domain2 est un sous ensemble du domain de la variable variable2
	 */
	public Implication(Variable variable1, Set<Object> domain1, Variable variable2, Set<Object> domain2) {
		this.variable1 = variable1;
		this.variable2 = variable2;
		this.domain1 = domain1;
		this.domain2 = domain2;
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
		if (this.domain1.contains(instantiation.get(this.variable1)) && this.domain2.contains(instantiation.get(this.variable2)))
			return true;
		if (!this.domain1.contains(instantiation.get(this.variable1)))
			return true;
		return false;
	}
	@Override
	public String toString() {
		return "Implication [variable1=" + variable1 + ", variable2=" + variable2 + ", domain1=" + domain1 + ", domain2="
				+ domain2 + "]";
	}

}
