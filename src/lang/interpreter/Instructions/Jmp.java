package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;

public class Jmp implements IInstruction {
	private int destination;

	public Jmp(int destination) {
		this.destination = destination;
	}

	@Override
	public void execute(Env env, Program program) {
		env.jump(program.getPcFor(destination) - 1);
	}

	@Override
	public String toString() {
		return "jmp " + destination;
	}
}
