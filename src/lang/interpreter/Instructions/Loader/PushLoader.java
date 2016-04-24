package lang.interpreter.Instructions.Loader;

import lang.interpreter.Instructions.Loader.ILoader;
import lang.interpreter.Instructions.Push;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

public class PushLoader implements ILoader {
	@Override
	public void load(Program program, String parameter) {
		Type type = parseType(parameter.charAt(0));

		program.addInstruction(new Push(new Value(type, parameter.substring(1))));
	}

	private Type parseType(char parameter) {
		switch(parameter) {
			case 'I':
				return Type.INT;
			case 'S':
				return Type.STRING;
			case 'F':
				return Type.FLOAT;
			case 'B':
				return Type.BOOLEAN;
			default:
				return Type.ERROR;
		}
	}
}
