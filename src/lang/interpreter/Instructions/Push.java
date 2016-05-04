package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;

public class Push implements IInstruction {
	private Value value;

	public Push(Value value) {
		this.value = value;
	}

	@Override
	public void execute(Env env, Program program) {
		env.getStack().push(value);
	}

	@Override
	public String toString() {
		return "push";
	}
}
