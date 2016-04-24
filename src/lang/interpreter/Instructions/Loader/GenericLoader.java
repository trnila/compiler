package lang.interpreter.Instructions.Loader;

import lang.interpreter.Instructions.IInstruction;
import lang.interpreter.Program;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GenericLoader implements ILoader {
	private Class type;

	public GenericLoader(Class type) {
		this.type = type;
	}

	@Override
	public void load(Program program, String parameter) {
		Constructor constructor = type.getConstructors()[0];
		Class[] parameters = constructor.getParameterTypes();

		try {
			if (parameters.length == 1) {
				if (parameters[0].getName().equals("int")) {
					program.addInstruction((IInstruction)constructor.newInstance(Integer.parseInt(parameter)));
					return;
				} else if(parameters[0].getName().equals("java.lang.String")) {
					program.addInstruction((IInstruction)constructor.newInstance(parameter));
					return;
				}
			} else {
				program.addInstruction((IInstruction) type.newInstance());
				return;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("fd");
	}
}
