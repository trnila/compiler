package lang.interpreter.Instructions.Loader;

import lang.interpreter.Instructions.Read;
import lang.interpreter.Program;
import lang.ir.Type;

public class ReadLoader implements ILoader {
	@Override
	public void load(Program program, String parameter) {
		Type type = parseType(parameter.charAt(0));
		program.addInstruction(new Read(type));
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
