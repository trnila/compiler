package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;

public class Fjmp implements IInstruction {
	private int destination;

	public Fjmp(int destination) {
		this.destination = destination;
	}


	@Override
	public void execute(Env env, Program program) {
		Value v = env.getStack().pop();
		if(!v.getBool()) {
			env.jump(program.getPcFor(destination) - 1);
		}
	}
}
