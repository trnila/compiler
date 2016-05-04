package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

public class Concat implements IInstruction {
	@Override
	public void execute(Env env, Program program) {
		String b = env.getStack().pop().toString();
		String a = env.getStack().pop().toString();

		env.getStack().push(new Value(Type.STRING, a + b));
	}

	@Override
	public String toString() {
		return "concat";
	}
}
