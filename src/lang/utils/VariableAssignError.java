package lang.utils;

import lang.ir.Node;
import lang.ir.Type;
import lang.ir.Variable;

public class VariableAssignError extends Error {
	private Type tried;

	public VariableAssignError(String message, Variable node, Type tried) {
		super(message, node);
		this.tried = tried;
	}

	public Variable getVariable() {
		return (Variable) getNode();
	}

	@Override
	public String toString() {
		return "tried to assign type " + tried + " to variable of type " + getVariable().getType();
	}
}
