package lang.utils;

import lang.ir.Node;
import lang.ir.Variable;

public class VariableError extends Error {
	public VariableError(String message, Variable node) {
		super(message, node);
	}

	public Variable getVariable() {
		return (Variable) getNode();
	}

	@Override
	public String toString() {
		return "variable " + getVariable().getName() + " not exists";
	}
}
