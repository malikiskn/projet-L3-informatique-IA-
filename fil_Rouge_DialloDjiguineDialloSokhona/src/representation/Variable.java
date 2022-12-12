package representation;

import java.util.Objects;
import java.util.Set;

/**
 * 
 * Classe de définition de variable
 *
 */
public class Variable {
	protected String name;
	protected Set<Object> domain;

	/**
	 * Construit une nouvelle instance
	 * @param name le nom de la variable 
	 * @param domain est l'ensemble des valeurs possibles de la variable
	 */
	public Variable(String name, Set<Object> domain) {
		this.name = name;
		this.domain = domain;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Variable other = (Variable) obj;
		return Objects.equals(name, other.name);
	}

	public String getName() {
		return name;
	}

	public Set<Object> getDomain() {
		return domain;
	}

	@Override
	public String toString() {
		return "Variable [name=" + name + ", domain=" + domain + "]";
	}

	
}
