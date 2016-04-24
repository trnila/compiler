package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;
import lang.ir.Type;

import java.util.Scanner;

public class Read implements IInstruction {
	private Type type;

	public Read(Type type) {
		this.type = type;
	}

	@Override
	public void execute(Env env, Program program) {
		Value v = null;

		Scanner s = env.getInputStream();



		switch(type) {
			case INT:
				v = new Value(type, s.nextInt());
				break;
			case FLOAT:
				v = new Value(type, Float.toString(s.nextFloat()));
				break;
			case STRING:
				v = new Value(type, s.next("[^ ]+"));
				break;
			case BOOLEAN:
				v = new Value(type, Boolean.toString(s.nextBoolean()));
				break;
			case ERROR:
				break;
		}

		env.getStack().push(v);
	}
}
