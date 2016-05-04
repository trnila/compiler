package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

public class Relational implements IInstruction {
	private String operator;

	public Relational(String operator) {
		this.operator = operator;
	}

	@Override
	public void execute(Env env, Program program) {
		Value second = env.getStack().pop();
		Value first = env.getStack().pop();
		boolean result;

		//TODO: rewrite this to value class?
		switch(operator) {
			case "lt":
				if(first.getType() == Type.STRING) {
					result = first.getString().compareTo(second.getString()) == -1;
				} else {
					result = first.getFloat() < second.getFloat();
				}
				break;
			case "gt":
				if(first.getType() == Type.STRING) {
					result = first.getString().compareTo(second.getString()) == 1;
				} else {
					result = first.getFloat() > second.getFloat();
				}
				break;

			case "eq":
				if(first.getType() == Type.STRING) {
					result = first.getString().equals(second.getString());
				} else {
					result = first.getFloat() == second.getFloat();
				}
				break;

			default:
				throw new UnsupportedOperationException();
		}

		env.getStack().push(new Value(Type.BOOLEAN, result));
	}

	@Override
	public String toString() {
		return operator;
	}
}
