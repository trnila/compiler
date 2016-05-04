package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

public class Logical implements IInstruction {
	private String operator;

	public Logical(String operator) {
		this.operator = operator;
	}

	@Override
	public void execute(Env env, Program program) {
		boolean second = env.getStack().pop().getBool();
		boolean first = env.getStack().pop().getBool();
		boolean result;

		switch(operator) {
			case "or":
				result = first || second;
				break;

			case "and":
				result = first && second;
				break;

			default:
				throw new UnsupportedOperationException("unknown logical operator " + operator);
		}

		env.getStack().push(new Value(Type.BOOLEAN, result));
	}

	@Override
	public String toString() {
		return operator;
	}
}
