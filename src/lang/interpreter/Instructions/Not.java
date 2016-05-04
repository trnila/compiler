package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

public class Not implements IInstruction {
	@Override
	public void execute(Env env, Program program) {
		env.getStack().push(
				new Value(Type.BOOLEAN, !env.getStack().pop().getBool())
		);
	}

	@Override
	public String toString() {
		return "not";
	}
}
