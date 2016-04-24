package lang.interpreter.Instructions.Loader;

import lang.interpreter.Instructions.IInstruction;
import lang.interpreter.Instructions.Loader.ILoader;
import lang.interpreter.Program;

import java.lang.reflect.InvocationTargetException;

public class ParamLoader implements ILoader {
	private String op;
	private Class type;

	public ParamLoader(Class type, String op) {
		this.type = type;
		this.op = op;
	}

	@Override
	public void load(Program program, String parameter) {
		try {
			program.addInstruction((IInstruction) type.getConstructor(String.class).newInstance(op));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
