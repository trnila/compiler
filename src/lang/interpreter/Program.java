package lang.interpreter;

import lang.interpreter.Instructions.IInstruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Program {
	private List<IInstruction> instructions = new ArrayList<>();
	private HashMap<Integer, Integer> labels = new HashMap<>();

	public void addInstruction(IInstruction instruction) {
		instructions.add(instruction);
	}

	public void addLabel(int i) {
		labels.put(i, instructions.size());
	}

	public void execute(Env env) {
		int pc;
		while((pc = env.getPc()) < instructions.size()) {
			try {
				instructions.get(pc).execute(env, this);
				env.nextInstruction();
			} catch(Exception e) {
				printDebuggingInformation(env, pc, e);
				return;
			}
		}
	}

	private void printDebuggingInformation(Env env, int pc, Exception e) {
		System.err.println("An internal error occured in language interpretter\n");

		for(int i = Math.max(0, pc - 5); i <= pc; i++) {
			System.err.println(instructions.get(i));
		}

		System.err.println("\nStack:");
		while(!env.getStack().empty()) {
			Value val = env.getStack().pop();
			System.err.println(val);
		}

		System.err.println("\nThrown exception: ");
		e.printStackTrace(System.err);
	}

	public int getPcFor(int destination) {
		return labels.get(destination);
	}
}
