package lang.interpreter.Instructions.Loader;

import lang.interpreter.Instructions.Loader.ILoader;
import lang.interpreter.Program;

public class LabelLoader implements ILoader {
	@Override
	public void load(Program program, String parameter) {
		program.addLabel(Integer.parseInt(parameter));
	}
}
