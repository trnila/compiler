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

		if(first.getType() == Type.INT) {
			int a = first.getInt();
			int b = second.getInt();
			int c;

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
					throw new RuntimeException("unknown");
			}

			env.getStack().push(new Value(Type.INT, c));
		} else {
			throw new RuntimeException("unknown");
		}
	}

	@Override
	public String toString() {
		return operator;
	}
}
