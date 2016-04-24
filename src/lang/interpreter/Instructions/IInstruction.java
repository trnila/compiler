package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;

public interface IInstruction {
	void execute(Env env, Program program);
}
