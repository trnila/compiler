package lang.interpreter;


import lang.interpreter.Instructions.Loader.InstructionLoader;
import org.apache.tools.ant.filters.StringInputStream;

public class Interpreter {
	public void run() {
		StringInputStream i = new StringInputStream("push 1\n" +
				"push 4\n" +
				"push 5\n" +
				"mul\n" +
				"add\n" +
				"write 1");

		Program program = InstructionLoader.load(i);

		program.execute(new Env());
	}

	public static void main(String[] args) {
		Interpreter i = new Interpreter();
		i.run();
	}
}
