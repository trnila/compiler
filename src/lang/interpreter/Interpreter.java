package lang.interpreter;


import lang.interpreter.Instructions.Loader.InstructionLoader;

import java.io.*;

public class Interpreter {
	public static void main(String[] args) throws IOException {
		InputStream in;
		if(args.length >= 1) {
			in = new FileInputStream(args[0]);
		} else {
			in = System.in;
		}

		Program program = InstructionLoader.load(in);
		program.execute(new Env());
	}
}
