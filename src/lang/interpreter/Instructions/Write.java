package lang.interpreter.Instructions;

import lang.interpreter.Env;
import lang.interpreter.Program;
import lang.interpreter.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Write implements IInstruction {
	private final int count;

	public Write(int count) {
		this.count = count;
	}

	@Override
	public void execute(Env env, Program program) {
		List<Value> values = new ArrayList<>();

		for(int i = 0; i < count; i++) {
			values.add(env.getStack().pop());
		}

		for(int i = values.size() - 1; i >= 0; i--) {
			env.write(values.get(i).toString());
		}
		env.write("\n");
	}

	@Override
	public String toString() {
		return "write " + count;
	}
}
