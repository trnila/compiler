package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;

public class Load implements IInstruction{
	private String variable;

	public Load(String variable) {
		this.variable = variable;
	}

	@Override
	public void execute(Env env, Program program) {
		env.getStack().push(env.load(variable));
	}
}
