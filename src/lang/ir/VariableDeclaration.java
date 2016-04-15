package lang.ir;

import lang.utils.IRVisitor;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclaration extends Statement {
	private Type type;
	private List<Variable> variables = new ArrayList<>();

	public VariableDeclaration(Type type, Variable var) {
		this.type = type;
		variables.add(var);
	}

	@Override
	public void accept(IRVisitor visitor) {
		for(Variable var: variables) {
			visitor.visit(var);
		}
		visitor.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		boolean first = true;
		for(Variable var: variables) {
			if(first) {
				first = false;
			} else {
				s.append(", ");
			}
			s.append(var.toString());
		}

		return type.toString() + " " + s.toString();
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public Type getType() {
		return type;
	}
}
