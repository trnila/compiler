package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

public class MathInstruction implements IInstruction {
	private String operator;

	public MathInstruction(String operator) {
		this.operator = operator;
	}

	@Override
	public void execute(Env env, Program program) {
		Value first = env.getStack().pop();
		Value second = env.getStack().pop();

		float a = second.getFloat();
		float b = first.getFloat();
		float c;

		switch(operator) {
			case "add":
				c = a + b;
				break;
			case "sub":
				c = a - b;
				break;
			case "mul":
				c = a * b;
				break;
			case "div":
				c = a / b;
				break;
			case "mod":
				c = a % b;
				break;
			default:
				throw new RuntimeException("unknown-" + operator);
		}

		boolean flo = first.getType() == Type.FLOAT || second.getType() == Type.FLOAT;

		if(flo) {
			env.getStack().push(new Value(Type.FLOAT, c));
		} else {
			env.getStack().push(new Value(Type.INT, (int) c));
		}

	}

	@Override
	public String toString() {
		return operator;
	}
}
