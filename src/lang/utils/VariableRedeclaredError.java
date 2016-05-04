package lang.utils;

import lang.ir.Variable;

public class VariableRedeclaredError extends Error {
	private Variable prev;

	public VariableRedeclaredError(String message, Variable current, Variable prev) {
		super(message, current);
		this.prev = prev;
	}

	public Variable getVariable() {
		return (Variable) getNode();
	}

	@Override
	public String toString() {
		return "variable " + getVariable().getName() + " is already declared, previous declaration is on line " + prev.getLine();
	}
}
