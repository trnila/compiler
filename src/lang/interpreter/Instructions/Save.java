package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;

public class Save implements IInstruction {
	private String variable;

	public Save(String variable) {
		this.variable = variable;
	}

	@Override
	public void execute(Env env, Program program) {
		Value val = env.getStack().pop();
		env.save(variable, val);
	}
}
