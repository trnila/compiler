package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

public class Uminus implements IInstruction {
	@Override
	public void execute(Env env, Program program) {
		env.getStack().push(
				new Value(Type.INT, env.getStack().pop().getInt() * -1)
		);
	}

	@Override
	public String toString() {
		return "uminus";
	}
}
