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
				System.out.println(pc + ":" + instructions.get(pc));
				e.printStackTrace();
				throw e;
			}
		}
	}

	public int getPcFor(int destination) {
		return labels.get(destination);
	}
}
