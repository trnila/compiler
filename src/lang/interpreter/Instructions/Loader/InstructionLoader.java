package lang.interpreter.Instructions.Loader;

import lang.interpreter.Instructions.*;
import lang.interpreter.Program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class InstructionLoader {
	public static Program load(InputStream in) {
		HashMap<String, ILoader> loaders = new HashMap<>();
		loaders.put("push", new PushLoader());

		loaders.put("uminus", new GenericLoader(Uminus.class));

		loaders.put("add", new ParamLoader(MathInstruction.class, "add"));
		loaders.put("sub", new ParamLoader(MathInstruction.class, "sub"));
		loaders.put("div", new ParamLoader(MathInstruction.class, "div"));
		loaders.put("mul", new ParamLoader(MathInstruction.class, "mul"));
		loaders.put("mod", new ParamLoader(MathInstruction.class, "mod"));
		loaders.put("concat", new GenericLoader(Concat.class));

		loaders.put("label", new LabelLoader());
		loaders.put("jmp", new GenericLoader(Jmp.class));
		loaders.put("fjmp", new GenericLoader(Fjmp.class));

		loaders.put("load", new GenericLoader(Load.class));
		loaders.put("save", new GenericLoader(Save.class));

		loaders.put("write", new GenericLoader(Write.class));
		loaders.put("read", new ReadLoader());

		loaders.put("lt", new ParamLoader(Relational.class, "lt"));
		loaders.put("gt", new ParamLoader(Relational.class, "gt"));
		loaders.put("eq", new ParamLoader(Relational.class, "eq"));

		loaders.put("or", new ParamLoader(Logical.class, "or"));
		loaders.put("and", new ParamLoader(Logical.class, "and"));
		loaders.put("not", new GenericLoader(Not.class));

		Program program = new Program();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			while((line = reader.readLine()) != null) {
				int end = line.indexOf(' ');
				String instruction = end > 0 ? line.substring(0, end) : line;
				if(instruction.startsWith("#!")) {
					continue;
				} else if(!loaders.containsKey(instruction)) {
					throw new RuntimeException("unknown instruction " + instruction);
				}

				loaders.get(instruction).load(program, line.substring(end + 1));}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return program;
	}
}
