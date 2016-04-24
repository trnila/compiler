package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;

public class Write implements IInstruction {
	private final int count;

	public Write(int count) {
		this.count = count;
	}

	@Override
	public void execute(Env env, Program program) {
		for(int i = 0; i < count; i++) {
			env.write(env.getStack().pop().toString());
		}
		env.write("\n");
	}
}
